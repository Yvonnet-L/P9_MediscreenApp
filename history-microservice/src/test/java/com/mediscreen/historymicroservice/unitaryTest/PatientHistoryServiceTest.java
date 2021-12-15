package com.mediscreen.historymicroservice.unitaryTest;


import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.exception.DataNotFoundException;
import com.mediscreen.historymicroservice.model.PatientHistory;
import com.mediscreen.historymicroservice.repository.PatientHistoryRepository;
import com.mediscreen.historymicroservice.service.PatientHistoryServiceImp;
import com.mediscreen.historymicroservice.tool.DtoBuilder;
import com.mediscreen.historymicroservice.tool.ModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PatientHistoryServiceTest {

    @InjectMocks
    PatientHistoryServiceImp patientHistoryService;

    @Mock
    PatientHistoryRepository patientHistoryRepository;

    @Mock
    DtoBuilder dtoBuilder;

    @Mock
    ModelBuilder modelBuilder;

    private List<PatientHistory> patientHistoryList;
    private List<PatientHistory> patientHistoryListPatientID2;
    private PatientHistory ph1;
    LocalDate date = LocalDate.now();

    @BeforeEach
    public void setup() {
        ph1 = new PatientHistory("MongoId1", 1, date, "commentary 1-1");
        PatientHistory ph2 = new PatientHistory("MongoId2", 1, date, "commentary 1-2");
        PatientHistory ph3 = new PatientHistory("MongoId3", 2, date, "commentary 2-1");
        PatientHistory ph4 = new PatientHistory("MongoId4", 2, date, "commentary 2-2");
        PatientHistory ph5 = new PatientHistory("MongoId5", 2, date, "commentary 2-3");
        PatientHistory ph6 = new PatientHistory("MongoId6", 3, date, "commentary 3-1");
        patientHistoryList = Arrays.asList(ph1, ph2, ph3, ph4, ph5, ph6);
        patientHistoryListPatientID2 = Arrays.asList(ph3, ph4, ph5);
    }

    // ---------- findAll() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("findAll() Test")
    public void findAllTest(){
        Mockito.when(patientHistoryRepository.findAll()).thenReturn(patientHistoryList);
        assertThat(patientHistoryService.findAll().size()).isEqualTo(6);
    }
    // ---------- findById ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("findById() Test")
    public void findByIdExistingTest(){
        Mockito.when(patientHistoryRepository.findById(any(String.class))).thenReturn(java.util.Optional.ofNullable(ph1));
        PatientHistoryDTO phDto1 = new PatientHistoryDTO("MongoId1", 1, date, "commentary 1-1");
        Mockito.when(dtoBuilder.buildPatientHistoryDTO(any(PatientHistory.class))).thenReturn(phDto1);

        assertThat(patientHistoryService.findById("MongoId1").getPatientId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findById() with id no exists Test")
    public void findByIdNoExistTest(){
        Mockito.when(patientHistoryRepository.findById("IdNotExist")).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> patientHistoryService.findById("IdNotExist"));
    }

    @Test
    @DisplayName("findById() with id no exists Test")
    public void findByIdNullTest(){
        Mockito.when(patientHistoryRepository.findById(null)).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> patientHistoryService.findById(null));
    }

    // ---------- findAllByPatientId --------------------------------------------------------------------------------
    @Test
    @DisplayName("findAllByPatientId() with patientId exists Test")
    public void findAllByPatientIdWithPatientIdExistTest(){
        Mockito.when(patientHistoryRepository.findAllByPatientId(2)).thenReturn(patientHistoryListPatientID2);

        assertThat(patientHistoryService.findAllByPatientId(2).size()).isEqualTo(3);
    }
    @Test
    @DisplayName("findAllByPatientId() with patientId not exists Test")
    public void findAllByPatientIdWithPatientIdNotExistTest(){
       List<PatientHistory> phListEmpty = new ArrayList<>();
        Mockito.when(patientHistoryRepository.findAllByPatientId(any(Integer.class))).thenReturn(phListEmpty);
        assertThat(patientHistoryService.findAllByPatientId(any(Integer.class)).size()).isEqualTo(0);
    }

    // ---------- addPatientHistory() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("addPatientHistory with good params Test")
    public void addPatientHistoryWithGoodParamsTest(){
        PatientHistoryDTO phDTO7 = new PatientHistoryDTO( 3, date, "commentary 3-2");
        PatientHistory ph7 = new PatientHistory( null,3, date, "commentary 3-2");
        Mockito.when(modelBuilder.buildPatientHistory(phDTO7)).thenReturn(ph7);
        Mockito.when(patientHistoryRepository.save(ph7)).thenReturn(ph7);
        phDTO7.setId("MongoId7");
        Mockito.when(dtoBuilder.buildPatientHistoryDTO(ph7)).thenReturn(phDTO7);

        assertThat(patientHistoryService.addPatientHistory(phDTO7).getNotes()).isEqualTo("commentary 3-2");
    }
    // ---------- updatePatientHistory() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("updatePatientHistory that exists Test")
    public void updatePatientHistoryWithIdExistTest(){
        Mockito.when(patientHistoryRepository.findById("MongoId1")).thenReturn(java.util.Optional.ofNullable(ph1));
        PatientHistoryDTO phDto1 = new PatientHistoryDTO("MongoId1", 1, date, "commentary 1-1");
        Mockito.when(modelBuilder.buildPatientHistory(phDto1)).thenReturn(ph1);
        Mockito.when(patientHistoryRepository.save(ph1)).thenReturn(ph1);
        Mockito.when(dtoBuilder.buildPatientHistoryDTO(ph1)).thenReturn(phDto1);
        assertThat(patientHistoryService.updatePatientHistory("MongoId1", phDto1).getNotes()).isEqualTo("commentary 1-1");
    }
    @Test
    @DisplayName("updatePatientHistory which no exists Test")
    public void updatePatientHistoryWithIdNotExistTest(){
        PatientHistoryDTO phDtoIdNoExist = new PatientHistoryDTO("MongoId-NoExist", 1, date, "commentary 1-1");
        Mockito.when(patientHistoryRepository.findById("MongoId-NoExist")).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> patientHistoryService.updatePatientHistory("MongoId-NoExist", phDtoIdNoExist));
    }

    // ---------- DeletePatientHistory() -----------------------------------------------------------------------------
    @Test
    @DisplayName("deletePatientHistory  Test")
    public void deletePatientHistoryExistTest(){
        patientHistoryService.deletePatientHistory("MongoId1");

        verify( patientHistoryRepository, times(1)).deleteById(any(String.class));
    }

}
