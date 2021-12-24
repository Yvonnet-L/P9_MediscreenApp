package com.clientui.clientui.controller;


import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.service.PatientServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {


    @MockBean
    private PatientServiceImpl patientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private List<PatientDTO> patientDTOList = new ArrayList<>();
    private PatientDTO patientDTO1;
    private PatientDTO patientDTO2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        patientDTO1 = new PatientDTO(1, "TestBorderline", "Test",
                "1945-06-24", "F", "2 High St", "200-333-4444");
        patientDTO2 = new PatientDTO(2, "TestInDanger", "Test",
                "2004-06-18", "M", "3 Club Road", "300-444-5555");
        patientDTOList.add(patientDTO1);
        patientDTOList.add(patientDTO2);
    }

    //------- Get --- Home---- / --------------------------------------------
    @Test
    @DisplayName("getHome Test response 200")
    public void homeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());

    }
    //------- Get --- getPatients ---- /patient/list --------------------------------------------
    @Test
    @DisplayName("getAllPatientss No string research Test response 200")
    public void getPatientsWithNoStringOfResearchTest() throws Exception {

        Mockito.when(patientService.getAllPatients()).thenReturn(patientDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list?stringSearch="))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("patientDTOS"))
                .andExpect(model().attributeExists("stringSearch"))
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

        verify(patientService).getAllPatients();
    }
    @Test
    @DisplayName("getAllPatients with string research Test response 200")
    public void getPatientsWithStringOfResearchTest() throws Exception {

        Mockito.when(patientService.getPatientStartingFamilyNameWith(any(String.class))).thenReturn(patientDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list?stringSearch=test"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("patientDTOS"))
                .andExpect(model().attributeExists("stringSearch"))
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

        verify(patientService).getPatientStartingFamilyNameWith(any(String.class));
    }
    //------- Get --- getUpdatePatient( ---- /patient/update/{id}--------------------------------------------
    @Test
    @DisplayName("getUpdatePatHistory Test response 200")
    public void getUpdatePatHistoryTest() throws Exception {

        Mockito.when(patientService.getById(any(Integer.class))).thenReturn(patientDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/update/2"))
                .andExpect(model().attributeExists("patientDTO"))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk());

        verify(patientService).getById(any(Integer.class));
    }
    //------- Put --- updatePatient ---- /patient/update/{id}--------------------------------------------
    @Test
    @DisplayName("updatePatHistory Test redirectedUrl ok")
    public void updatePatientTest() throws Exception {

        Mockito.when(patientService.updatePatient((any(PatientDTO.class)),any(Integer.class))).thenReturn("Patient updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update/2")
                        .sessionAttr("patientDTO", patientDTO2)
                        .param("familyName", patientDTO2.getFamilyName())
                        .param("givenName" , patientDTO2.getGivenName())
                        .param("dateOfBirth", patientDTO2.getDateOfBirth())
                        .param("sex", patientDTO2.getSex())
                        .param("address", patientDTO2.getAddress())
                        .param("phone", patientDTO2.getPhone()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patient/list?stringSearch=TestInDanger&message=Patient updated"));

        verify(patientService).updatePatient((any(PatientDTO.class)),any(Integer.class));

    }

    //------- Get --- getAddPatientNote ---- /patient/add--------------------------------------------
    @Test
    @DisplayName("getAddPatientTest response 200")
    public void getAddPatientTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/add"))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk());
    }

    //------- Post --- validateNewPatHistory ---- /patient/validate--------------------------------------------
    @Test
    @DisplayName("validateNewPatHistory Test redirectedUrl ok")
    public void validatePatientHistoryTest() throws Exception {

        Mockito.when(patientService.addPatient(any(PatientDTO.class))).thenReturn("Patient added");

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                        .sessionAttr("patientDTO", patientDTO2)
                        .param("familyName", patientDTO2.getFamilyName())
                        .param("givenName" , patientDTO2.getGivenName())
                        .param("dateOfBirth", patientDTO2.getDateOfBirth())
                        .param("sex", patientDTO2.getSex())
                        .param("address", patientDTO2.getAddress())
                        .param("phone", patientDTO2.getPhone()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patient/list?stringSearch=TestInDanger&message=Patient added"));


        verify(patientService).addPatient(any(PatientDTO.class));
    }

    //------- Delete --- deletePatient ---- /patient/delete/{id}--------------------------------------------
    @Test
    @DisplayName("deletePatient Test redirectedUrl ok")
    public void deletePatientHistoryTest() throws Exception {

        Mockito.when(patientService.deletePatientById(any(Integer.class))).thenReturn("Patient deleted");

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/delete/2"))
                .andExpect(redirectedUrl("/patient/list?stringSearch=&message=Patient deleted"));

        verify(patientService).deletePatientById(any(Integer.class));
    }
}
