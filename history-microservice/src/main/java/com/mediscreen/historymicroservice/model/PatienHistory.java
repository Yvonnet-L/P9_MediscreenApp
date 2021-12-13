package com.mediscreen.historymicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatienHistory {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer patientId;

    private LocalDate date;

    private String notes;
}
