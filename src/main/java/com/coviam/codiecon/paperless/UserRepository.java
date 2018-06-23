package com.coviam.codiecon.paperless;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByNameIgnoreCase(String name);

  @Query("SELECT user FROM User user WHERE LOWER(user.name) like CONCAT('%',LOWER(:name),'%')")
  List<User> findByNameContains(String name);

}
