package com.clientui.clientui.tool;


import com.clientui.clientui.beans.Patient;
import com.clientui.clientui.dto.PatientDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * This class allows the construction of a Model from a DTO
 */

@Component
public class ModelBuilder {

    private static Logger logger = LogManager.getLogger(ModelBuilder.class);

    /** --------------  Patient -------------------------------------------------------------------------------------
     *
     * @param patientDTO
     * @return Patient
     */
    public Patient buildPatient(final PatientDTO patientDTO) {
        //logger.info(" ----> Launch buildPatient()");
        return new Patient(patientDTO.getId(),patientDTO.getFamilyName(),patientDTO.getGivenName(),
                             LocalDate.parse(patientDTO.getDateOfBirth()), patientDTO.getSex(),
                                patientDTO.getAddress(), patientDTO.getPhone());
    }
}

