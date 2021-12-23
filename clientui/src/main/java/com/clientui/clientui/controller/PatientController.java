package com.clientui.clientui.controller;

import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.service.IPatientService;
import feign.Param;
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
import java.util.ArrayList;
import java.util.List;

/**
 *  Display controller and customer information management Patient
 */

@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;


    private static Logger logger = LogManager.getLogger(PatientController.class);

    private String message;

    /**
     *  Home Page of Mediscreen
     * @return home.html
     */
    @GetMapping("/")
    public String home(){
        logger.info(" ---> Launch home");
        return "home";
    }

    /**
     * Patient list overview page based on last name search, returns all clients if no search data is entered
     * @param model
     * @param stringSearch
     * @return list.html in folder patient
     */
    //---------Get----- /patient/list ---------------------------------------------------------------------------
    @GetMapping("/patient/list")
    public String getPatients(Model model, @Param("stringSearch")  String stringSearch,
                              @RequestParam(name="message", defaultValue = "") String message){
        logger.info(" ----> Launch /patients getPatients()");
        List<PatientDTO> patientDTOS = new ArrayList<>();
        if(stringSearch.isBlank()){
           patientDTOS = patientService.getAllPatients();
       } else {
            patientDTOS = patientService.getPatientStartingFamilyNameWith( stringSearch);
        }
        model.addAttribute("message", message);
        model.addAttribute("patientDTOS", patientDTOS);
        model.addAttribute("stringSearch", stringSearch);
        return "patient/list";
    }

    /**
     * launches the deletion of the patient by his id and refreshes the page
     * @param id
     * @return list.html in folder patient
     */
    //---------Get----- /patient/delete/id  ---------------------------------------------------------------------------
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") Integer id){
        logger.info(" ----> Launch /patient/delete/id - deletePatient()");
           message = patientService.deletePatientById(id);
        return   "redirect:/patient/list?stringSearch=&message="+ message;
    }
    /**
     * Launches the patient's update page by his id
     * @param id
     * @param model
     * @return update.html in folder patient
     */
    //---------Get----- /patient/update/{id} -------------------------------------------------------------------------
    @GetMapping("/patient/update/{id}")
    public String getUpdatePatient(@PathVariable("id") Integer id, Model model){
        logger.info(" ----> Launch Get /patient/update/{id} with id=" + id);
        PatientDTO patientDTO = patientService.getById(id);
       if(patientDTO==null){
            message="Patient with id "+id+" not found ! ";
            return   "redirect:/patient/list?stringSearch=&message="+ message;
        }
        model.addAttribute("patientDTO", patientDTO);
        return "patient/update";

    }

    /**
     * Starts updating patient data with data consistency check using the PatientDTO
     * javax.validation.constraints.Pattern.
     * Errors are reported on the pages if there are any, otherwise the update is made
     * Then return to the page with a success or failure message if a problem has arisen
     * @param id
     * @param patientDTO
     * @param result
     * @param model
     * @return update.html in folder patient with msg
     */
    //---------Put----- /patient/update/{id} --------------------------------------------------------------------
    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, @Valid PatientDTO patientDTO,
                                BindingResult result, Model model){
        logger.info(" ----> Launch Post /patient/update/{id} with id=" + id);
        if (Date.valueOf(patientDTO.getDateOfBirth()).after(Date.valueOf(LocalDate.now().toString()))){
            model.addAttribute("msgAlertDate", "The date cannot be later than today's date");
            return "patient/update";
        }else{
            model.addAttribute("msgAlertDate", "");
        }
        if(result.hasErrors()) {
            model.addAttribute("patientDTO", patientDTO);
            model.addAttribute(id);
            return "patient/update";
        }
        message = patientService.updatePatient(patientDTO, id);
        return   "redirect:/patient/list?stringSearch="+patientDTO.getFamilyName()+"&message="+ message;
    }
    /**
     * Launches the page for creating a new patient
     * @param patientDTO
     * @return add.htlm in folder patient
     */
    //---------Get----- /patient/add  ---------------------------------------------------------------------------
    @GetMapping("/patient/add")
    public String getAddPatient(PatientDTO patientDTO){
        logger.info(" ----> Launch /patient/add getAddPatient");
        return "patient/add";
    }
    /**
     *   Starts updating patient data with data consistency check using the PatientDTO
     *   javax.validation.constraints.Pattern.
     *   Errors are reported on the pages if there are any, otherwise the update is made
     *   Then return to the page with a success or failure message if a problem has arisen
     * @param patientDTO
     * @param result
     * @param model
     * @param message
     * @return add.html in folder patient
     */
    //---------Post-----/patient/validate---------------------------------------------------------------------------------
    @PostMapping("/patient/validate")
    public String validatePatient(@Valid PatientDTO patientDTO, BindingResult result, Model model,
                           @RequestParam(name="message", defaultValue = "") String message) {
        logger.info( "--> Launch /bidList/validate");
        if (Date.valueOf(patientDTO.getDateOfBirth()).after(Date.valueOf(LocalDate.now().toString()))){
            model.addAttribute("msgAlertDate", "The date cannot be later than today's date");
            return "patient/add";
        }else{
            model.addAttribute("msgAlertDate", "");
        }
        if(result.hasErrors()){
            logger.info( "  --> **  Errors ** Nb error: " + result.getErrorCount());
            return "patient/add";
        }
        message = patientService.addPatient(patientDTO);
        return   "redirect:/patient/list?stringSearch="+patientDTO.getFamilyName()+"&message="+ message;
    }
}