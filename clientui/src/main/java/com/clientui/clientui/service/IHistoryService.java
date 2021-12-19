package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientHistoryDTO;

import java.util.List;


public interface IHistoryService {

    List<PatientHistoryDTO> findAllPatientHistories();

    List<PatientHistoryDTO> findPatientHistoriesNotes(Integer patientId);

    PatientHistoryDTO findPatientHistoryById(String id);

    String addPatientHistory(PatientHistoryDTO patientHistoryDTO);

    String deletePatientHistory(String id);

    String updatePatientHistory(String id,PatientHistoryDTO patientHistoryDTO);

}
