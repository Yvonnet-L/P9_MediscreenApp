package com.clientui.clientui.controller;


import com.clientui.clientui.dto.AssessmentDTO;
import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.service.AssessmentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AssessmentController.class)
public class AssessmentControllerTest {

    @MockBean
    private AssessmentServiceImpl assessmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    List<AssessmentDTO> assessmentDTOList;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //------- Get --- getAssessmentPatient ---- /patReport/{patientId} --------------------------------------------
    @Test
    @DisplayName("getAssessmentPatient Test response 200")
    public void getAssessmentPatientTest() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO(new PatientDTO(2, "TestBorderline", "Test",
                "1945-06-24", "F", "2 High St", "200-333-4444"), 76, " Borderline");
        assessmentDTOList = Arrays.asList(assessmentDTO);

        Mockito.when(assessmentService.getAssessPatientById(2)).thenReturn(assessmentDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/patReport/2"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("assessmentDTOS"))
                .andExpect(view().name("report/patReport"))
                .andExpect(status().isOk());

        verify(assessmentService).getAssessPatientById(2);
    }

    //------- Get --- getAssessments ---- /report/list --------------------------------------------
    @Test
    @DisplayName("getAssessments Test response 200")
    public void getAssessmentsTest() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO(new PatientDTO(2, "TestBorderline", "Test",
                "1945-06-24", "F", "2 High St", "200-333-4444"), 76, " Borderline");
        assessmentDTOList = Arrays.asList(assessmentDTO);

        Mockito.when(assessmentService.getAssessPatientStartingFamilyNameWith("TestBorderline")).thenReturn(assessmentDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/report/list?stringSearch=TestBorderline"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("assessmentDTOS"))
                .andExpect(model().attributeExists( "stringSearch"))
                .andExpect(view().name("report/list"))
                .andExpect(status().isOk());

        verify(assessmentService).getAssessPatientStartingFamilyNameWith("TestBorderline");
    }
}
