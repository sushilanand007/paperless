package com.coviam.codiecon.paperless;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    User user = userRepository.findByName(name);
    if (null != user) {
      UserModel userModel = new UserModel();
      userModel.setName(user.getName());
      userModel.setUserType(user.getType());
      return userModel;
    } else {
      return null;
    }
  }

}
