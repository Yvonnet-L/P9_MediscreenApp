package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.service.IPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "patient management ")
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

    /** ------------------------------------------------------------------------------------------------------------
     * retrieves and displays the list of all patients
     *
     * @return List<PatientDTO>
     */
    @ApiOperation(value="retrieve the list of all patients")
    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients(){
        logger.info(" ---> Launch getAllPatients");
        List<PatientDTO> patientDTOS = patientService.findAll();
        return patientDTOS;
    }

    /** ------------------------------------------------------------------------------------------------------------
     * return a patient according to his id
     *
     * @param id integer
     * @return PatientDTO
     */
    @ApiOperation(value="pick up a patient according to his id")
    @GetMapping(value="/patients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO getPatientById(@PathVariable(name = "id") Integer id){
        logger.info(" ---> Launch getPatientById , /patient/{id}");
        PatientDTO patientDTO = patientService.findById(id);
        return  patientDTO;
    }

    /** ------------------------------------------------------------------------------------------------------------
     * return patient search for a given last name (familyName)
     *
     * @param familyName string
     * @return PatientDTO
     */
     @ApiOperation(value="retrieve patient with his familyName")
     @GetMapping(value="/patient/family/{familyName}", produces = MediaType.APPLICATION_JSON_VALUE)
     @ResponseStatus(HttpStatus.OK)
     public PatientDTO getPatientByFamilyName(@PathVariable(name = "familyName") String familyName){
        logger.info(" ---> Launch getPatientByFamilyName , /patient/{familyName}");
         PatientDTO patientDTO = patientService.findPatientByFamilyName(familyName);
        return  patientDTO;
     }

    /**  ------------------------------------------------------------------------------------------------------------
     * return patient search for a given last name starting by %
     *
     * @param familyName string
     * @return List<PatientDTO>
     */
    @ApiOperation(value="retrieve List of patients with his familyName starting by %")
    @GetMapping(value="/patients/family/{familyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDTO> getPatientsStartingWith(@PathVariable(name = "familyName") String familyName){
        logger.info(" ---> Launch getPatientByFamilyName , /patient/{familyName}");
        List<PatientDTO> patientDTOS = patientService.findByFamilyNameStartingWith(familyName);
        return  patientDTOS;
    }
}
