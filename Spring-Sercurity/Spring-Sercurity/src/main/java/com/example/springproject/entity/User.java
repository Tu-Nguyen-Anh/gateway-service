package com.example.springproject.entity;

import com.example.springproject.entity.base.BaseEntityWithUpdater;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity class representing a user in the system.
 * Extends the BaseEntityWithUpdater class to inherit common fields for auditing.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
@Builder
public class User extends BaseEntityWithUpdater {
  @Column(name = "username", unique = true)
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "email")
  private String email;
  @Column(name = "phone")
  private String phone;
  @Column(name = "dateofbirth")
  private Date dateOfBirth;
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String username, String password, String email, String phone, Date dateOfBirth) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
    this.dateOfBirth = dateOfBirth;
    this.role = Role.USER;
  }
}
