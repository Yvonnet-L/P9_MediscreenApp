package com.mediscreen.historymicroservice.repository;

import com.mediscreen.historymicroservice.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientHistoryRepository extends MongoRepository<PatientHistory, String> {

    List<PatientHistory> findAllByPatientId(Integer patientId);

}
