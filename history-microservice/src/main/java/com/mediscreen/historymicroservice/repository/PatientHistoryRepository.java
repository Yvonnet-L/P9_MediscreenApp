package com.mediscreen.historymicroservice.repository;

import com.mediscreen.historymicroservice.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
/**
 * Interface allowing interaction with the patientHistory table of the MonogoDB database
 */
public interface PatientHistoryRepository extends MongoRepository<PatientHistory, String> {

    List<PatientHistory> findAllByPatientId(Integer patientId);

}
