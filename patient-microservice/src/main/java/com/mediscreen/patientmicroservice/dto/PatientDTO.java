package com.mediscreen.patientmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
}
