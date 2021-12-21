package com.clientui.clientui.controller;


import com.clientui.clientui.dto.AssessmentDTO;
import com.clientui.clientui.proxies.AssessmentMicroserviceProxy;
import com.clientui.clientui.service.IAssessmentService;
import feign.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AssessmentController {

    @Autowired
    IAssessmentService assessmentService;

    @Autowired
    AssessmentMicroserviceProxy assessmentMicroserviceProxy;

    private static Logger logger = LogManager.getLogger(PatientController.class);

    private String message;

    //---------Get----- /report/list ---------------------------------------------------------------------------
    @GetMapping("/report/list")
    public String getAssessments(Model model, @Param("stringSearch")  String stringSearch,
                              @RequestParam(name="message", defaultValue = "") String message){
        logger.info(" ----> Launch /report/list ");
        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        if(!stringSearch.isBlank()) {
            assessmentDTOS = assessmentService.getAssessPatientStartingFamilyNameWith(stringSearch);
            System.out.println("--- nb resultat in assessmentDTOS " + assessmentDTOS.size());
        }
        model.addAttribute("message", message);
        model.addAttribute("assessmentDTOS", assessmentDTOS);
        model.addAttribute("stringSearch", stringSearch);
        return "report/list";
    }

    //---------Get----- /patReport/ ---------------------------------------------------------------------------
    @GetMapping("/patReport/{patientId}")
    public String getAssessmentPatient(@PathVariable("patientId") Integer patientId, Model model,
                                       @RequestParam(name="message", defaultValue = "") String message){
        logger.info(" ----> Launch /patReport/{id} with id: " + patientId);
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        //if(!patientId.equals(null)) {
            assessmentDTO = assessmentService.getAssessPatientById(patientId);
        //}
        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        assessmentDTOS.add(assessmentDTO);
        model.addAttribute("message", message);
        model.addAttribute("assessmentDTOS", assessmentDTOS);
        return "report/patReport";
    }
}
