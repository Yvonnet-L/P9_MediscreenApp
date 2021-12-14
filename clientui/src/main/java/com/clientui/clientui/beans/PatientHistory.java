package com.clientui.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 *  Model to collect PatientHistory-microservice data
 */
@Getter
@Setter
@AllArgsConstructor
public class PatientHistory {

    private String id;

    private Integer patientId;

    private LocalDate date;

    private String notes;

    public PatientHistory() {
    }
}
