package com.mediscreen.assessmentmicroservice.proxies;


import com.mediscreen.assessmentmicroservice.dto.PatientHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="history-microservice", url="localhost:8082")
public interface HistoryMicroserviceProxy {

    /**
     * Connection to the uri which returns the list of patients histories notes
     * @return List<PatientHistory>
     */
    @GetMapping("/patHistories")
    List<PatientHistoryDTO> getAllPatientHistories();

    /**
     * Connection to the uri which returns the History Patient Notes found by his id
     * @param patientId
     * @return  List<PatientHistory>
     */
    @GetMapping("/patHistories/{patientId}")
    List<PatientHistoryDTO> getPatientHistoryBypatientId(@PathVariable(name = "patientId") Integer patientId);
}
