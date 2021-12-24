package com.clientui.clientui.service;

import com.clientui.clientui.dto.AssessmentDTO;
import com.clientui.clientui.proxies.AssessmentMicroserviceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  Assessment Service Implementation
 */
@Service
public class AssessmentServiceImpl implements IAssessmentService{

    @Autowired
    AssessmentMicroserviceProxy assessmentMicroserviceProxy;

    private static Logger logger = LogManager.getLogger( AssessmentServiceImpl.class);

    /**
     * Method of liason with AssessmentMicroserviceProxy on getAssessPatientStartingFamilyNameWith
     *
     * @param familystringSearch
     * @return  List<AssessmentDTO>
     */
    @Override
    public List<AssessmentDTO> getAssessPatientStartingFamilyNameWith(String familystringSearch) {
        logger.info(" ----> Launch getAssessPatientStartingFamilyNameWith with familystringSearch: " +familystringSearch);
        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        assessmentDTOS = assessmentMicroserviceProxy.getAssessmentForPatientWithFamilyName(familystringSearch);
        return assessmentDTOS;
    }

    /**
     * Method of liason with AssessmentMicroserviceProxy on getAssessPatientById
     *
     * @param patientId
     * @return assessmentDTO
     */
    @Override
    public AssessmentDTO getAssessPatientById(Integer patientId) {
        logger.info(" ----> Launch getAssessPatientById with patientid: "+patientId);
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO = assessmentMicroserviceProxy.getAssessmentForPatientByIdPatient(patientId);
        return assessmentDTO;
    }
}
