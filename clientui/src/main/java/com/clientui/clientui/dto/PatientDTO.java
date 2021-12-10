package com.clientui.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class PatientDTO {

    private Integer id;

    @NotBlank(message = "familyName is mandatory")
    @Pattern(regexp ="[a-zA-Z\\+\\-\\+]{2,40}",
            message = "the name must contain at least 2 characters and max 40 without number ")
    private String familyName;

    @NotBlank(message = "givenName is mandatory")
    @Pattern(regexp ="[a-zA-Z\\+\\-\\+]{2,40}",
            message = "the name must contain at least 2 characters and max 40 without number")
    private String givenName;

    @NotNull(message = "Date Of Birth is mandatory")
    private String dateOfBirth;

    @NotNull(message = "Sex is mandatory")
    private String sex;

    @NotBlank(message = "address is mandatory")
    @Length( min = 2, max = 200 , message = "the address must contain at least 2 characters and max 200")
    private String address;

    @NotBlank(message = "phone is mandatory")
    @Pattern(regexp ="\\d{3}-\\d{3}-\\d{4}",
            message = "the phone number sould be formated xxx-xxx-xxxx")
    private String phone;
}
