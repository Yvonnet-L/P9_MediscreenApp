package com.mediscreen.historymicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patienthistory")
public class PatientHistory {

    @Id
    private String id;

    private Integer patientId;

    private LocalDate date;

    private String notes;


    public PatientHistory(Integer patientId, LocalDate date, String notes) {
        this.patientId = patientId;
        this.date = date;
        this.notes = notes;
    }
}
