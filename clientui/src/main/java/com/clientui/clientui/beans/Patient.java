package com.clientui.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 *  Model to collect patient-microservice data
 */
@Getter
@Setter
@AllArgsConstructor
public class Patient {

    private Integer id;
    private String familyName;
    private String givenName;
    private LocalDate dateOfBirth;
    private String sex;
    private String address;
    private String phone;
}
