package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<Patient> findAll();

    Patient findPatientById(Integer id);

    Patient findPatientByFamillyName(String famillyName);

    Optional<Patient> findById(Integer id);
}
