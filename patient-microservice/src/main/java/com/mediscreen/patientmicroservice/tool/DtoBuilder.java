package com.mediscreen.patientmicroservice.tool;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.model.Patient;
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
    public PatientDTO buildPatientDTO(final Patient patient){
        logger.info(" ----> Launch buildPatientDTO");
        return new PatientDTO(patient.getIdPatient(),patient.getFamilyName(),patient.getGivenName(), patient.getDateOfBirth(),
                               patient.getSex(), patient.getAddress(), patient.getPhone());
    }


}
