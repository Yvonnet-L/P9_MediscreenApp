package com.mediscreen.assessmentmicroservice.proxies;

/**
 * Class allowing connection with the HistoryMicroservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */

import com.mediscreen.assessmentmicroservice.dto.PatientHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name="history-microservice", url="localhost:8082") // ligne non Docker
@FeignClient(name="patient-microservice", url="history-ms:8082") // ligne Docker
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
