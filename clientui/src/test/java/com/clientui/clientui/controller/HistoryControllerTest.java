package com.clientui.clientui.controller;


import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.dto.PatientHistoryDTO;
import com.clientui.clientui.service.HistoryServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HistoryController.class)
public class HistoryControllerTest {

    @MockBean
    private HistoryServiceImpl historyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private List<PatientHistoryDTO> patientHistoryDTOList = new ArrayList<>();
    private PatientHistoryDTO patientHistoryDTO1;
    private PatientHistoryDTO patientHistoryDTO2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        patientHistoryDTO1 = new PatientHistoryDTO(2, LocalDate.now().toString(), " StringNotePatient2");
        patientHistoryDTO2 = new PatientHistoryDTO(2, LocalDate.now().toString(), " StringNotePatient2");
        patientHistoryDTOList.add(patientHistoryDTO1);
        patientHistoryDTOList.add(patientHistoryDTO2);
    }


    //------- Get --- getAllPatientHistories ---- /patHistories --------------------------------------------
    @Test
    @DisplayName("getAllPatientHistories Test response 200")
    public void getAllPatientHistoriesTest() throws Exception {


        Mockito.when(historyService.findAllPatientHistories()).thenReturn(patientHistoryDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistories"))
                .andExpect(model().attributeExists("patientHistories"))
                .andExpect(view().name("patHistory/patientHistories"))
                .andExpect(status().isOk());

        verify(historyService).findAllPatientHistories();
    }

    //------- Get --- getAllPatientHistories ---- /patHistories/{patientId}--------------------------------------------
    @Test
    @DisplayName("getPatientHistory Test response 200")
    public void getPatientHistoryTest() throws Exception {

        Mockito.when(historyService.findPatientHistoriesNotes(2)).thenReturn(patientHistoryDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistories/2"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("patientHistories"))
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("patHistory/patientNotes"))
                .andExpect(status().isOk());

        verify(historyService).findPatientHistoriesNotes(2);
    }

    //------- Get --- getAddPatientNote ---- /patHistory/add/{IdPatient}}--------------------------------------------
    @Test
    @DisplayName("getPatientHistory Test response 200")
    public void getAddPatientNoteTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/add/2"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("msgAlertDate"))
                .andExpect(model().attributeExists("patientHistoryDTO"))
                .andExpect(view().name("patHistory/add"))
                .andExpect(status().isOk());
    }

    //------- Post --- validateNewPatHistory ---- /patHistory/validate--------------------------------------------
    @Test
    @DisplayName("validateNewPatHistory Test redirectedUrl ok")
    public void validateNewPatHistoryTest() throws Exception {

        Mockito.when(historyService.addPatientHistory(any(PatientHistoryDTO.class))).thenReturn("New note added");
        PatientDTO patientDTO = new PatientDTO(2, "TestBorderline", "Test",
                "1945-06-24", "F", "2 High St", "200-333-4444");

        mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/validate")
                        .sessionAttr("patientHistoryDTO", patientHistoryDTO2)
                        .param("patientId", patientHistoryDTO2.getPatientId().toString())
                        .param("date", patientHistoryDTO2.getDate())
                        .param("notes", patientHistoryDTO2.getNotes()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patHistories/2?message=New note added"));

        verify(historyService).addPatientHistory(any(PatientHistoryDTO.class));

    }

    //------- Get --- getUpdatePatHistory ---- /patHistory/update/{id}&{patientid}--------------------------------------------
    @Test
    @DisplayName("getUpdatePatHistory Test response 200")
    public void getUpdatePatHistoryTest() throws Exception {

        Mockito.when(historyService.findPatientHistoryById(any(String.class))).thenReturn(patientHistoryDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/update/2&3"))
                .andExpect(model().attributeExists("patientHistoryDTO"))
                .andExpect(view().name("patHistory/update"))
                .andExpect(status().isOk());

        verify(historyService).findPatientHistoryById(any(String.class));
    }


    //------- Post --- updatePatHistory ---- /patHistory/update/{id}--------------------------------------------
    @Test
    @DisplayName("updatePatHistory Test redirectedUrl ok")
    public void updatePatHistoryTest() throws Exception {

        Mockito.when(historyService.updatePatientHistory(any(String.class), (any(PatientHistoryDTO.class)))).thenReturn("Note updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/patHistory/update/2")
                        .sessionAttr("patientHistoryDTO", patientHistoryDTO2)
                        .param("patientId", patientHistoryDTO2.getPatientId().toString())
                        .param("date", patientHistoryDTO2.getDate())
                        .param("notes", patientHistoryDTO2.getNotes()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patHistories/2?message=Note updated"));

        verify(historyService).updatePatientHistory(any(String.class), (any(PatientHistoryDTO.class)));

    }

    //------- Delete --- deletePatientHistory ---- /patHistory/delete/{id}&{patientid}--------------------------------------------
    @Test
    @DisplayName("deletePatientHistory Test redirectedUrl ok")
    public void deletePatientHistoryTest() throws Exception {

        Mockito.when(historyService.deletePatientHistory(any(String.class))).thenReturn("Note deleted");

        mockMvc.perform(MockMvcRequestBuilders.get("/patHistory/delete/2&3"))
                .andExpect(redirectedUrl("/patHistories/3?message=Note deleted"));

        verify(historyService).deletePatientHistory("2");
    }

}