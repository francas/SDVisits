package com.example.application.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="specialist")
public class Specialist extends AbstractEntity implements Cloneable {

  @NotNull
  @NotEmpty
  private String firstName = "";

  @NotNull
  @NotEmpty
  private String lastName = "";


  @Email
  @NotNull
  @NotEmpty
  private String email = "";

  @NotNull
  @NotEmpty
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
