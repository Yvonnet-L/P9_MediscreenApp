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

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @Override
    public List<Patient> findAll() {
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return
     */
    @Override
    public Patient findPatientById(Integer id) {
        Patient patient = patientRepository.getById(id);
        return patient;
    }

    /** ----------------------------------------------------------------------------------------------------------
     *
     * @param firstName
     * @return
     */
    @Override
    public Patient findPatientByFirstName(String firstName) {
        Patient patient = patientRepository.findByFirstName(firstName);
        return patient;
    }


}
