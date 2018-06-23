package com.coviam.codiecon.paperless;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/paperless")
@RestController
public class PaperlessController {

  @Autowired private PaperlessService paperlessService;

  @Autowired private UserService userService;

  @RequestMapping(value = "/upload", method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

  @RequestMapping(value = "/missing-doc", method = {RequestMethod.GET}, consumes = {MediaType.ALL_VALUE})
  @ResponseBody
  public List<String> fetchMissingDocuments(@RequestParam String name) {

    try {
      return paperlessService.getMissingDocument(name);
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

  @RequestMapping(value = "/users", method = {RequestMethod.GET}) @ResponseBody
  public List<String> getUserList(@RequestParam(required = false) String name) {
    return userService.getUserList(name);
  }

  @RequestMapping(value = "/report", method = {RequestMethod.GET}) @ResponseBody
  public Map<String, List<String>> getReport() {
    try {
      return paperlessService.missingDocumentsReport();
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return null;
    }
  }

  @RequestMapping(value = "/getUsername", method = {RequestMethod.GET}) @ResponseBody
  public String getUsername() {
    OAuth2Authentication auth2Authentication =
        (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
    String email = ((LinkedHashMap) auth2Authentication.getUserAuthentication().getDetails()).get("email")
        .toString();
    return email.split("@")[0];
  }

  public String isValidCoviamEmailAddress(String email) {
    String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@(coviam.com)$";
    Pattern p1 = Pattern.compile(ePattern);
    Matcher m1 = p1.matcher(email);
    if (m1.matches()){
      return email.split("@")[0];
    }
    return "Not a covaim email. login from coviam email";
  }


  public String isValidEmailAddress(String email) {
    String ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    Pattern p1 = Pattern.compile(ePattern);
    Matcher m1 = p1.matcher(email);
    if (m1.matches()){
      return email.split("@")[0];
    }
    return "Not a covaim email. login from coviam email";
  }
}
