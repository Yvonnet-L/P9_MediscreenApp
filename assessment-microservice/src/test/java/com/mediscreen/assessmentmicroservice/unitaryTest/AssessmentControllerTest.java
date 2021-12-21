package com.mediscreen.assessmentmicroservice.unitaryTest;


import com.mediscreen.assessmentmicroservice.controller.AssessmentController;
import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import com.mediscreen.assessmentmicroservice.service.AssessmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AssessmentController.class)
@AutoConfigureMockMvc
public class AssessmentControllerTest {

    @MockBean
    private AssessmentServiceImpl assessmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;



    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    @DisplayName("getHome() Test")
    public void getHomeTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


    //@Test
    @DisplayName("getAssessmentForPatientByIdPatientTest")
    public void getAssessmentForPatientByIdPatientTest() throws Exception {
     AssessmentDTO assessmentDTO1;
         AssessmentDTO assessmentDTO2;
         List<AssessmentDTO> assessmentDTOList;
         PatientDTO patientDTO;
        patientDTO = new PatientDTO(1,"LastName","FirstName", "date", "M","address", "phoneNumber");
        assessmentDTO1 = new AssessmentDTO(patientDTO, 24, "None" );
        assessmentDTO2 = new AssessmentDTO(patientDTO, 28, "None" );
        assessmentDTOList = Arrays.asList(assessmentDTO1,assessmentDTO2);

        Mockito.when(assessmentService.diabeteAssessmentByIdPatient(1)).thenReturn(assessmentDTO1);

        mockMvc.perform(get("/assess/id/1"))
                .andExpect(status().isOk());
    }
}
