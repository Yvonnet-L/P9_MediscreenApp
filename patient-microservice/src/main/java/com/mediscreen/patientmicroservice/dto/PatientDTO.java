package com.mediscreen.patientmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data Transfer Object of the Patient class with the input constraints,
 * used for validation of incoming data
 * @See Patient
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Integer id;
    @NotBlank(message = "familyName is mandatory")
    private String familyName;
    @NotBlank(message = "givenName is mandatory")
    private String givenName;
    @NotNull(message = "Date Of Birth is mandatory")
    private LocalDate dateOfBirth;
    private String sex;
    private String address;
    private String phone;

    //----------Constructor-----------------------------------------------------------------------------------


    public PatientDTO(String familyName, String givenName, LocalDate dateOfBirth, String sex, String address, String phone) {
        this.familyName = familyName;
        this.givenName = givenName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }
}
