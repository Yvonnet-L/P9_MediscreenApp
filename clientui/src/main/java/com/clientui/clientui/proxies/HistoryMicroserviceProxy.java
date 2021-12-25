package com.clientui.clientui.proxies;

import com.clientui.clientui.dto.PatientHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Allows the connection between the application and the history-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */

//@FeignClient(name="history-microservice", url="localhost:8082")  // ligne non Docker
@FeignClient(name="history-microservice", url="history-ms:8082") // ligne Docker
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

    @PostMapping("/patHistory/add")
    PatientHistoryDTO addPatientHistory(@Valid @RequestBody PatientHistoryDTO patientHistoryDTO);

    @DeleteMapping(value="/patHistory/delete/{id}")
    void deletePatientHistory(@PathVariable("id") String id);

    @GetMapping("/patHistory/{id}")
    PatientHistoryDTO getPatientHistoryById(@PathVariable(name = "id") String id);

    @PutMapping("/patHistory/update/{id}")
    PatientHistoryDTO updatePatientHistory(@PathVariable("id") String id,@Valid @RequestBody PatientHistoryDTO patientHistoryDTO);


}
