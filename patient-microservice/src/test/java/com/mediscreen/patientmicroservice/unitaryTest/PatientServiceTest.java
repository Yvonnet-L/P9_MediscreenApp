package com.mediscreen.patientmicroservice.unitaryTest;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.exception.DataAlreadyExistException;
import com.mediscreen.patientmicroservice.exception.DataNotFoundException;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import com.mediscreen.patientmicroservice.service.PatientServiceImpl;
import com.mediscreen.patientmicroservice.tool.DtoBuilder;
import com.mediscreen.patientmicroservice.tool.ModelBuilder;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    PatientServiceImpl patientService ;

    @Mock
    PatientRepository patientRepository;


    @Mock
    DtoBuilder dtoBuilder;

    @Mock
    ModelBuilder modelBuilder;

    private List<Patient> patientList;
    private Patient patient1;
    LocalDate date = LocalDate.ofEpochDay(1966 - 12 - 31);

    @BeforeEach
    public void setup() {

         patient1 = new Patient(1, "TestNone1", "Test1", date,
                "F", "1 Brookside St", "100-222-3333");
        Patient patient2 = new Patient(2, "NoTestNone2", "Test2", date,
                "F", "1 Brookside St", "100-222-3333");
        Patient patient3 = new Patient(3, "TestNone3", "Test3", date,
                "F", "1 Brookside St", "100-222-3333");
        Patient patient4 = new Patient(4, "TestNone4", "Test4", date,
                "F", "1 Brookside St", "100-222-3333");
        patientList = Arrays.asList(patient1, patient2, patient3, patient4);
    }

    // ---------- findAll() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("findAll() Test")
    public void findAllTest(){
        Mockito.when(patientRepository.findAll()).thenReturn(patientList);

        assertThat(patientService.findAll().size()).isEqualTo(4);
    }

    // ---------- findById ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("findById() Test")
    public void findByIdExistingTest(){
        Mockito.when(patientRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(patient1));
        PatientDTO patientDTO = new PatientDTO(1, "TestNone1", "Test1", date,
                "F", "1 Brookside St", "100-222-3333");
        Mockito.when(dtoBuilder.buildPatientDTO(any(Patient.class))).thenReturn(patientDTO);

        assertThat(patientService.findById(1).getFamilyName()).isEqualTo("TestNone1");
    }

    @Test
    @DisplayName("findById() with id no exists Test")
    public void findByIdNoExistTest(){
        Mockito.when(patientRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> patientService.findById(any(Integer.class)));
    }

    // ---------- findByFamilyNameStartingWith() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("findByFamilyNameStartingWith Test")
    public void findByFamilyNameStartingWithExistTest(){
        Mockito.when(patientRepository.findByFamilyNameStartingWith("TestNone")).thenReturn(patientList);

        assertThat(patientService.findByFamilyNameStartingWith("TestNone").size()).isEqualTo(4);
    }
    @Test
    @DisplayName("findByFamilyNameStartingWith Test")
    public void findByFamilyNameStartingWithNullTest(){
        List<Patient> patientListEmpty = new ArrayList<>();
        Mockito.when(patientRepository.findByFamilyNameStartingWith(null)).thenReturn(patientListEmpty);

        assertThat(patientService.findByFamilyNameStartingWith(null).size()).isEqualTo(0);
    }

    // ---------- updatePatient() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("updatePatient with good params Test")
    public void updatePatientWithGoodParamsTest(){
        PatientDTO patientDTO = new PatientDTO(1, "TestNone1", "Test1", date,
            "F", "New Address", "100-222-3333");
        Mockito.when(patientRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.ofNullable(patient1));
        Mockito.when(modelBuilder.buildPatient(any(PatientDTO.class))).thenReturn(patient1);
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        Mockito.when(dtoBuilder.buildPatientDTO(any(Patient.class))).thenReturn(patientDTO);

        assertThat(patientService.updatePatient(1, patientDTO).getAddress()).isEqualTo("New Address");
    }

    @Test
    @DisplayName("updatePatient with id not foundTest")
    public void updatePatientWithIdNotFoundTest(){
        PatientDTO patientDTO = new PatientDTO(1, "TestNone1", "Test1", date,
                "F", "New Address", "100-222-3333");
        Mockito.when(patientRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.empty());

        assertThrows(DataNotFoundException.class, () -> patientService.updatePatient(99, patientDTO));
    }
    // ---------- addPatient() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("addPatient with good params Test")
    public void addPatientWithGoodParamsTest(){
        PatientDTO patientDTO5 = new PatientDTO( "TestNone5", "Test5", date,
                "F", "New Address", "100-222-3333");
        Mockito.when(patientRepository.findByFamilyNameAndGivenNameAndDateOfBirth(any(String.class), any(String.class),
                                                                               any(LocalDate.class))).thenReturn(null);
        Mockito.when(modelBuilder.buildPatient(any(PatientDTO.class))).thenReturn(patient1);
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        patientDTO5.setId(5);
        Mockito.when(dtoBuilder.buildPatientDTO(any(Patient.class))).thenReturn(patientDTO5);

        assertThat(patientService.addPatient(patientDTO5).getFamilyName()).isEqualTo("TestNone5");

    }

    @Test
    @DisplayName("addPatient with similary params found --> DataAlreadyExistException")
    public void addPatientWithSimilaryDataFoundTest(){
        PatientDTO patientDTO5 = new PatientDTO( "TestNone5", "Test5", date,
                "F", "New Address", "100-222-3333");
        Mockito.when(patientRepository.findByFamilyNameAndGivenNameAndDateOfBirth(any(String.class), any(String.class),
                any(LocalDate.class))).thenReturn(patient1);

        assertThrows(DataAlreadyExistException.class, () -> patientService.addPatient(patientDTO5));
    }

    // ---------- deletePatient() ---------------------------------------------------------------------------------------
    @Test
    @DisplayName("deletePatient with good id Test")
    public void deletePatientExistTest(){
        Mockito.when(patientRepository.findByIdPatient(any(Integer.class))).thenReturn(patient1);
        patientService.deletePatient(any(Integer.class));

        verify( patientRepository, times(1)).findByIdPatient(any(Integer.class));
        verify( patientRepository, times(1)).delete(any(Patient.class));
    }
    @Test
    @DisplayName("deletePatient with  id not existTest")
    public void deletePatientNotExistTest(){
        Mockito.when(patientRepository.findByIdPatient(any(Integer.class))).thenReturn(null);

        assertThrows(DataNotFoundException.class, () ->  patientService.deletePatient(any(Integer.class)));
    }

}
