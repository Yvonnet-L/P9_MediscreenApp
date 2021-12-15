package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.exception.DataAlreadyExistException;
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

/**
 *
 */
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
     * Method which returns the list of all the patients existing in the database by calling the repository
     * @return patientDTOS List<PatientDTO>
     */
    @Override
    public List<PatientDTO> findAll() {
        logger.info(" ---> Launch findAll");
        List<Patient> patients = patientRepository.findAll();

        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
          patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }

        return listPatientToListDTO(patients);
    }

    /**  ----------------------------------------------------------------------------------------------------------
     * Method who retrieves a Patient by his id by calling the repository
     * @param id
     * @return patientDTO
     */
    @Override
    public PatientDTO findById(Integer id) {
        logger.info(" ---> Launch findById id = " + id);
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
        PatientDTO patientDTO = dtoBuilder.buildPatientDTO(patient);
        return patientDTO;
    }

    /**  ----------------------------------------------------------------------------------------------------------
     * Search method for patients having their last names starting with the transmitted variable
     * by calling the repository
     * @param familyName
     * @return patientDTOS all patients found
     */
    @Override
    public List<PatientDTO> findByFamilyNameStartingWith(String familyName) {
        logger.info(" ---> Launch findByFamilyNameStartingWith with familyName = " + familyName);
        List<Patient> patients = patientRepository.findByFamilyNameStartingWith(familyName);
        List<PatientDTO> patientDTOS = listPatientToListDTO(patients);
            return patientDTOS;
    }

    /** ----------------------------------------------------------------------------------------------------------
     *
     * Method for adding a new patient who after checking the non-existence of the patient by name, first name date of
     * birth calls the repository method to create the data
     * @param patientDTO
     * @return patientDTO new Patient created
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

    /** ----------------------------------------------------------------------------------------------------------
     *
     * This method verifies the existence of the patient by his identifier, if it exists updates it
     * by calling the repository
     * @param id
     * @param patientDTO
     * @return PatientDTO returned PatientDTO with new data
     */
    @Override
    public PatientDTO updatePatient(Integer id, PatientDTO patientDTO) {
        logger.info(" ---> Launch updatePatient id = " + id);
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
        patientDTO.setId(id);
        Patient patientUpdate = patientRepository.save(modelBuilder.buildPatient(patientDTO));
        return dtoBuilder.buildPatientDTO(patientUpdate);
    }

    /** ----------------------------------------------------------------------------------------------------------
     *
     * This method verifies the existence of the patient by his identifier, if it exists deletes it
     * by calling the repository
     * @param id id of the patient to be deleted
     */
    @Override
    public void deletePatient(Integer id) {
        logger.info(" ---> Launch deletePatient id = " + id);
        Patient patient = patientRepository.findByIdPatient(id);
        if (patient == null) {
            throw new DataNotFoundException("Can not find the entity patient with id = " + id );
        }
        patientRepository.delete(patient);
    }

    /** ----------------------------------------------------------------------------------------------------------
     * Method utility to build a List<PatientDTO> from a List<Patient> using dtoBuilder
     * @param patients  List<Patient>
     * @return patientDTOS List<PatientDTO>
     */
    public List<PatientDTO> listPatientToListDTO(List<Patient> patients) {
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }
}
