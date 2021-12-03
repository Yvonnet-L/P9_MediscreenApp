package com.clientui.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {

    private Integer id;
    private String familyName;
    private String givenName;
    private LocalDate dateOfBirth;
    private String sex;
    private String address;
    private String phone;
}
