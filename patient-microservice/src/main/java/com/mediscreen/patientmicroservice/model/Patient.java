package com.mediscreen.patientmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="patients")
public class Patient {

    /**
     * Les informations personnelles des patients sont :
     * - prénom
     * - nom
     * - date de naissance
     * - genre
     * - adresse postale
     * - numéro de téléphone
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Integer idPatient;

    @Column(name= "family", length = 40, nullable = false)
    private String familyName;

    @Column(name= "given", length = 40, nullable = false)
    private String givenName;

    @Column(name= "dob", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name="sex", columnDefinition = "enum('F','M')")
    private String sex;

    @Column(name="address", length = 200)
    private String address;

    @Column(name="phone", length = 20)
    private String phone;

}
