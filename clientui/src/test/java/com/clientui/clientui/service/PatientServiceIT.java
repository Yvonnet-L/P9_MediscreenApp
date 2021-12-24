package com.clientui.clientui.service;

import com.clientui.clientui.dto.PatientDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatientServiceIT {

    @Autowired
    private PatientServiceImpl patientService;

    @Test
    public void patientTest() {


        PatientDTO patientDTO1 = new PatientDTO(null, "TestBorderline2", "Test",
                "1945-06-24", "F", "2 High St", "200-333-4444");

        // -- FindAll --
        List<PatientDTO> patients = patientService.getAllPatients();
        assertTrue(patients.size()>0);

        // --  Post --
        int patientsSize = patients.size();
        String message = patientService.addPatient(patientDTO1);
        List<PatientDTO> patientsAfterPost = patientService.getAllPatients();

        assertEquals(patientsAfterPost.size(), patientsSize + 1);
        assertTrue(message.equals("New patient added"));

        // -- FindByFamilyName --
        List<PatientDTO> patientDTOs= patientService.getPatientStartingFamilyNameWith("TestBorderline2");
        PatientDTO patientDTOPost = patientDTOs.get(0);
        assertTrue(patientDTOs.size()>0);

        // -- FindById --
        PatientDTO patientDTOFind = patientService.getById(patientDTOPost.getId());
        assertEquals(patientDTOFind.getId(), patientDTOPost.getId());

        // --  Put  ---
        patientDTOFind.setAddress("New address");
        String messagePut = patientService.updatePatient(patientDTOFind,patientDTOFind.getId() );
        PatientDTO patientDTOUpdate = patientService.getById(patientDTOFind.getId());
        assertEquals(messagePut, "Patient Updated");
        assertEquals(patientDTOUpdate.getId(), patientDTOFind.getId());

        // -- Delete ---
        String messageDelete = patientService.deletePatientById(patientDTOPost.getId());
        List<PatientDTO> patientsAfterDelete = patientService.getAllPatients();

        assertEquals(patients.size(), patientsAfterDelete.size());

    }
}
