package com.clientui.clientui.proxies;


import com.clientui.clientui.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Allows the connection between the application and the patient-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */
//@FeignClient(name="patient-microservice", url="localhost:8081") // ligne non docker
@FeignClient(name="patient-microservice", url="patient-ms:8081") // ligne Docker
public interface PatientMicroserviceProxy {

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which returns the list of patients
     * @return List<PatientDTO>
     */
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

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which allows the deletion of the patient by his id
     * @param id
     */
    @DeleteMapping(value="/patient/delete/{id}")
    void deletePatient(@PathVariable("id") Integer id);

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which allows the creation of the patient
     * @param patient
     * @return Patient
     */
    @PostMapping(value="/patient/add")
    PatientDTO addPatient(@Valid @RequestBody PatientDTO patientDTO);

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which allows the update of the patient
     * @param id
     * @param patientDTO
     * @return PatientDTO
     */
    @PutMapping(value="/patient/update/{id}")
    PatientDTO updatePatient(@PathVariable("id") Integer id,@Valid @RequestBody PatientDTO patientDTO);
}
