package com.mediscreen.patientmicroservice.repository;

import com.mediscreen.patientmicroservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface allowing interaction with the patient table of the sql database thanks to JPA
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByFamilyNameStartingWith(String familyName);

    Patient findByFamilyNameAndGivenNameAndDateOfBirth(String familyName, String givenName, LocalDate dateOfBirth);

    Patient findByIdPatient(Integer id);
}
