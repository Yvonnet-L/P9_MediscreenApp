package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.proxies.PatientMicroserviceProxy;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Patient Service implementation
 */

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;


    private static Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    /**
     * Method of liason with PatientMicroserviceProxy on getAllPatients
     *
     * @return List<PatientDTO>
     */
    @Override
    public List<PatientDTO> getAllPatients() {
        logger.info(" ----> Launch getAllPatients()");
            List<PatientDTO> patientDTOS = patientMicroserviceProxy.getAllPatients();
            return patientDTOS;
    }

    /**
     * Method of liason with PatientMicroserviceProxy on getPatientStartingFamilyNameWith
     *
     * @param stringSearch
     * @return  patientDTOS List<PatientDTO>
     */
    @Override
    public List<PatientDTO> getPatientStartingFamilyNameWith(String stringSearch) {
        logger.info(" ----> Launch getPatientStartingFamilyNameWith()");
        List<PatientDTO> patientDTOS = patientMicroserviceProxy.getPatientsStartingWith(stringSearch);
        return patientDTOS;
    }

    /**
     * Method of liason with PatientMicroserviceProxy on getById
     *
     * @param id id of patient
     * @return PatientDTO
     */
    @Override
    public PatientDTO getById(Integer id) {
        logger.info(" ----> Launch getById(Integer id) with id = " + id);
        try {
            PatientDTO patientDTO = patientMicroserviceProxy.getPatientById(id);
            return patientDTO;
        }catch(FeignException e){
            return null;
        }
    }

    /**
     * Method of liason with PatientMicroserviceProxy on deletePatientById
     *
     * @param id id of patient
     * @return String result
     */
    @Override
    public String deletePatientById(Integer id) {
        logger.info(" ----> Launch deletePatientById(Integer id) with id = " + id);
        try {
            patientMicroserviceProxy.deletePatient(id);
            return "Patient deleted";
        }catch ( feign.FeignException e ){
            logger.info(" ****** Exception **** " + e.toString());
            return "- Not Found - patient no longer exists";
        }
    }

    /** -
     * Method of liason with PatientMicroserviceProxy on addPatient
     *
     * @param patientDTO
     * @return String message résultat opération
     */
    @Override
    public String addPatient(PatientDTO patientDTO) {
        logger.info(" ----> Launch addPatient");
        try {
            PatientDTO patientDTOAdd = patientMicroserviceProxy.addPatient(patientDTO);
            return "New patient added";
        }catch ( feign.FeignException e ){
            return "This patient with this name, first name and date of birth already exists!";
        }
    }

    /**
     *  Method of liason with PatientMicroserviceProxy on updatePatient
     *
     * @param patientDTO
     * @param id
     * @return String message résultat opération
     */
    @Override
    public String updatePatient(PatientDTO patientDTO, Integer id) {
        logger.info(" ---->  Launch updatePatient");
       try {
            PatientDTO patientDTOAdd = patientMicroserviceProxy.updatePatient(id,patientDTO);
            return "Patient Updated";
        }catch ( feign.FeignException e ){
            return "Cannot update this patient because he no longer exists in the database ";
        }
    }
}

