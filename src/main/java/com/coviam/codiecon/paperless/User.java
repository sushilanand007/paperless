package com.coviam.codiecon.paperless;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME", unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  private UserType type;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }
}
