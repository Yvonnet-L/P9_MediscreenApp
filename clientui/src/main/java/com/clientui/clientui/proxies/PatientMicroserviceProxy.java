package com.clientui.clientui.proxies;


import com.clientui.clientui.beans.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="patient-microservice", url="localhost:7071")
public interface PatientMicroserviceProxy {

    /**
     *
     * @return List<PatientDTO>
     */
    @GetMapping("/patients")
    List<Patient> getAllPatients();

}
