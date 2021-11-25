package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;

import java.util.List;

public interface IPatientService {

    List<Patient> findAll();

    Patient findPatientById(Integer id);

    Patient findPatientByFirstName(String firstName);


}
