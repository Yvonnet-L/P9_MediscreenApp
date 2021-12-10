package com.clientui.clientui.service;

import com.clientui.clientui.beans.Patient;
import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.proxies.PatientMicroserviceProxy;
import com.clientui.clientui.tool.DtoBuilder;
import com.clientui.clientui.tool.ModelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

    @Autowired
    DtoBuilder dtoBuilder;

    @Autowired
    ModelBuilder modelBuilder;

    private static Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    @Override
    public List<PatientDTO> getAllPatients() {
        logger.info(" ----> Launch getAllPatients()");
        List<Patient> patients = patientMicroserviceProxy.getAllPatients();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for ( Patient p: patients){
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }

    @Override
    public List<PatientDTO> getPatientStartingFamilyNameWith(String stringSearch) {
        logger.info(" ----> Launch getPatientStartingFamilyNameWith()");
        List<Patient> patients = patientMicroserviceProxy.getPatientsStartingWith(stringSearch);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for ( Patient p: patients){
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }

    @Override
    public PatientDTO getById(Integer id) {
        logger.info(" ----> Launch getById(Integer id) with id = " + id);
        Patient patient = patientMicroserviceProxy.getPatientById(id);
        return dtoBuilder.buildPatientDTO(patient);
    }

    @Override
    public void deletePatientById(Integer id) {
        logger.info(" ----> Launch deletePatientById(Integer id) with id = " + id);

        try {
            patientMicroserviceProxy.deletePatient(id);
        }catch ( feign.FeignException e ){
            logger.info(" ****** Exception **** " + e.toString());
        }
    }

    @Override
    public String addPatient(PatientDTO patientDTO) {
        logger.info(" ----> Launch addPatient");
        try {
            Patient patientAdd = patientMicroserviceProxy.addPatient(modelBuilder.buildPatient(patientDTO));
            logger.info(" ---->  Patient add success with id: " + patientAdd.getId());
            return "New patient successfully added.!";
        }catch ( feign.FeignException e ){

            logger.info(" ****** Exception **** " + e.toString());
            return "This patient with this name, first name and date of birth already exists!";
        }
    }

    @Override
    public String udatePatient(PatientDTO patientDTO, Integer id) {
        try {
            Patient patientAdd = patientMicroserviceProxy.updatePatient(id, modelBuilder.buildPatient(patientDTO));
            logger.info(" ---->  Patient Update success with id: " + patientAdd.getId());
            return "Update Patient success.";
        }catch ( feign.FeignException e ){
            logger.info(" ****** Exception **** " + e.toString());
            return "Cannot update this patient because he no longer exists in the database ";
        }
    }

}

