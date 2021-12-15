package com.clientui.clientui.controller;

import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.proxies.PatientMicroserviceProxy;
import com.clientui.clientui.service.IPatientService;
import feign.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  Display controller and customer information management Patient
 */

@Controller
public class PatientController {

    @Autowired
    IPatientService patientService;

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;


    private static Logger logger = LogManager.getLogger(PatientController.class);

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
    public String getPatients(Model model, @Param("stringSearch")  String stringSearch){
        logger.info(" ----> Launch /patients getPatients()");
        List<PatientDTO> patientDTOS = new ArrayList<>();
        if(stringSearch.isBlank()){
            patientDTOS = patientService.getAllPatients();
       } else {
            patientDTOS = patientService.getPatientStartingFamilyNameWith( stringSearch);
        }

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
        patientService.deletePatientById(id);
        logger.info( "  --> ** patient Deleted ** id: " + id);
        return   "redirect:/patient/list?stringSearch=";
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
        if(result.hasErrors()) {
            model.addAttribute("patientDTO", patientDTO);
            model.addAttribute(id);
            return "patient/update";
        }
        String msg =  patientService.updatePatient(patientDTO, id);
        model.addAttribute("message", msg);
        logger.info( "  --> **  Patient update success ** id: " + id);
        //return   "redirect:/patient/list?stringSearch=";
        return "patient/update";
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
    public String validate(@Valid PatientDTO patientDTO, BindingResult result, Model model,
                           @RequestParam(name="message", defaultValue = "") String message) {
        logger.info( "--> Launch /bidList/validate");
        if(result.hasErrors()){
            logger.info( "  --> **  Errors ** Nb error: " + result.getErrorCount());
            return "patient/add";
        }
        String msg = patientService.addPatient(patientDTO);
        logger.info( "  --> **  Patient saved **");
        model.addAttribute("message", msg);
        return "patient/add";
       // return  "redirect:/patient/list?stringSearch=";
    }
}