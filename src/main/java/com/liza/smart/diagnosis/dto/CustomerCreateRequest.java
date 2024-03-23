package com.liza.smart.diagnosis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
