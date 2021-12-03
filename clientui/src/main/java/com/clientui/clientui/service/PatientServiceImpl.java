package com.clientui.clientui.service;

import com.clientui.clientui.beans.Patient;
import com.clientui.clientui.dto.PatientDTO;
import com.clientui.clientui.proxies.PatientMicroserviceProxy;
import com.clientui.clientui.tool.DtoBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

    @Autowired
    DtoBuilder dtoBuilder;

    private static Logger logger = LogManager.getLogger(PatientServiceImpl.class);

    @Override
    public List<PatientDTO> getAllPatients() {
        logger.info(" ----> Launch getAllPatients()");
        List<Patient> patients = patientMicroserviceProxy.getAllPatients();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for ( Patient p: patients){
            patientDTOS.add(dtoBuilder.buildPatientDTO(p));
        }
        return patientDTOS;
    }
}
