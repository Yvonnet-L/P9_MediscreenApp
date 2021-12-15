package com.mediscreen.patientmicroservice.unitaryTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientmicroservice.controller.PatientController;
import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.service.PatientServiceImpl;
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
@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    private PatientServiceImpl patientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private PatientDTO patient1DTO;
    private PatientDTO patient2DTO;
    private LocalDate date;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        date = LocalDate.ofEpochDay(1966 - 12 - 31);
        patient1DTO = new PatientDTO(1, "TestNone", "Test", date,
                "F", "1 Brookside St", "100-222-3333");
        patient1DTO = new PatientDTO(2, "TestNone,", "Test2", date,
                "M", "1 Brookside St", "100-222-3333");
    }

    // ------- Get ---  getAllPatients ----------------------------------------------------------------------------
    @Test
    @DisplayName("getAllPatientsOK Test response 200 on ")
    public void getAllPatientsOKTest() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(Arrays.asList(patient1DTO, patient2DTO));
        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk());
    }

    // ------- Get ---  getPatientById ----------------------------------------------------------------------------
    @Test
    @DisplayName("getPatientById Test response 200 on ")
    public void getPatientByIdTest() throws Exception {
        Mockito.when(patientService.findById(any(Integer.class))).thenReturn(patient1DTO);
        mockMvc.perform(get("/patient/1"))
                .andExpect(status().isOk());
    }

    //  ----- Get --- getPatientsStartingWith ----------------------------------------------------------------------
    @Test
    @DisplayName("getPatientsStartingWith Test response 200 on ")
    public void getPatientsStartingWithTest() throws Exception {
        Mockito.when(patientService.findByFamilyNameStartingWith(any(String.class))).thenReturn(Arrays.asList(patient1DTO, patient2DTO));
        mockMvc.perform(get("/patients/family/family"))
                .andExpect(status().isOk());
    }
    //  ------- Post ---- addPatient ------------------------------------------------------------------------------
    @Test
    @DisplayName("addPatient Test response 201 on ")
    public void addPatientOkTest() throws Exception {
        Mockito.when(patientService.addPatient(any(PatientDTO.class))).thenReturn(patient1DTO);
        mockMvc.perform(post("/patient/add")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient1DTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("addPatient Test response 400 on ")
    public void addPatientParamsNullTest() throws Exception {

        PatientDTO patientNull = null;
        mockMvc.perform(post("/patient/add")
                .contentType("application/json")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientNull)))
                .andExpect(status().isBadRequest());
    }

    //  ------- Put ---- updatePatient ------------------------------------------------------------------------------
    @Test
    @DisplayName("addPatient Test response 200  ")
    public void UpdatePatientTest() throws Exception {
        Mockito.when(patientService.updatePatient(any(Integer.class), any(PatientDTO.class))).thenReturn(patient1DTO);
        mockMvc.perform(put("/patient/update/1")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1DTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("addPatient Test with bad Params response 400  ")
    public void UpdatePatientBadParamsTest() throws Exception {
        PatientDTO patientNull = null;
        mockMvc.perform(put("/patient/update/1")
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientNull)))
                .andExpect(status().isBadRequest());
    }
    // ------- Delete ---- deletePatient -----------------------------------------------------------------------------
    @Test
    @DisplayName("deletePatient Test response No Content ")
    public void deletePatientTest() throws Exception {

        mockMvc.perform(delete("/patient/delete/1"))
                .andExpect(status().isNoContent());
    }
}
