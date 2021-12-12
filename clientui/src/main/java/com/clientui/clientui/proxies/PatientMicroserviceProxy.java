package com.clientui.clientui.proxies;


import com.clientui.clientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Allows the connection between the application and the patient-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */
@FeignClient(name="patient-microservice", url="localhost:7071")
public interface PatientMicroserviceProxy {

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which returns the list of patients
     * @return List<PatientDTO>
     */
    @GetMapping("/patients")
    List<Patient> getAllPatients();

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which returns the patient found by his id
     * @param id
     * @return Patient
     */
    @GetMapping(value="/patient/{id}")
    Patient getPatientById(@PathVariable(name = "id") Integer id);

    /** ---------------------------------------------------------------------------------------------
     *  Connection to the uri which returns the list of patients found whose last name begins with
     *  the transmitted variable, if the variable is empty returns all patients
     * @param familyName
     * @return List<Patient>
     */
    @GetMapping(value="/patients/family/{familyName}")
    List<Patient> getPatientsStartingWith(@PathVariable(name = "familyName") String familyName);

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
    Patient addPatient(@Valid @RequestBody Patient patient);

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which allows the update of the patient
     * @param id
     * @param patient
     * @return Patient
     */
    @PutMapping(value="/patient/update/{id}")
    public Patient updatePatient(@PathVariable("id") Integer id,@Valid @RequestBody Patient patient);
}
