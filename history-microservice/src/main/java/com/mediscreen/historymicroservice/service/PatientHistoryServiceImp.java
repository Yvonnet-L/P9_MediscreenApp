package com.mediscreen.historymicroservice.service;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.exception.DataNotFoundException;
import com.mediscreen.historymicroservice.model.PatientHistory;
import com.mediscreen.historymicroservice.repository.PatientHistoryRepository;
import com.mediscreen.historymicroservice.tool.DtoBuilder;
import com.mediscreen.historymicroservice.tool.ModelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PatientHistoryServiceImp implements IPatientHistoryService {

    @Autowired
    PatientHistoryRepository patientHistoryRepository;

    @Autowired
    DtoBuilder dtoBuilder;

    @Autowired
    ModelBuilder modelBuilder;

    private static Logger logger = LogManager.getLogger(PatientHistoryServiceImp.class);

    /** ------------------------------------------------------------------------------------------------------------
     * Method that returns the list of all patient note histories existing in the database by calling the repository
     * @return List<PatientHistoryDTO>
     */
    @Override
    public List<PatientHistoryDTO> findAll() {
        logger.info(" ---> Launch findAll");
        List<PatientHistory> patientHistories = patientHistoryRepository.findAll();
        return listPatientHistoryToListDTO(patientHistories);
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * Method who retrieves a PatientHistory by his id by calling the repository
     * @param id
     * @return PatientHistoryDTO
     */
    @Override
    public PatientHistoryDTO findById(String id) {
        logger.info(" ---> Launch findById with id = " + id);
       PatientHistory patientHistory = patientHistoryRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
       return dtoBuilder.buildPatientHistoryDTO(patientHistory);
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * Method who retrieves  all PatientHistory of patientid by calling the repository
     * @param patientId
     * @return List<PatientHistoryDTO>
     */
    @Override
    public List<PatientHistoryDTO> findAllByPatientId(Integer patientId) {
        logger.info(" ---> Launch findAllByPatientId with id = " + patientId);
        List<PatientHistory> patientHistories = patientHistoryRepository.findAllByPatientId(patientId);
        return listPatientHistoryToListDTO(patientHistories);
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * Method to add a PatientHistory
     * @param patientHistoryDTO
     * @return PatientHistoryDTO added
     */
    @Override
    public PatientHistoryDTO addPatientHistory(PatientHistoryDTO patientHistoryDTO) {
        logger.info(" ---> Launch addPatientHistory");
          PatientHistory  phAdd = patientHistoryRepository.save(modelBuilder.buildPatientHistory(patientHistoryDTO));
        return dtoBuilder.buildPatientHistoryDTO(phAdd);
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * Method to update a PatientHistory with his id and new params
     * @param id String
     * @param patientHistoryDTO  PatientHistoryDTO
     * @return PatientHistoryDTO
     */
    @Override
    public PatientHistoryDTO updatePatientHistory(String id, PatientHistoryDTO patientHistoryDTO) {
        logger.info(" ---> Launch updatePatientHistory with id = " + id);
        PatientHistory patientHistory = patientHistoryRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("PatientHistory with id = " + id + " is unknown "));
        patientHistoryDTO.setId(id);
        PatientHistory phUpdate = patientHistoryRepository.save(modelBuilder.buildPatientHistory(patientHistoryDTO));
        logger.info(" ---> PatientHistory with id = " + id + " update success !");
        return dtoBuilder.buildPatientHistoryDTO(phUpdate);
    }

    /** ------------------------------------------------------------------------------------------------------------
     *
     * Method to delete a PatientHistory with his id
     * @param id
     */
    @Override
    public void deletePatientHistory(String id) {
        logger.info(" ---> Launch deletePatientHistory with id = " + id);
        patientHistoryRepository.deleteById(id);
        logger.info(" ---> PatientHistory note id:-"+ id +"- deleted !");
    }

    /** ------------- Tool ------------------------------------------------------------------------------------------
     *
     * Method utility to build a List<PatienHistorytDTO> from a List<PatientHistory> using dtoBuilder
     * @param patientHistories
     * @return patientHistoryDTOS List<PatientHistoryDTO>
     */
    public List<PatientHistoryDTO> listPatientHistoryToListDTO(List<PatientHistory> patientHistories){
        List<PatientHistoryDTO> patientHistoryDTOS = new ArrayList<>();
        for(PatientHistory ph: patientHistories){
            patientHistoryDTOS.add(dtoBuilder.buildPatientHistoryDTO(ph));
        }
        return patientHistoryDTOS;
    }
}
