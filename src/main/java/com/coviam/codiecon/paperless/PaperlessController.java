package com.coviam.codiecon.paperless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class PaperlessController {

  @Autowired
  private PaperlessService paperlessService;

  @RequestMapping(value = "/paperless/upload", method = {RequestMethod.POST})
  @ResponseBody
  public Boolean uploadFile(@RequestBody UploadFileModel model) {
    // TODO set file and pass
    File file = null;
    String mimeType = model.getFile().getContentType().split("/")[0];
    try {
      return paperlessService
          .uploadDocument(model.getDocumentType(), model.getName(), file, mimeType);
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return false;
    }
  }

  @RequestMapping(value = "/paperless/missingDoc", method = {RequestMethod.GET})
  @ResponseBody
  public List<String> uploadFile(@RequestParam String name) {

    try {
      return paperlessService.getMissingDocument(name);
    } catch (IOException | GeneralSecurityException e) {
      e.printStackTrace();
      return null;
    }
  }


}
