package com.clientui.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientHistoryDTO {

    private String id;

    @NotNull(message = "familyName is mandatory")
    private Integer patientId;

    @NotBlank(message = "Date is mandatory")
    private String date;

    @NotBlank(message = "Notes is mandatory")
    @Length( min = 2, max = 5000 , message = "the address must contain at least 2 characters and max 5000")
    private String notes;


    public PatientHistoryDTO(Integer patientId, String date, String notes) {
        this.patientId = patientId;
        this.date = date;
        this.notes = notes;
    }
}
