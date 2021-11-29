package com.mediscreen.patientmicroservice.repository;

import com.mediscreen.patientmicroservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByFamilyName(String familyName);

    List<Patient> findByFamilyNameStartingWith(String familyName);
}
