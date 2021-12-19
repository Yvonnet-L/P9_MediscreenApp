package com.mediscreen.assessmentmicroservice.proxies;

import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Allows the connection between the application and the patient-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */
@FeignClient(name="patient-microservice", url="localhost:8081")
public interface PatientMicroserviceProxy {

    @GetMapping("/patients")
    List<PatientDTO> getAllPatients();

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which returns the patient found by his id
     * @param id
     * @return Patient
     */
    @GetMapping(value="/patient/{id}")
    PatientDTO getPatientById(@PathVariable(name = "id") Integer id);
}
