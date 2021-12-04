package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.dto.PatientDTO;

import java.util.List;

public interface IPatientService {

    List<PatientDTO> findAll();

    List<PatientDTO> findByFamilyNameStartingWith(String familyName);

    PatientDTO findById(Integer id);

    PatientDTO addPatient(PatientDTO patientDTO);

    PatientDTO updatePatient(Integer id, PatientDTO patientDTO);

    String deletePatient(Integer id);
}
