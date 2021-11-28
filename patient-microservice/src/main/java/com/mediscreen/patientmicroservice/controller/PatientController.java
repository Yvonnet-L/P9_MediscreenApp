package com.mediscreen.patientmicroservice.controller;

import com.mediscreen.patientmicroservice.model.Patient;
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
import java.util.Optional;

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
    public List<Patient> getAllPatients(){
        logger.info(" ---> Launch getAllPatients");
        List<Patient> patients = patientService.findAll();
        return patients;
    }

    /** ------------------------------------------------------------------------------------------------------------
     * return a patient according to his id
     * @param id
     * @return PatientDTO
     */
    @ApiOperation(value="pick up a patient according to his id")
    @GetMapping(value="/patients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Optional<Patient> getPatientById(@PathVariable(name = "id") Integer id){
        logger.info(" ---> Launch getPatientById , /patient/{id}");
        Optional<Patient> patient = patientService.findById(id);
        return  patient;
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * @param famillyName
     * @return PatientDTO
     */
     @ApiOperation(value="retrieve patient with his famillyName")
     @GetMapping(value="/patient/familly/{famillyName}", produces = MediaType.APPLICATION_JSON_VALUE)
     @ResponseStatus(HttpStatus.OK)
     public Patient getPatientByFamillyName(@PathVariable(name = "famillyName") String famillyName){
        logger.info(" ---> Launch getPatientByFamillyName , /patient/{famillyName}");
         Patient patient = patientService.findPatientByFamillyName(famillyName);
        return  patient;
     }

    /**  ------------------------------------------------------------------------------------------------------------
     *
     * @param famillyName
     * @return
     */
    @ApiOperation(value="retrieve List of patients with his famillyName starting by %")
    @GetMapping(value="/patients/familly/{famillyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Patient> getPatientsStartingWith(@PathVariable(name = "famillyName") String famillyName){
        logger.info(" ---> Launch getPatientByFamillyName , /patient/{famillyName}");
        List<Patient> patients = patientService.findByFamillyNameStartingWith(famillyName);
        return  patients;
    }
}
