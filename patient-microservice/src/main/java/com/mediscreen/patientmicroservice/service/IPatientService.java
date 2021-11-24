package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;

import java.util.List;

public interface IPatientService {

    public List<Patient> findAll();

}
