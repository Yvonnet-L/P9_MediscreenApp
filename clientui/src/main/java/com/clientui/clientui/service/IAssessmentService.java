package com.clientui.clientui.service;

import com.clientui.clientui.dto.AssessmentDTO;

import java.util.List;

public interface IAssessmentService {


    List<AssessmentDTO> getAssessPatientStartingFamilyNameWith(String stringSearch);

    AssessmentDTO getAssessPatientById(Integer patientId);
}
