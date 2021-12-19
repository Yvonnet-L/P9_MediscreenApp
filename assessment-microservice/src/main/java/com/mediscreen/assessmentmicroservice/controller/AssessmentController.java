package com.mediscreen.assessmentmicroservice.controller;

import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientHistoryDTO;
import com.mediscreen.assessmentmicroservice.proxies.HistoryMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.proxies.PatientMicroserviceProxy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "patient management ")
@RestController
public class AssessmentController {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

   private static Logger logger = LogManager.getLogger(AssessmentController.class);

    @ApiOperation(value="Return home page")
    @GetMapping("/")
    public String getHome(){
        return "Welcone on Assessment-Microservice";
    }
    ///-----------------------------------------------------------------------------------------------------
    @ApiOperation(value="All HistoriesPatiens")
    @GetMapping("/Histories")
    public List<PatientHistoryDTO> geHistories(){
        return historyMicroserviceProxy.getAllPatientHistories();
    }

    @GetMapping("/patHistories/{patientId}")
    public List<PatientHistoryDTO> getPatientHistory(@PathVariable(name="patientId") Integer patientId){
        return historyMicroserviceProxy.getPatientHistoryBypatientId(patientId);
    }
    ///-----------------------------------------------------------------------------------------------------
    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients(){
        return patientMicroserviceProxy.getAllPatients();
    }

    /** ---------------------------------------------------------------------------------------------
     * Connection to the uri which returns the patient found by his id
     * @param id
     * @return Patient
     */
    @GetMapping(value="/patient/{id}")
    public PatientDTO getPatientById(@PathVariable(name = "id") Integer id){
        return patientMicroserviceProxy.getPatientById(id);
    }
}
