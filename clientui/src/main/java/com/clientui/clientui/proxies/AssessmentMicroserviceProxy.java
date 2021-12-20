package com.clientui.clientui.proxies;

import com.clientui.clientui.dto.AssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="Assessment-microservice", url="localhost:8083")
public interface AssessmentMicroserviceProxy {

    @GetMapping("/assess/id/{id}")
    String getAssessmentForPatientByIdPatient(@PathVariable(name="id") Integer patientId);


    @GetMapping("/assess/familyName/{familyName}")
    public List<AssessmentDTO> getAssessmentForPatientWithFamilyName(@PathVariable(name="familyName") String familyName);

}
