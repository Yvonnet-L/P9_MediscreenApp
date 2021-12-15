package com.mediscreen.historymicroservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 *  Class allowing the link with the PatientHistory table of the database
 *  mongoDB named mediscreen
 */

@Data
@Getter
@Setter
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
