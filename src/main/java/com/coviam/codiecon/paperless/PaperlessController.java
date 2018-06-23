package com.coviam.codiecon.paperless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@RestController
public class PaperlessController {

  @Autowired
  private PaperlessService paperlessService;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/paperless/upload", method = {RequestMethod.POST})
  @ResponseBody
  public Boolean uploadFile(@RequestParam("file") MultipartFile multipartFile,
      @RequestParam String documentType, @RequestParam String name) {

    String mimeType = multipartFile.getContentType();
    try {
      File convFile = new File(multipartFile.getOriginalFilename());
      convFile.createNewFile();
      FileOutputStream fos = new FileOutputStream(convFile);
      fos.write(multipartFile.getBytes());
      fos.close();

      boolean success = paperlessService
          .uploadDocument(DocumentTypes.getByUiMenuLabel(documentType), name, convFile, mimeType);
      convFile.delete();
      return success;
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return false;
    }
  }

  @RequestMapping(value = "/paperless/missingDoc", method = {RequestMethod.GET})
  @ResponseBody
  public List<String> fetchMissingDocuments(@RequestParam String name) {

    try {
      return paperlessService.getMissingDocument(name);
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

  @RequestMapping(value = "/paperless/users", method = {RequestMethod.GET})
  @ResponseBody
  public List<String> getUserList(@RequestParam(required = false) String name) {
    return userService.getUserList(name);
  }

  @RequestMapping(value = "/paperless/report", method = {RequestMethod.GET})
  @ResponseBody
  public Map<String, List<String>> getReport() {
    try {
      return paperlessService.missingDocumentsReport();
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

}
