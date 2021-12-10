package com.clientui.clientui.proxies;


import com.clientui.clientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name="patient-microservice", url="localhost:7071")
public interface PatientMicroserviceProxy {

    /**
     *
     * @return List<PatientDTO>
     */
    @GetMapping("/patients")
    List<Patient> getAllPatients();

    @GetMapping(value="/patient/{id}")
    Patient getPatientById(@PathVariable(name = "id") Integer id);

    @GetMapping(value="/patients/family/{familyName}")
    List<Patient> getPatientsStartingWith(@PathVariable(name = "familyName") String familyName);

    @DeleteMapping(value="/patient/delete/{id}")
    void deletePatient(@PathVariable("id") Integer id);

    @PostMapping(value="/patient/add")
    Patient addPatient(@Valid @RequestBody Patient patient);

    @PutMapping(value="/patient/update/{id}")
    public Patient updatePatient(@PathVariable("id") Integer id,@Valid @RequestBody Patient patient);
}
