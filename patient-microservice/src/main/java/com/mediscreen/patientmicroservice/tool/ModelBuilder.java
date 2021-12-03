package com.mediscreen.patientmicroservice.tool;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This class allows the construction of a Model from a DTO
 */

@Component
public class ModelBuilder {

    private static Logger logger = LogManager.getLogger(ModelBuilder.class);

    /**
     *
     * @param patientDTO
     * @return Patient
     */
    public Patient buildPatient(final PatientDTO patientDTO) {
        logger.info(" ----> Launch buildPatient()");
        return new Patient(patientDTO.getId(),patientDTO.getFamilyName(),patientDTO.getGivenName(), patientDTO.getDateOfBirth(),
                patientDTO.getSex(), patientDTO.getAddress(), patientDTO.getPhone());
    }
}
