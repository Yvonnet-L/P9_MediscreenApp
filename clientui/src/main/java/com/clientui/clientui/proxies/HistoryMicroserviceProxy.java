package com.clientui.clientui.proxies;

import com.clientui.clientui.beans.PatientHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Allows the connection between the application and the history-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */

@FeignClient(name="history-microservice", url="localhost:8082")
public interface HistoryMicroserviceProxy {


    /**
     * Connection to the uri which returns the list of patients histories notes
     * @return List<PatientHistory>
     */
    @GetMapping("/patHistories")
    List<PatientHistory> getAllPatientHistories();

    /**
     * Connection to the uri which returns the History Patient Notes found by his id
     * @param patientId
     * @return  List<PatientHistory>
     */
    @GetMapping("/patHistory/patientId/{patientId}")
    List<PatientHistory> getPatientHistoryBypatientId(@PathVariable(name = "patientId") Integer patientId);

    @PostMapping("/patHistory/add")
    PatientHistory addPatients(@RequestBody PatientHistory patientHistory);
}
