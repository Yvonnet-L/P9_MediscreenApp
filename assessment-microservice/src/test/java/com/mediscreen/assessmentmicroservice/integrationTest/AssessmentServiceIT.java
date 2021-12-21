package com.mediscreen.assessmentmicroservice.integrationTest;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.service.AssessmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AssessmentServiceIT {

    @Autowired
    private AssessmentServiceImpl assessmentService;


    @Test
    public void assessmentServiceTest(){

        AssessmentDTO assessmentDTO;
        List<AssessmentDTO> assessmentDTOList;

        // Test sur  -id 1-  TestNone Test	54	F	None
        assessmentDTO = assessmentService.getDiabeteAssessmentByIdPatient(1);
        assertTrue(assessmentDTO.getDiabetesRiskAssessment().equals("None") );
        assertThat(assessmentDTO.getAge().equals(54));
        assertThat(assessmentDTO.getPatientDTO().getFamilyName().equals("TestNone"));
        assertThat(assessmentDTO.getPatientDTO().getId().equals(1));
        assertThat(assessmentDTO.getPatientDTO().getSex().equals("F"));


        // Test sur -id 2-   TestBorderline	Test	76	M	Borderline
        assessmentDTO = assessmentService.getDiabeteAssessmentByIdPatient(2);
        assertTrue(assessmentDTO.getDiabetesRiskAssessment().equals("Borderline") );
        assertThat(assessmentDTO.getAge().equals(76));
        assertThat(assessmentDTO.getPatientDTO().getFamilyName().equals("TestBorderline"));
        assertThat(assessmentDTO.getPatientDTO().getId().equals(2));
        assertThat(assessmentDTO.getPatientDTO().getSex().equals("M"));

        // Test sur -id 3-   TestInDanger	Test	17	M	In Danger
        assessmentDTO = assessmentService.getDiabeteAssessmentByIdPatient(3);
        assertTrue(assessmentDTO.getDiabetesRiskAssessment().equals("In Danger") );
        assertThat(assessmentDTO.getAge().equals(17));
        assertThat(assessmentDTO.getPatientDTO().getFamilyName().equals("TestInDanger"));
        assertThat(assessmentDTO.getPatientDTO().getId().equals(3));
        assertThat(assessmentDTO.getPatientDTO().getSex().equals("M"));

        // Test sur -id 4-   TestEarlyOnset	Test	19	F	Early onset
        assessmentDTO = assessmentService.getDiabeteAssessmentByIdPatient(4);
        assertTrue(assessmentDTO.getDiabetesRiskAssessment().equals("Early onset") );
        assertThat(assessmentDTO.getAge().equals(19));
        assertThat(assessmentDTO.getPatientDTO().getFamilyName().equals("TestEarlyOnset"));
        assertThat(assessmentDTO.getPatientDTO().getId().equals(4));
        assertThat(assessmentDTO.getPatientDTO().getSex().equals("F"));


        // Test sur getDiabeteAssessmentByFamilyName with test
        assessmentDTOList = assessmentService.getDiabeteAssessmentByFamilyName("test");
        assertTrue(assessmentDTOList.size()==4 );



    }
}
