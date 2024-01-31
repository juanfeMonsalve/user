package com.smartjob.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "phone")
@Setter
@Getter
public class Phone {
    @Id
    @Column(name = "id")
    @GeneratedValue
    UUID id;
    @Column(name = "number")
    String number;
    @Column(name = "citycode")
    String citycode;
    @Column(name = "countrycode")
    String contrycode;
    @ManyToOne(cascade = CascadeType.ALL)
    User user;
}
