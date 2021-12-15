package com.clientui.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientHistoryDTO {


    private String id;

    @NotNull(message = "familyName is mandatory")
    private Integer patientId;

    @NotBlank(message = "Date is mandatory")
    private String date;

    @NotNull(message = "Notes is mandatory")
    private String notes;



    public PatientHistoryDTO() {
    }

    public PatientHistoryDTO(Integer patientId, LocalDate now, String s) {
    }
}
