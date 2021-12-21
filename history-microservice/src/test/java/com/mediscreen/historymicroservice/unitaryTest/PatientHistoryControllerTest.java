package com.mediscreen.historymicroservice.unitaryTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.historymicroservice.controller.PatientHistoryController;
import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.exception.DataNotFoundException;
import com.mediscreen.historymicroservice.service.PatientHistoryServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PatientHistoryController.class)
@AutoConfigureMockMvc
public class PatientHistoryControllerTest {

    @MockBean
    private PatientHistoryServiceImp patientHistoryServiceImp;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;


    private PatientHistoryDTO phDTO1;
    private PatientHistoryDTO phDTO2;
    private LocalDate date;
    private DataNotFoundException dataNotFoundException;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        date = LocalDate.now();
        phDTO1 = new PatientHistoryDTO("MongoId-1", 1, date,
                "a note from a doctor on a patient, he is ill");
        phDTO2 = new PatientHistoryDTO("MongoId-2", 1, date,
                "a note from a doctor on a patient,he is in full form");
    }

    // -------- Get --- getAllPatientHistories -------------------------------------------------------
    @Test
    @DisplayName("getAllPatientHistoriesOK Test response 200")
    public void getAllPatientHistoriesOKTest() throws Exception {
        Mockito.when(patientHistoryServiceImp.findAll()).thenReturn(Arrays.asList(phDTO1,phDTO2));
        mockMvc.perform(get("/patHistories"))
                         .andExpect(status().isOk());
    }

    ///patHistories/{patientId}

    // -------- Get --- getPatientHistoryBypatientId -------------------------------------------------------
    @Test
    @DisplayName(" getPatientHistoryBypatientIdOK Test response 200")
    public void  getPatientHistoryBypatientIdOKTest() throws Exception {
        Mockito.when(patientHistoryServiceImp.findAllByPatientId(any(Integer.class))).thenReturn(Arrays.asList(phDTO1,phDTO2));
        mockMvc.perform(get("/patHistories/1"))
                .andExpect(status().isOk());
    }

    // -------- Get --- getPatientHistoryById -------------------------------------------------------
    @Test
    @DisplayName("getPatientHistoryBypatientIdOK Test response 200")
    public void getPatientHistoryByIdTest() throws Exception {
        Mockito.when(patientHistoryServiceImp.findAllByPatientId(any(Integer.class))).thenReturn(Arrays.asList(phDTO1,phDTO2));
        mockMvc.perform(get("/patHistory/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("getPatientHistoryBypatientIdNotExist Test response not found")
    public void getPatientHistoryBypatientIdNotExistTest() throws Exception {
       dataNotFoundException = new DataNotFoundException("message not found");
        Mockito.when(patientHistoryServiceImp.findAllByPatientId(any(Integer.class))).thenThrow(dataNotFoundException);
        mockMvc.perform(get("/patHistory/1"))
                .andExpect(status().isOk());
    }

    // -------- Post --- addPatientHistory -------------------------------------------------------
    @Test
    @DisplayName("addPatientHistory Test with params ok response 201 created")
    public void addPatientHistoryTestWithParamsOk() throws Exception {
        Mockito.when((patientHistoryServiceImp.addPatientHistory(any(PatientHistoryDTO.class)))).thenReturn(phDTO1);
        mockMvc.perform(post("/patHistory/add")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phDTO1)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("addPatientHistory Test with params null response 400 bad Request")
    public void addPatientHistoryTestWithParamsNull() throws Exception {

        mockMvc.perform(post("/patHistory/add")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    //  ------- Put ---- updatePatientHistory ------------------------------------------------------------------------------
    @Test
    @DisplayName("updatePatientHistory with good params response 200")
    public void updatePatientHistoryTestWithParamsOk() throws Exception {
        Mockito.when((patientHistoryServiceImp.addPatientHistory(any(PatientHistoryDTO.class)))).thenReturn(phDTO1);
        mockMvc.perform(put("/patHistory/update/1")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(phDTO1)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updatePatientHistory Test with params null response 400 bad Request")
    public void updatePatientHistoryTestWithParamsNull() throws Exception {

        mockMvc.perform(put("/patHistory/update/1")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    // ------- Delete ---- deletePatientHistory -----------------------------------------------------------------------------
    @Test
    @DisplayName("deletePatient Test response 204 No Content ")
    public void deletePatientHistoryTest() throws Exception {

        mockMvc.perform(delete("/patHistory/delete/1"))
                .andExpect(status().isNoContent());
    }

}
