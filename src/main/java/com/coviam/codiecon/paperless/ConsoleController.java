package com.coviam.codiecon.paperless;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsoleController {

  @GetMapping("/welcome")
  public String welcome(ModelMap model) {
    return "console";
  }

  @GetMapping("/admin")
  public String adminPage(ModelMap model) {
    return "admin";
  }
}
