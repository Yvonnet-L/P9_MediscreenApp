package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.controller.PatientController;
import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.exception.DataAlreadyExistException;
import com.mediscreen.patientmicroservice.exception.DataNotConformException;
import com.mediscreen.patientmicroservice.exception.DataNotFoundException;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import com.mediscreen.patientmicroservice.tool.DtoBuilder;
import com.mediscreen.patientmicroservice.tool.ModelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService{

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ModelBuilder modelBuilder;

    @Autowired
    DtoBuilder dtoBuilder;

    private static Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @return patientDTOS
     */
    @Override
    public List<PatientDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
          patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return patientDTO
     */
    @Override
    public PatientDTO findById(Integer id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
        PatientDTO patientDTO = dtoBuilder.buildPatientDTO(patient);
        return patientDTO;
    }

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @param familyName
     * @return patientDTOS
     */
    @Override
    public List<PatientDTO> findByFamilyNameStartingWith(String familyName) {
        List<Patient> patients = patientRepository.findByFamilyNameStartingWith(familyName);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
            return patientDTOS;
    }

    /**
     * Method for adding a new patient who after checking the non-existence of the patient by name, first name date of
     * birth calls the ripository method to create the data
     * @param patientDTO
     * @return patientDTO ( returned )
     */
    @Override
    public PatientDTO addPatient(PatientDTO patientDTO) {
        logger.info(" ---> Launch addPatient");
       Patient patientSearched = patientRepository.findByFamilyNameAndGivenNameAndDateOfBirth(patientDTO.getFamilyName(),
                patientDTO.getGivenName(), patientDTO.getDateOfBirth());
       if(patientSearched != null) {
            throw new DataAlreadyExistException("A patient with this name, first name and date of birth already exists");
        };
        Patient patientAdded = patientRepository.save(modelBuilder.buildPatient(patientDTO));
        logger.info(" ------>  Patient added Success !");
        return dtoBuilder.buildPatientDTO(patientAdded);
    }

    /**
     *
     * @param id
     * @param patientDTO
     * @return PatientDTO
     */
    @Override
    public PatientDTO updatePatient(Integer id, PatientDTO patientDTO) {
        logger.info(" ---> Launch updatePatient");
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
        patientDTO.setId(id);
        Patient patientUpdate = patientRepository.save(modelBuilder.buildPatient(patientDTO));
        return dtoBuilder.buildPatientDTO(patientUpdate);
    }

    @Override
    public void deletePatient(Integer id) {
        Patient patient = patientRepository.findByIdPatient(id);
        if (patient == null) {
            throw new DataNotFoundException("Can not find the entity patient with id = " + id );
        }
        patientRepository.delete(patient);
    }



}
