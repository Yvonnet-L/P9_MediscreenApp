package com.mediscreen.historymicroservice.controller;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.service.IPatientHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "Hystory history Notes management ")
@RestController
public class PatientHistoryController {

    @Autowired
    IPatientHistoryService patientHistoryService;

   private static Logger logger = LogManager.getLogger(PatientHistoryController.class);

    /** ------- Get --- getAllPatientHistories --------------------------------------------------------------------
     *  Return the list of all patient Note History
     *
     * @return patientHistoryList List<PatientHistory>
     */
    @ApiOperation(value="Return the list of all patient Note History")
    @GetMapping("/patHistories")
    public List<PatientHistoryDTO> getAllPatientHistories(){
        logger.info(" ---> Launch getAllPatientHistories()");
        List<PatientHistoryDTO> patientHistoryDTOS = patientHistoryService.findAll();
        return patientHistoryDTOS;
    }

    /** ------- Get --- getPatientHistoryBypatientId ---------------------------------------------------------------
     * Returns the history of all the notes of a patient by his id
     * @param patientId
     * @return patientHistoryList List<PatientHistory>
     */
    @ApiOperation(value="Returns the history of all the notes of a patient by his id")
    @GetMapping("/patHistory/{patientId}")
    public List<PatientHistoryDTO> getPatientHistoryBypatientId(@PathVariable(name = "patientId") Integer patientId){
        logger.info(" ---> Launch getPatientHistoryBypatientId() - id = " + patientId);
        List<PatientHistoryDTO> patientHistoryDTOS = patientHistoryService.findAllByPatientId(patientId);
        return patientHistoryDTOS;
    }

    /** ------- Post ---  addPatientHistory -------------------------------------------------------------------------
     *  Adds a history of a patient's note
     * @param patientHistoryDTO
     * @return patientHistoryDTOAdd PatientHistoryDTO
     */
    @ApiOperation(value="Adds a history of a patient's note")
    @PostMapping("/patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientHistoryDTO addPatientHistory(@Valid @RequestBody PatientHistoryDTO patientHistoryDTO){
        logger.info(" ---> Launch addPatientHistory()");
        PatientHistoryDTO patientHistoryDTOAdd = patientHistoryService.addPatientHistory(patientHistoryDTO);
        return patientHistoryDTOAdd;
    }

    @PutMapping("/patHistory/put")
    public PatientHistoryDTO putPatientHistory(@PathVariable("id") Integer id,@Valid @RequestBody PatientHistoryDTO patientHistoryDTO){
        logger.info(" ---> Launch putPatientHistory()");
        PatientHistoryDTO patientHistoryDTOPut = patientHistoryService.updatePatientHistory(id,patientHistoryDTO);
        return patientHistoryDTOPut;
    }

    @DeleteMapping("/patHistory/delete/{id}")
    public void deletePatientHistory(@PathVariable(name ="id") String id ) {
        logger.info(" ---> Launch deletePatientHistory() with id = "+ id );
        patientHistoryService.deletePatientHistory(id);
        logger.info( "  --> ** patientHistory Deleted ** id: " + id );
    }
}
