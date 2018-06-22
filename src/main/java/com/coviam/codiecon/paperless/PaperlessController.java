package com.coviam.codiecon.paperless;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaperlessController {

  @RequestMapping(value = "/paperless/upload", method = {RequestMethod.POST})
  @ResponseBody
  public Boolean uploadFile(@RequestBody UploadFileModel model) {

    return true;
  }

  @RequestMapping(value = "/paperless/missingDoc", method = {RequestMethod.GET})
  @ResponseBody
  public List<String> uploadFile(@RequestParam String name) {

    return null;
  }


}
