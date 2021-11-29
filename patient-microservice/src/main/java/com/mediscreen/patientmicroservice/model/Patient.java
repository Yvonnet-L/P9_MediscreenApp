package com.mediscreen.patientmicroservice.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Patients")
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
    @Column(name= "id", unique = true, nullable = false)
    private int id;

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

    /**
     * ----------- Getters / Setters -------------------------------------------------------------------------------
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
