package com.smartjob.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_bank")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    UUID id;
    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @OneToMany(cascade = CascadeType.ALL)
    List<Phone> phones;
    @Column(name = "created")
    LocalDateTime created;
    @Column(name = "modified")
    LocalDateTime modified;
    @Column(name = "last_login")
    LocalDateTime lastLogin;
    @Column(name = "token")
    String token;
    @Column(name = "is_active")
    Boolean isActive;
}
