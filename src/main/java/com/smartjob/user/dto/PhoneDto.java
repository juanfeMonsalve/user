package com.smartjob.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PhoneDto {
    UUID id;
    String number;
    String citycode;
    String contrycode;
}
