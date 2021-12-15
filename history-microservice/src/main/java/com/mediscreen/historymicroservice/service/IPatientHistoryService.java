package com.mediscreen.historymicroservice.service;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;

import java.util.List;

/**
 *  PatientHistoryService Interface
 */
public interface IPatientHistoryService {

    List<PatientHistoryDTO> findAll();

    PatientHistoryDTO findById(String id);

    List<PatientHistoryDTO> findAllByPatientId(Integer patientId);

    PatientHistoryDTO addPatientHistory(PatientHistoryDTO patientHistoryDTO);

    PatientHistoryDTO updatePatientHistory(Integer id, PatientHistoryDTO patientHystoryDTO);

    void deletePatientHistory(String id);
}
