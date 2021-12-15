package com.mediscreen.historymicroservice.tool;


import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.model.PatientHistory;
import org.springframework.stereotype.Component;

/**
 * This class allows the construction of a Model from a DTO
 */

@Component
public class ModelBuilder {

   //private static Logger logger = LogManager.getLogger(ModelBuilder.class);

    /**
     * @param patientHistoryDTO
     * @return Patient
     */
    public PatientHistory buildPatientHistory(final PatientHistoryDTO patientHistoryDTO) {
        //logger.info(" ----> Launch buildPatient()");
        return new PatientHistory(patientHistoryDTO.getId(), patientHistoryDTO.getPatientId(),
                patientHistoryDTO.getDate(), patientHistoryDTO.getNotes());
    }
}
