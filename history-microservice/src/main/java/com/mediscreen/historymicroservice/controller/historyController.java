package com.mediscreen.historymicroservice.controller;

import com.mediscreen.historymicroservice.model.PatientHistory;
import com.mediscreen.historymicroservice.repository.HistoryRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Hystory patient management ")
@RestController
public class historyController {

    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("/patHistories")
    public List<PatientHistory> getAllPatientHistories(){
        List<PatientHistory> patientHistoryList = historyRepository.findAll();
        return patientHistoryList;
    }

    @GetMapping("/patHistory/patientId/{patientId}")
    public List<PatientHistory> getPatientHistoryBypatientId(@PathVariable(name = "patientId") Integer patientId){
        List<PatientHistory> patientHistoryList = historyRepository.findAllByPatientId(patientId);
        return patientHistoryList;
    }

    @PostMapping("/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientHistory addPatients(@RequestBody PatientHistory patientHistory){
        //PatientHistory patientHistory = new PatientHistory(12, LocalDate.now(),"Le patient est atteind de troubles du sommeil");
        PatientHistory patientHistoryAdd = historyRepository.save(patientHistory);
        return patientHistoryAdd;
    }
}
