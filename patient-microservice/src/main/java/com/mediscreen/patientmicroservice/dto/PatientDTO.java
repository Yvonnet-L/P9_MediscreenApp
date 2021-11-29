package com.mediscreen.patientmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Integer id;
    private String familyName;
    private String givenName;
    private LocalDate dateOfBirth;
    private String sex;
    private String address;
    private String phone;

    //----------Constructor-----------------------------------------------------------------------------------
}
