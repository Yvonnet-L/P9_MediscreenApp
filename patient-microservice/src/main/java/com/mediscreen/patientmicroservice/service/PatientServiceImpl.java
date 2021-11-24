package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService{

    @Autowired
    PatientRepository patientRepository;

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }
}
