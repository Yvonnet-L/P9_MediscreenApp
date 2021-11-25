package com.mediscreen.patientmicroservice.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    @Column(name= "id_patient", nullable = false)
    private int id;

    @Column(name= "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name= "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name= "bird_of_date", nullable = false)
    private LocalDate bod;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBod() {
        return bod;
    }

    public void setBod(LocalDate bod) {
        this.bod = bod;
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
