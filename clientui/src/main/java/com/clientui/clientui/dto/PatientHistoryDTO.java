package com.clientui.clientui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientHistoryDTO {


    private String id;

    private Integer patientId;

    private String date;

    private String notes;



    public PatientHistoryDTO() {
    }

    public PatientHistoryDTO(Integer patientId, LocalDate now, String s) {
    }
}
