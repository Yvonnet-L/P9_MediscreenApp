package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientService {

    List<PatientDTO> findAll();

    PatientDTO findPatientById(Integer id);

    PatientDTO findPatientByFamilyName(String familyName);

    List<PatientDTO> findByFamilyNameStartingWith(String familyName);

    PatientDTO findById(Integer id);
}
