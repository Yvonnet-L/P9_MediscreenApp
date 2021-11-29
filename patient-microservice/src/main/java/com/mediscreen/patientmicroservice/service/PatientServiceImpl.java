package com.mediscreen.patientmicroservice.service;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import com.mediscreen.patientmicroservice.exception.DataNotFoundException;
import com.mediscreen.patientmicroservice.model.Patient;
import com.mediscreen.patientmicroservice.repository.PatientRepository;
import com.mediscreen.patientmicroservice.tool.DtoBuilder;
import com.mediscreen.patientmicroservice.tool.ModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService{

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ModelBuilder modelBuilder;

    @Autowired
    DtoBuilder dtoBuilder;

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    @Override
    public List<PatientDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
          patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }

    /**  ----------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return
     */
    @Override
    public PatientDTO findPatientById(Integer id) {
        PatientDTO patientDTO = dtoBuilder.buildPatientDTO(patientRepository.getById(id));
        return patientDTO;
    }

    /** ----------------------------------------------------------------------------------------------------------
     *
     * @param familyName
     * @return
     */
    @Override
    public PatientDTO findPatientByFamilyName(String familyName) {
        PatientDTO patientDTO = dtoBuilder.buildPatientDTO(patientRepository.findByFamilyName(familyName));
        return patientDTO;
    }

    @Override
    public List<PatientDTO> findByFamilyNameStartingWith(String familyName) {
        List<Patient> patients = patientRepository.findByFamilyNameStartingWith(familyName);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p: patients) {
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
            return patientDTOS;
    }

    @Override
    public PatientDTO findById(Integer id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
               new DataNotFoundException("Patient with this id is unknown"));
        PatientDTO patientDTO = dtoBuilder.buildPatientDTO(patient);
        return patientDTO;
    }


}
