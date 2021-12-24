package com.clientui.clientui.proxies;

import com.clientui.clientui.dto.AssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Allows the connection between the application and the assessement-microservice
 * Feign is an http client which greatly facilitates the calling of APIs.
 */

//@FeignClient(name="Assessment-microservice", url="localhost:8083") // ligne non Docker
@FeignClient(name="patient-microservice", url="assessment-ms:8083") // ligne Docker
public interface AssessmentMicroserviceProxy {

    @GetMapping("/assess/id/{id}")
    AssessmentDTO getAssessmentForPatientByIdPatient(@PathVariable(name="id") Integer patientId);


    @GetMapping("/assess/familyName/{familyName}")
    List<AssessmentDTO> getAssessmentForPatientWithFamilyName(@PathVariable(name="familyName") String familyName);

}
