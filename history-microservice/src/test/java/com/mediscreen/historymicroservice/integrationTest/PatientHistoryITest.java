package com.mediscreen.historymicroservice.integrationTest;

import com.mediscreen.historymicroservice.dto.PatientHistoryDTO;
import com.mediscreen.historymicroservice.exception.DataNotFoundException;
import com.mediscreen.historymicroservice.service.PatientHistoryServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatientHistoryITest {

    @Autowired
    private PatientHistoryServiceImp patientHistoryService;

    @Test
    public void patientHistoryTest(){

        LocalDate date = LocalDate.now();
        PatientHistoryDTO phDTO1 = new PatientHistoryDTO(1, date, "commentary 1-1");


        // -- FindAll --
        List<PatientHistoryDTO> patientHistories = patientHistoryService.findAll();
        assertTrue(patientHistories.size()>0);

        // --  Post --
        int patientsSize = patientHistories.size();
        PatientHistoryDTO phDTOPost = patientHistoryService.addPatientHistory(phDTO1);
        List<PatientHistoryDTO> patientHistorisAfterPost = patientHistoryService.findAll();

        assertEquals(patientHistorisAfterPost.size(), patientsSize + 1);
        assertTrue(phDTOPost.getId()!=null);

        // -- FindById --
        PatientHistoryDTO phDTOFind = patientHistoryService.findById(phDTOPost.getId());
        assertEquals(phDTOFind.getId(), phDTOPost.getId());

        // -- FindByPatientId --
        List<PatientHistoryDTO> patientHistoriesDTOFind = patientHistoryService.findAllByPatientId(phDTOPost.getPatientId());
        assertTrue(patientHistoriesDTOFind.size()>0);

        // --  Put  ---
        phDTOFind.setNotes("Note modified");
        PatientHistoryDTO phDTOUpdate = patientHistoryService.updatePatientHistory(phDTOFind.getId(), phDTOFind);

        assertEquals(phDTOUpdate.getNotes(), "Note modified");
        assertEquals(phDTOUpdate.getId(), phDTOFind.getId());

        // -- Delete ---
        patientHistoryService.deletePatientHistory(phDTOPost.getId());
        List<PatientHistoryDTO> patientHistoriesAfterDelete = patientHistoryService.findAll();

        assertEquals(patientHistories.size(),  patientHistoriesAfterDelete.size());
        assertThrows(DataNotFoundException.class, () -> patientHistoryService.findById(phDTOPost.getId()));
    }
}
