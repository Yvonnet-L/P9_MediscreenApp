package com.mediscreen.assessmentmicroservice.service;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;

import java.util.List;

public interface IAssessmentService {

    List<String> rechercheString();

    String diabeteAssessmentByIdPatient(Integer patientId);

    List<AssessmentDTO> diabeteAssessmentByFamilyName(String familyName);
}
