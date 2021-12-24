package com.mediscreen.assessmentmicroservice.controller;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.service.IAssessmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Assessment management by creating REST endpoints
 */
@Api(description = "Assessment management ")
@RestController
public class AssessmentController {


    @Autowired
    IAssessmentService assessmentService;

    private static Logger logger = LogManager.getLogger(AssessmentController.class);


    /** ------ Get -- Home Page ---------------------------------------------------------------------------------------
     * Return a welcome message
     * @return string welcome
     */
    @ApiOperation(value="Return home page")
    @GetMapping("/")
    public String getHome(){;
        logger.info(" ---> getHome ");
        return "Welcone on Assessment-Microservice";
    }

    /** ------- Get ---- getAssessmentForPatientByIdPatient ------------------------------------------------------------
     * Return assessment Diabete of patient find by his id
     *
     * @param patientId
     * @return AssessmentDTO
     */
    @ApiOperation(value="Result Assessment for a patient by his id")
    @GetMapping("/assess/id/{id}")
    public AssessmentDTO getAssessmentForPatientByIdPatient(@PathVariable(name="id") Integer patientId){
        logger.info(" ---> /assess/id/{id} - getAssessmentForPatientByIdPatient");
        return assessmentService.getDiabeteAssessmentByIdPatient(patientId);
    }

    /** ------- Get ---- getAssessmentForPatientWithFamilyName ---------------------------------------------------------
     * Return the list of diabetes assessments for all patients found by their common lastname
     *
     * @param familyName
     * @return List<AssessmentDTO>
     */
    @ApiOperation(value="Result Assessment for a patient by FamilyName")
    @GetMapping("/assess/familyName/{familyName}")
    public List<AssessmentDTO> getAssessmentForPatientWithFamilyName(@PathVariable(name="familyName") String familyName){
        logger.info(" ---> /assess/familyName/{familyName} - getAssessmentForPatientWithFamilyName");
        return assessmentService.getDiabeteAssessmentByFamilyName(familyName);
    }

}
