package com.mediscreen.patientmicroservice.integrationTest;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.exception.DataNotFoundException;
import com.mediscreen.patientmicroservice.service.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientServiceImpl patientService;

    @Test
    public void patientTest() {


        LocalDate date = LocalDate.ofEpochDay(1966-12-31);
        PatientDTO patientDTO = new PatientDTO( "TestNonePost", "TestPost", date,
                "F", "1 Brookside St", "100-222-3333");

        // -- FindAll --
        List<PatientDTO> patients = patientService.findAll();
        assertTrue(patients.size()>0);

        // --  Post --
        int patientsSize = patients.size();
        PatientDTO patientDTOPost = patientService.addPatient(patientDTO);
        List<PatientDTO> patientsAfterPost = patientService.findAll();

        assertEquals(patientsAfterPost.size(), patientsSize + 1);
        assertTrue(patientDTOPost.getId()!=null);

        // -- FindById --
        PatientDTO patientDTOFind = patientService.findById(patientDTOPost.getId());
        assertEquals(patientDTOFind.getId(), patientDTOPost.getId());

        // --  Put  ---
        patientDTOFind.setAddress("New address");
        PatientDTO patientDTOUpdate = patientService.updatePatient(patientDTOFind.getId(), patientDTOFind);

        assertEquals(patientDTOUpdate.getAddress(), "New address");
        assertEquals(patientDTOUpdate.getId(), patientDTOFind.getId());

        // -- Delete ---
        patientService.deletePatient(patientDTOPost.getId());
        List<PatientDTO> patientsAfterDelete = patientService.findAll();

        assertEquals(patients.size(), patientsAfterDelete.size());
        assertThrows(DataNotFoundException.class, () -> patientService.findById(patientDTOPost.getId()));
    }
}
