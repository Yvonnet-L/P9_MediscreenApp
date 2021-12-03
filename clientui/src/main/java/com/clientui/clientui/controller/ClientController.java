package com.clientui.clientui.controller;

import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.proxies.PatientMicroserviceProxy;
import com.clientui.clientui.service.IPatientService;
import com.clientui.clientui.service.PatientServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    IPatientService patientService;

    private static Logger logger = LogManager.getLogger(ClientController.class);
/**
    @RequestMapping("/")
    public String acceuil(Model model){
        System.out.println("----- > dans le controller !!");
        return "Hello";
    }
*/
    @GetMapping("/patients")
    public String getPatients(Model model){
        logger.info(" ----> Launch /patients getPatients()");
        List<PatientDTO> patientDTOS = patientService.getAllPatients();
        model.addAttribute("patientDTOS", patientDTOS);
        return "Acceuil";
    }
}