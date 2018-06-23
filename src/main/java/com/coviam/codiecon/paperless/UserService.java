package com.coviam.codiecon.paperless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void createUser(String name, UserType userType) {
    User user = new User();
    user.setName(name);
    if (null == userType) {
      user.setType(UserType.USER);
    } else {
      user.setType(userType);
    }
    userRepository.save(user);
  }

  public UserModel findUser(String name) {
    User user = userRepository.findByNameIgnoreCase(name);
    if (null != user && !name.isEmpty()) {
      UserModel userModel = new UserModel();
      userModel.setName(user.getName());
      userModel.setUserType(user.getType());
      return userModel;
    } else {
      return null;
    }
  }

  public List<String> getUserList(String name) {
    List<User> users = null;
    if (null == name || name.isEmpty()) {
      users = userRepository.findAll();
    } else {
      users = userRepository.findByNameContains(name);
    }

    List<String> userList = new ArrayList<>(users.size());
    if (users != null && !users.isEmpty()) {
      for (User user : users) {
        userList.add(user.getName());
      }
    }
    return userList;
  }
}
