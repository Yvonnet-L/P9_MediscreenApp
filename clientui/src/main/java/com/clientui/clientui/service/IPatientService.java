package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientDTO;

import java.util.List;

/**
 *  Patient Service Interface
 */
public interface IPatientService {

    List<PatientDTO> getAllPatients();

    List<PatientDTO> getPatientStartingFamilyNameWith(String stringSearch);

    PatientDTO getById(Integer id);

    void deletePatientById(Integer id);

    String addPatient(PatientDTO patientDTO);

    String updatePatient(PatientDTO patientDTO, Integer id);
}
