package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import com.mediscreen.patientmicroservice.service.IPatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    IPatientService patientService;

    private static Logger logger = LogManager.getLogger(PatientController.class);

    @GetMapping("/index")
    public String index(Model model){
        logger.info(" ---> Launch index");
        return "index: Welcome Here !!!!";
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients(Model model){
        logger.info(" ---> Launch getAllPatients");
        List<Patient> patients = patientService.findAll();
        return patients;
    }
}
