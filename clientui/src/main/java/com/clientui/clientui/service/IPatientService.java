package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientDTO;

import java.util.List;

public interface IPatientService {

    List<PatientDTO> getAllPatients();

    List<PatientDTO> getPatientStartingFamilyNameWith(String stringSearch);

    PatientDTO getById(Integer id);

    void deletePatientById(Integer id);

    String addPatient(PatientDTO patientDTO);

    String udatePatient(PatientDTO patientDTO, Integer id);
}
