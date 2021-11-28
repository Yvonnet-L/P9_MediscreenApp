package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * @param famillyName
     * @return
     */
    @Override
    public Patient findPatientByFamillyName(String famillyName) {
        Patient patient = patientRepository.findByFamillyName(famillyName);
        return patient;
    }

    @Override
    public List<Patient> findByFamillyNameStartingWith(String famillyName) {
        List<Patient> patients = patientRepository.findByFamillyNameStartingWith(famillyName);
        return patients;
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient;
    }


}
