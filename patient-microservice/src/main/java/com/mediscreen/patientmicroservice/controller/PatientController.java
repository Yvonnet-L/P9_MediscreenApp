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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /** ------- Get ---  getAllPatients ----------------------------------------------------------------------------
     * retrieves and displays the list of all patients
     *
     * @return List<PatientDTO>
     */
    @ApiOperation(value="Return the list of all patients")
    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients(){
        logger.info(" ---> Launch getAllPatients");
        List<PatientDTO> patientDTOS = patientService.findAll();
        return patientDTOS;
    }

    /** ------ Get --- getPatientById -------------------------------------------------------------------------------
     * return a patient according to his id
     *
     * @param id integer
     * @return PatientDTO
     */
    @ApiOperation(value="pick up a patient according to his id")
    @GetMapping(value="/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO getPatientById(@PathVariable(name = "id") Integer id){
        logger.info(" ---> Launch getPatientById , /patient/{id}");
        PatientDTO patientDTO = patientService.findById(id);
        return  patientDTO;
    }


    /**  ----- Get --- getPatientsStartingWith ----------------------------------------------------------------------
     * return patients search for a given last name starting by %
     * if params is empty return all patients
     * @param familyName string
     * @return List<PatientDTO>
     */
    @ApiOperation(value="Return list of patients with his familyName starting by %")
    @GetMapping(value="/patients/family/{familyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<PatientDTO> getPatientsStartingWith(@PathVariable(name = "familyName") String familyName){
        logger.info(" ---> Launch getPatientByFamilyName , /patient/{familyName}");
        List<PatientDTO> patientDTOS = patientService.findByFamilyNameStartingWith(familyName);
        return  patientDTOS;
    }

    /**  ------- Post ---- addPatient -----------------------------------------------------------------------------
     * @param patientDTO
     * @return PatientDTO created and status
     */
    @ApiOperation(value="Adding a patient in the base")
    @PostMapping(value="/patient/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@Valid @RequestBody PatientDTO patientDTO) {
        logger.info(" ---> Launch addPatient , /patients/add with patient named : " + patientDTO.getFamilyName()+" "+
                patientDTO.getGivenName());
        PatientDTO patientAdded = patientService.addPatient(patientDTO);

        return patientAdded;
    }

    /**  ------- Put ---- addPatient -----------------------------------------------------------------------------
     *  Update a patient found thanks to his id if it exists
     * @param id
     * @param patientDTO
     * @return patientUpdated
     */
    @ApiOperation(value="Update a patient in the base if it exists")
    @PutMapping(value="/patient/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PatientDTO updatePatient(@PathVariable("id") Integer id, @Valid @RequestBody PatientDTO patientDTO) {
        logger.info(" ---> Launch addPatient , /patients/update/{id} with patient id : " + id);
        PatientDTO patientUpdated = patientService.updatePatient(id,patientDTO);
        return patientUpdated ;
    }

    /**  ------- Delete ---- deletePatient -----------------------------------------------------------------------------
     * Delete a patient found thanks to his id if it exists
     */
    @ApiOperation(value="Delete a patient in the base if it exists")
    @DeleteMapping(value="/patient/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Integer id) {
        logger.info(" ---> Launch addPatient , /patients/update/{id} with patient id : " + id);
         patientService.deletePatient(id);
    }

}
