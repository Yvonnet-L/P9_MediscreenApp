package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientHistoryDTO;
import com.clientui.clientui.proxies.HistoryMicroserviceProxy;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Histopry Service Implementation
 */

@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    private static Logger logger = LogManager.getLogger( HistoryServiceImpl.class);



    /**
     *
     * @returnList<PatientHistoryDTO>
     */
    @Override
    public List<PatientHistoryDTO> findAllPatientHistories() {
        List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getAllPatientHistories();
        return patientHistoryDTOS;
    }

    /**
     *  method which returns the list of all the historical notes of a patient thanks to his id
     *  By calling the Proxy Feign
     * @param patientId
     * @return List<PatientHistoryDTO>
     */
    @Override
    public List<PatientHistoryDTO> findPatientHistoriesNotes(Integer patientId) {
        logger.info(" ----> Launch findPatientHistoriesNotes");
        List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getPatientHistoryBypatientId(patientId);
        return patientHistoryDTOS;
    }

    @Override
    public PatientHistoryDTO findPatientHistoryById(String id) {
        logger.info(" ----> Launch findPatientHistoryById");
        try{
            return historyMicroserviceProxy.getPatientHistoryById(id);
        }catch( Exception e ){
            return null;
        }
    }

    @Override
    public String addPatientHistory(PatientHistoryDTO patientHistoryDTO) {
        logger.info(" ----> Launch addPatientHistory");
        try{
            historyMicroserviceProxy.addPatientHistory(patientHistoryDTO);
            return "New note added";
        }catch( feign.FeignException e ){
            return "Sorry but an error occurred";
        }
    }

    @Override
    public String deletePatientHistory(String id) {
        logger.info(" ----> Launch deletePatientHistory");
        try{
            historyMicroserviceProxy.deletePatientHistory(id);
            return "Note deleted";
        }catch( FeignException.NotFound e ){
            return "- Not Found - note no longer exists\"";
        }catch( feign.FeignException e ){
            return "Sorry but an error occurred";
        }
    }

    @Override
    public String updatePatientHistory(String id, PatientHistoryDTO patientHistoryDTO) {
        logger.info(" ----> Launch updatePatientHistory");
        try {
            PatientHistoryDTO phDTO = historyMicroserviceProxy.updatePatientHistory(id, patientHistoryDTO);
            return "Note Updated";
        } catch (FeignException.NotFound e) {
            return "Cannot update this note because it no longer exists in the database ";
        } catch (feign.FeignException e) {
            return "Sorry but an error occurred";
        }
    }
}
