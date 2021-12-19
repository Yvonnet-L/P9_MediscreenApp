package com.clientui.clientui.controller;


import com.clientui.clientui.dto.PatientHistoryDTO;
import com.clientui.clientui.service.IHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
public class HistoryController {

    @Autowired
    IHistoryService historyService;

    private static Logger logger = LogManager.getLogger(HistoryController.class);

    private String message;
    /** ------- Get ---- getAllPatientHistories ---------------------------------------------------------
     * Retrieves and displays the list of all history notes patients
     * @param model
     * @return
     */
    @GetMapping("/patHistories")
    public String getAllPatientHistories(Model model){
        List<PatientHistoryDTO> patientHistoriesDTO =  historyService.findAllPatientHistories();
        model.addAttribute("patientHistories", patientHistoriesDTO);
        return "patHistory/patientHistories";
    }

    /**
     *
     * @param patientId
     * @param model
     * @return
     */
    @GetMapping("/patHistories/{patientId}")
    public String getPatientHistory(@PathVariable("patientId") Integer patientId,
                                    @RequestParam(name="message", defaultValue = "") String message, Model model){
        List<PatientHistoryDTO> patientHistoriesDTO =  historyService.findPatientHistoriesNotes(patientId);
        model.addAttribute("patientId", patientId);
        model.addAttribute("patientHistories", patientHistoriesDTO);
        model.addAttribute("message", message);
        return "patHistory/patientNotes";
    }

    /**
     *
     * @param patientId
     * @param model
     * @return
     */
    @GetMapping("/patHistory/add/{IdPatient}")
    public String getAddPatientNote(@PathVariable("IdPatient") Integer patientId, Model model){
        PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO(patientId, LocalDate.now(),"");
        model.addAttribute("patientId", patientId);
        patientHistoryDTO.setPatientId(patientId);
        model.addAttribute("msgAlertDate", "");
        model.addAttribute("patientHistoryDTO", patientHistoryDTO);
        return "patHistory/add";
    }

    /**
     *
     * @param patientHistoryDTO
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/patHistory/validate")
    public String validateNewPatHistory(@Valid PatientHistoryDTO patientHistoryDTO, BindingResult result, Model model) {
        logger.info(" ----> Launch Post validateNewPatHistory  with PatientId = " + patientHistoryDTO.getPatientId());
        // Gestion de la date si Posterieur à la date du jour
        LocalDate today = LocalDate.now();
        if (Date.valueOf(patientHistoryDTO.getDate()).after(Date.valueOf(LocalDate.now().toString()))){
            model.addAttribute("msgAlertDate","The date cannot be later than today's date");
            return "patHistory/add";
        }else{
            model.addAttribute("msgAlertDate", "");
        }
        if(result.hasErrors()) {
            model.addAttribute("patientHistoryDTO", patientHistoryDTO);
            return "patHistory/add";
        }
        message = historyService.addPatientHistory(patientHistoryDTO);
        return "redirect:/patHistories/"+patientHistoryDTO.getPatientId()+"?message="+ message;
    }
    //-------- Get ---- /patHistory/update/{id} ---------------------------------------------------------------------
    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/patHistory/update/{id}&{patientid}")
    public String getUpdatePatHistory(@PathVariable("id") String id, @PathVariable("patientid") Integer patientId,
                                                            Model model){
        logger.info(" ----> Launch Get /patHistory/update/{id} with id=" + id);
        PatientHistoryDTO patientHistoryDTO = historyService.findPatientHistoryById(id);
        if(patientHistoryDTO==null){
            message="Note with id "+id+" not found ! ";
            return "redirect:/patHistories/"+patientId+"?message="+ message;
        }
        model.addAttribute("patientHistoryDTO", patientHistoryDTO);
        return "patHistory/update";
    }
    //-------- Put ---- /patHistory/update/{id} --------------------------------------------------------------------

    /**
     *
     * @param id
     * @param patientHistoryDTO
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/patHistory/update/{id}")
    public String updatePatHistory(@PathVariable("id") String id, @Valid PatientHistoryDTO patientHistoryDTO,
                                                    BindingResult result, Model model){

        logger.info(" ----> Launch Put updatePatientHistory  with PatientId = " + patientHistoryDTO.getPatientId());
        // Gestion de la date si Posterieur à la date du jour
        LocalDate today = LocalDate.now();
        if (Date.valueOf(patientHistoryDTO.getDate()).after(Date.valueOf(LocalDate.now().toString()))){
            model.addAttribute("msgAlertDate", "The date cannot be later than today's date");
            return "patHistory/update";
        }else{
            model.addAttribute("msgAlertDate", "");
        }
        if(result.hasErrors()) {
            model.addAttribute("patientHistoryDTO", patientHistoryDTO);
            return "patHistory/update";
        }
            message = historyService.updatePatientHistory(id, patientHistoryDTO);
        return "redirect:/patHistories/"+patientHistoryDTO.getPatientId()+"?message="+ message;
        //return "redirect:/patHistories/" + patientHistoryDTO.getPatientId();
    }
    //---------Get----- /patient/delete/id  ---------------------------------------------------------------------------

    /**
     *
     * @param id
     * @param patientId
     * @return
     */
    @GetMapping("/patHistory/delete/{id}&{patientid}")
    public String deletePatient(@PathVariable("id") String id, @PathVariable("patientid") Integer patientId){
        logger.info(" ----> Launch /pathistory/delete/id - note id: " + id + " - - " + patientId);
        message = historyService.deletePatientHistory(id);
        return "redirect:/patHistories/" + patientId+"?message="+ message;
    }
}
