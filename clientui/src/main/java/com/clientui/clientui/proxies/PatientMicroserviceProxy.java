package com.clientui.clientui.proxy;


import com.clientui.clientui.bean.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="patient-microservice", url="localhost:7071")
public class PatientMicroserviceProxy {

    @GetMapping("/patients")
    List<PatientDTO> getAllPatients();
}
