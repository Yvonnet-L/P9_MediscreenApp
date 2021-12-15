package com.mediscreen.historymicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Data Transfer Object of the PatientHistory class with the input constraints,
 * used for validation of incoming data
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientHistoryDTO {

    private String id;
    @NotBlank(message = "patientId is mandatory")
    private Integer patientId;
    @NotNull(message = "Date is mandatory")
    private LocalDate date;
    @NotBlank(message = "Notes is mandatory")
    private String notes;

    //----------Constructor--------------------------------------------------------------------------------

    public PatientHistoryDTO(Integer patientId, LocalDate date, String notes) {
        this.patientId = patientId;
        this.date = date;
        this.notes = notes;
    }
}
