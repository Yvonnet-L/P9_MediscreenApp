package com.mediscreen.assessmentmicroservice.service;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;

import java.util.List;
/**
 *  Assessment Service Interface
 */
public interface IAssessmentService {

    AssessmentDTO getDiabeteAssessmentByIdPatient(Integer patientId);

    List<AssessmentDTO> getDiabeteAssessmentByFamilyName(String familyName);
}
