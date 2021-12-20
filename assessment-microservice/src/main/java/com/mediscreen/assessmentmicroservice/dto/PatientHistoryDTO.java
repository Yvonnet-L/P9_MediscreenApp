package com.mediscreen.assessmentmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



/**
 * Data Transfer Object of the PatientHistory class
 */
@Getter
@Setter
@AllArgsConstructor
public class PatientHistoryDTO {

    private String id;

    private Integer patientId;

    private String date;

    private String notes;
}
