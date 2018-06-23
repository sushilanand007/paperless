package com.coviam.codiecon.paperless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/user/insert", method = {RequestMethod.POST})
  @ResponseBody
  public Boolean insertUser(@RequestBody UserModel userModel) {
    try {
      userService.createUser(userModel.getName(), userModel.getUserType());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  @RequestMapping(value = "/user", method = {RequestMethod.GET})
  @ResponseBody
  public UserModel getUser(@RequestParam String name) {
    return userService.findUser(name);
  }
}
