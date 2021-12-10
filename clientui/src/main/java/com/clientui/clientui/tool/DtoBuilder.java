package com.clientui.clientui.tool;

import com.clientui.clientui.beans.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class allows the construction of a DTO from a Model
 */

@Component
public class DtoBuilder {

    private static Logger logger = LogManager.getLogger(DtoBuilder.class);

    /**-------------  PatientDTO  ------------------------------------------------------------------------
     *
     * @param patient
     * @return PatientDTO
     */
    public com.clientui.clientui.dto.PatientDTO buildPatientDTO(final Patient patient){
       // logger.info(" ----> Launch buildPatientDTO");

        return new com.clientui.clientui.dto.PatientDTO(patient.getId(),patient.getFamilyName(),patient.getGivenName(), patient.getDateOfBirth().toString(),
                patient.getSex(), patient.getAddress(), patient.getPhone());
    }


}
