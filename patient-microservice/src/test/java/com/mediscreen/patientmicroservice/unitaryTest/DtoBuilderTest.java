package com.mediscreen.patientmicroservice.unitaryTest;


import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.tool.DtoBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DtoBuilderTest {

    @InjectMocks
    DtoBuilder dtolBuilder;

    //-----------------PatientDTO--------------------------------------------------------------------------------------------------
    @Test
    @DisplayName("PatientDTO Builder Test")
    public void builderPatientDTOTest(){
       LocalDate date = LocalDate.ofEpochDay(1966 - 12 - 31);
        Patient patient = new Patient(1, "TestNone", "Test", date,
                "F", "1 Brookside St", "100-222-3333");;
        PatientDTO patientDTOBuild = dtolBuilder.buildPatientDTO(patient);

       assertThat(patientDTOBuild.getId()).isEqualTo(patient.getIdPatient());
       assertThat(patientDTOBuild.getAddress()).isEqualTo(patient.getAddress());
       assertThat(patientDTOBuild.getDateOfBirth()).isEqualTo(patient.getDateOfBirth());
       assertThat(patientDTOBuild.getFamilyName()).isEqualTo(patient.getFamilyName());
       assertThat(patientDTOBuild.getGivenName()).isEqualTo(patient.getGivenName());
       assertThat(patientDTOBuild.getPhone()).isEqualTo(patient.getPhone());
    }
}
