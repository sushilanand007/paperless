package com.coviam.codiecon.paperless;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleController {

  @GetMapping("/console")
  public String greeting(ModelMap model) {
    return "console";
  }

}
