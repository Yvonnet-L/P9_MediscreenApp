package com.mediscreen.assessmentmicroservice.proxies;

import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Class allowing connection with the PatientMicroservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */
//@FeignClient(name="patient-microservice", url="localhost:8081") // ligne non Docker
@FeignClient(name="patient-microservice", url="patient-ms:8081") // ligne Docker
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


    /** ---------------------------------------------------------------------------------------------
     *  Connection to the uri which returns the list of patients found whose last name begins with
     *  the transmitted variable, if the variable is empty returns all patients
     * @param familyName
     * @return List<Patient>
     */
    @GetMapping(value="/patients/family/{familyName}")
    List<PatientDTO> getPatientsStartingWith(@PathVariable(name = "familyName") String familyName);
}
