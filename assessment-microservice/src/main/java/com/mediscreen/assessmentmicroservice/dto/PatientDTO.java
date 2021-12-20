package com.mediscreen.assessmentmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object of the Patient class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Integer id;

    private String familyName;

    private String givenName;

    private String dateOfBirth;

    private String sex;

    private String address;

    private String phone;
}

