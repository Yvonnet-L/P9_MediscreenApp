package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.dto.PatientDTO;

import java.util.List;

public interface IPatientService {

    List<PatientDTO> findAll();

    PatientDTO findPatientById(Integer id);

    PatientDTO findPatientByFamilyName(String familyName);

    List<PatientDTO> findByFamilyNameStartingWith(String familyName);

    PatientDTO findById(Integer id);
}
