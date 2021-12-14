package com.clientui.clientui.controller;


import com.clientui.clientui.beans.PatientHistory;
import com.clientui.clientui.dto.PatientHistoryDTO;
import com.clientui.clientui.proxies.HistoryMicroserviceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    @GetMapping("/patHistories")
    public String getAllPatientHistories(Model model){
        List<PatientHistory> patientHistories =  historyMicroserviceProxy.getAllPatientHistories();
        model.addAttribute("patientHistories", patientHistories);
        return "patHistory/patientHistories";
    }

    @GetMapping("/patHistory/{patientId}")
    public String getPatientHistory(@PathVariable("patientId") Integer patientId, Model model){
        List<PatientHistory> patientHistories =  historyMicroserviceProxy.getPatientHistoryBypatientId(patientId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("patientHistories", patientHistories);
        return "patHistory/patientNotes";
    }

    @GetMapping("/patHistory/add/{Id}")
    public String getAddPatientNote(@PathVariable("Id") Integer patientId, Model model){
        PatientHistoryDTO patientHistory = new PatientHistoryDTO(patientId, LocalDate.now(),"");
        model.addAttribute("patientId", patientId);
        patientHistory.setPatientId(patientId);
        model.addAttribute("patientHistoryDTO", patientHistory);
        return "patHistory/add";
    }

    @PostMapping("/patHistory/validate")
    public String validate(PatientHistoryDTO patientHistoryDTO, Model model) {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPatientId(patientHistoryDTO.getPatientId());
        patientHistory.setDate(LocalDate.parse(patientHistoryDTO.getDate()));
        patientHistory.setNotes(patientHistoryDTO.getNotes());

        historyMicroserviceProxy.addPatients(patientHistory);
        return "redirect:/patHistory/"+patientHistoryDTO.getPatientId();
        //return "/patHistory/patientId/1";
    }

}
