package com.clientui.clientui.service;

import com.clientui.clientui.dto.AssessmentDTO;
import com.clientui.clientui.proxies.AssessmentMicroserviceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentServiceImpl implements IAssessmentService{

    @Autowired
    AssessmentMicroserviceProxy assessmentMicroserviceProxy;

    @Override
    public List<AssessmentDTO> getAssessPatientStartingFamilyNameWith(String FamilystringSearch) {
        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        assessmentDTOS = assessmentMicroserviceProxy.getAssessmentForPatientWithFamilyName(FamilystringSearch);
        return assessmentDTOS;
    }

    @Override
    public AssessmentDTO getAssessPatientById(Integer patientId) {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO = assessmentMicroserviceProxy.getAssessmentForPatientByIdPatient(patientId);
        return assessmentDTO;
    }
}
