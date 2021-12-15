package com.clientui.clientui.controller;


import com.clientui.clientui.beans.PatientHistory;
import com.clientui.clientui.dto.PatientHistoryDTO;
import com.clientui.clientui.proxies.HistoryMicroserviceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Controller
public class HistoryController {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    private static Logger logger = LogManager.getLogger(HistoryController.class);

    /** ------- Get ---- getAllPatientHistories ---------------------------------------------------------
     * Retrieves and displays the list of all history notes patients
     * @param model
     * @return
     */
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
    public String validate(@Valid PatientHistoryDTO patientHistoryDTO, Model model) {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setPatientId(patientHistoryDTO.getPatientId());
        patientHistory.setDate(LocalDate.parse(patientHistoryDTO.getDate()));
        patientHistory.setNotes(patientHistoryDTO.getNotes());

        historyMicroserviceProxy.addPatientHistory(patientHistory);
        return "redirect:/patHistory/"+patientHistoryDTO.getPatientId();
    }

    //---------Get----- /patient/delete/id  ---------------------------------------------------------------------------
    @GetMapping("/patHistory/delete/{id}&{patientid}")
    public String deletePatient(@PathVariable("id") String id, @PathVariable("patientid") Integer patientId){
        logger.info(" ----> Launch /pathistory/delete/id - note id: " + id + " - - " + patientId);
       historyMicroserviceProxy.deletePatientHistory(id);
        return "redirect:/patHistory/" + patientId;
    }
}
