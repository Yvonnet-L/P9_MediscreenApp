package com.mediscreen.historymicroservice.service;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.exception.DataNotConformException;
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

/**
 *
 */
@Service
public class PatientHistoryServiceImp implements IPatientHistoryService {

    @Autowired
    PatientHistoryRepository patientHistoryRepository;

    @Autowired
    DtoBuilder dtoBuilder;

    @Autowired
    ModelBuilder modelBuilder;

    private static Logger logger = LogManager.getLogger(PatientHistoryServiceImp.class);

    /**
     *
     * @return List<PatientHistoryDTO>
     */
    @Override
    public List<PatientHistoryDTO> findAll() {
        List<PatientHistory> patientHistories = patientHistoryRepository.findAll();
        List<PatientHistoryDTO> patientHistoryDTOS = new ArrayList<>();
        for (PatientHistory patientHistory: patientHistories) {
            patientHistoryDTOS.add(dtoBuilder.buildPatientHistoryDTO(patientHistory));
        }
        return patientHistoryDTOS;
    }

    @Override
    public PatientHistoryDTO findById(String id) {
       PatientHistory patientHistory = patientHistoryRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Patient with this id is unknown"));
       return dtoBuilder.buildPatientHistoryDTO(patientHistory);
    }

    @Override
    public List<PatientHistoryDTO> findAllByPatientId(Integer patientId) {
        List<PatientHistory> patientHistories = patientHistoryRepository.findAllByPatientId(patientId);
        return listPatientHistoryToListDTO(patientHistories);
    }

    @Override
    public PatientHistoryDTO addPatientHistory(PatientHistoryDTO patientHistoryDTO) {
        PatientHistory patientHistoryAdd = new PatientHistory();
        if (!patientHistoryDTO.equals(null)){
            patientHistoryAdd = patientHistoryRepository.save(modelBuilder.buildPatientHistory(patientHistoryDTO));
        } else {
            throw new DataNotConformException("PatientHistory patient cannot be Null ");
        }
        return dtoBuilder.buildPatientHistoryDTO(patientHistoryAdd);
    }

    @Override
    public PatientHistoryDTO updatePatientHistory(Integer id, PatientHistoryDTO patientHystoryDTO) {
        return null;
    }

    @Override
    public void deletePatientHistory(String id) {
        logger.info(" ---> Launch deletePatientHistory");
        patientHistoryRepository.deleteById(id);
        logger.info(" ---> PatientHistory note id:-"+ id +"- deleted !");
    }

    /** ----------------------------------------------------------------
     *
     */
    public List<PatientHistoryDTO> listPatientHistoryToListDTO(List<PatientHistory> patientHistories){
        List<PatientHistoryDTO> patientHistoryDTOS = new ArrayList<>();
        for(PatientHistory ph: patientHistories){
            patientHistoryDTOS.add(dtoBuilder.buildPatientHistoryDTO(ph));
        }
        return patientHistoryDTOS;
    }
}
