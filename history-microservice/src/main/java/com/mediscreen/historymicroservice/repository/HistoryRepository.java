package com.mediscreen.historymicroservice.repository;

import com.mediscreen.historymicroservice.model.PatientHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryRepository extends MongoRepository<PatientHistory, Integer> {

    List<PatientHistory> findAllByPatientId(Integer patientId);
}
