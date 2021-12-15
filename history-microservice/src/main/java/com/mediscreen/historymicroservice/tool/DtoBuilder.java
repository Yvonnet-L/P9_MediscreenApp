package com.mediscreen.historymicroservice.tool;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.model.PatientHistory;
import org.springframework.stereotype.Component;

/**
 * This class allows the construction of a DTO from a Model
 */

@Component
public class DtoBuilder {
   // private static Logger logger = LogManager.getLogger(DtoBuilder.class);

    /**-------------  PatientHistoryDTO  ------------------------------------------------------------------------
     *
     * @param patientHistory
     * @return PatientDTO
     */
    public PatientHistoryDTO buildPatientHistoryDTO(final PatientHistory patientHistory){
        //logger.info(" ----> Launch buildPatientHistoryDTO");
        return new PatientHistoryDTO(patientHistory.getId(), patientHistory.getPatientId(),
                patientHistory.getDate(), patientHistory.getNotes());
    }
}
