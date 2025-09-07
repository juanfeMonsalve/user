package com.bci.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String number;
    private int citryCode;
    private String countryCode;
}
