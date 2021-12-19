package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientHistoryDTO;

import java.util.List;

public interface IHistoryService {
    /**
     *     @GetMapping("/patHistories/{patientId}")
     *     List<PatientHistoryDTO> getPatientHistoryBypatientId(@PathVariable(name = "patientId") Integer patientId);
     *
     *     @PostMapping("/patHistory/add")
     *     PatientHistoryDTO addPatientHistory(@Valid @RequestBody PatientHistoryDTO patientHistoryDTO);
     *
     *     @DeleteMapping(value="/patHistory/delete/{id}")
     *     void deletePatientHistory(@PathVariable("id") String id);
     *
     *     @GetMapping("/patHistory/{id}")
     *     PatientHistoryDTO getPatientHistoryById(@PathVariable(name = "id") String id);
     *
     *     @PutMapping("/patHistory/update/{id}")
     *     PatientHistoryDTO updatePatientHistory(@PathVariable("id") String id,@Valid @RequestBody PatientHistoryDTO patientHistoryDTO);
     */


    List<PatientHistoryDTO> findAllPatientHistories();

    List<PatientHistoryDTO> findPatientHistoriesNotes(Integer patientId);

    PatientHistoryDTO findPatientHistoryById(String id);

    String addPatientHistory(PatientHistoryDTO patientHistoryDTO);

    String deletePatientHistory(String id);

    String updatePatientHistory(String id,PatientHistoryDTO patientHistoryDTO);

}
