package com.mediscreen.patientmicroservice.unitaryTest;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.tool.ModelBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ModelBuilderTest {

    @InjectMocks
    ModelBuilder modelBuilder;

    @Test
    @DisplayName("PatientModel Builder Test")
    public void builderPatientTest(){
        LocalDate date = LocalDate.ofEpochDay(1966 - 12 - 31);
        PatientDTO patientDTO = new PatientDTO(1, "TestNone", "Test", date,
                "F", "1 Brookside St", "100-222-3333");;
        Patient patientBuild = modelBuilder.buildPatient(patientDTO);

        assertThat(patientBuild.getIdPatient()).isEqualTo(patientDTO.getId());
        assertThat(patientBuild.getAddress()).isEqualTo(patientDTO.getAddress());
        assertThat(patientBuild.getDateOfBirth()).isEqualTo(patientDTO.getDateOfBirth());
        assertThat(patientBuild.getFamilyName()).isEqualTo(patientDTO.getFamilyName());
        assertThat(patientBuild.getGivenName()).isEqualTo(patientDTO.getGivenName());
        assertThat(patientBuild.getPhone()).isEqualTo(patientDTO.getPhone());
    }
}
