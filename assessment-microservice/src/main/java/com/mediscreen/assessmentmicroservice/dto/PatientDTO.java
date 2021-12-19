package com.mediscreen.assessmentmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object of the Patient class with the input constraints,
 * used for validation of incoming data
 */
@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {

    private Integer id;

    private String familyName;

    private String givenName;

    private String dateOfBirth;

    private String sex;

    private String address;

    private String phone;
}

