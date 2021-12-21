package com.mediscreen.assessmentmicroservice.service;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientHistoryDTO;
import com.mediscreen.assessmentmicroservice.enums.TriggerTerm;
import com.mediscreen.assessmentmicroservice.exception.DataNotFoundException;
import com.mediscreen.assessmentmicroservice.proxies.HistoryMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.proxies.PatientMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.tool.UtilityMethods;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class AssessmentServiceImpl implements IAssessmentService {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

    @Autowired
    UtilityMethods utilityMethods;

    @Override
    public List<String> rechercheString() {
        List<String> resFinal = new ArrayList<>();
        List<PatientDTO> patientDTOS = patientMicroserviceProxy.getAllPatients();

        Integer nbTermes = 0;
        String termesFind;
        ArrayList<String> wordNoteList;
        for (PatientDTO p : patientDTOS) {
            List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getPatientHistoryBypatientId(p.getId());
            nbTermes = 0;

            wordNoteList = new ArrayList<>();
            String allNotesOfPatient = "";
            for (PatientHistoryDTO ph : patientHistoryDTOS) {
                allNotesOfPatient = allNotesOfPatient + " " + ph.getNotes();
                wordNoteList.add(ph.getNotes());
            }

            Integer nbTriggerTermsFind = calculNbTriggerTerms(allNotesOfPatient);
            int age = utilityMethods.ageCalculator(LocalDate.parse(p.getDateOfBirth()));
            String riskLevel = riskLevelForDiabetes(age, nbTriggerTermsFind, p.getSex());
            String resString = "Patient: " + p.getGivenName() + " " + p.getFamilyName() + " (age " + age + ")" +
                            " diabetes assessment is: " + riskLevel + " - nbTrigger: "+ nbTriggerTermsFind;
            System.out.println(resString);
            resFinal.add(resString);
        }
        return resFinal;
    }

    @Override
    public AssessmentDTO diabeteAssessmentByIdPatient(Integer patientId) {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        try {
            PatientDTO patientDTO = patientMicroserviceProxy.getPatientById(patientId);
            List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getPatientHistoryBypatientId(patientId);
            // Concatenation de toutes les notes du patient en une seule string
            String allNotesOfPatient = "";
            for (PatientHistoryDTO ph : patientHistoryDTOS) {
                allNotesOfPatient = allNotesOfPatient + " " + ph.getNotes();
            }
            int nbTriggerTermsFind = calculNbTriggerTerms(allNotesOfPatient);
            int age = utilityMethods.ageCalculator(LocalDate.parse(patientDTO.getDateOfBirth()));
            String sexe = patientDTO.getSex();

            String riskLevel = riskLevelForDiabetes(age, nbTriggerTermsFind, patientDTO.getSex());
            String resString = "Patient: " + patientDTO.getGivenName() + " " + patientDTO.getFamilyName() + " (age " + age + ")" + " diabetes assessment is: " + riskLevel;
            System.out.println(resString);
            assessmentDTO = new AssessmentDTO(patientDTO, age, riskLevel);
            return assessmentDTO;
        }catch(FeignException e){
            throw  new DataNotFoundException("Patient with this id is unknown");
        }

        //return resString;
    }

    @Override
    public List<AssessmentDTO> diabeteAssessmentByFamilyName(String familyName) {
        List<PatientDTO> patientDTOs = patientMicroserviceProxy.getPatientsStartingWith(familyName);
        List<AssessmentDTO> resStringList = new ArrayList<>();
        for(PatientDTO patientDTO: patientDTOs) {
            List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getPatientHistoryBypatientId(patientDTO.getId());
            // Concatenation de toutes les notes du patient en une seule string
            String allNotesOfPatient = "";
            for (PatientHistoryDTO ph : patientHistoryDTOS) {
                allNotesOfPatient = allNotesOfPatient + " " + ph.getNotes();
            }
            int nbTriggerTermsFind = calculNbTriggerTerms(allNotesOfPatient);
            int age = utilityMethods.ageCalculator(LocalDate.parse(patientDTO.getDateOfBirth()));
            String sexe = patientDTO.getSex();

            String riskLevel = riskLevelForDiabetes(age, nbTriggerTermsFind, patientDTO.getSex());
            String resString = "Patient: " + patientDTO.getGivenName() + " " + patientDTO.getFamilyName() + " (age " + age + ")" + " diabetes assessment is: " + riskLevel;
            System.out.println(resString);
            AssessmentDTO assessmentDTO = new AssessmentDTO(patientDTO,age,riskLevel);
            //resStringList.add(resString);
            resStringList.add(assessmentDTO);
        }
        return resStringList;
    }

    public String riskLevelForDiabetes (Integer age, Integer nbTriggers, String sexe){
        String riskLevel = "None";
        // Early onset : 3 cas ( 2, <30 ans et 1 >30 ans )
        // In Danger   : 3 cas (  2, >30 ans et 1 >30 ans )
        // Borderline  : 1 cas ( > 30 )
        if ( age < 30 && sexe.equals("H") && nbTriggers>=5 || age < 30 && sexe.equals("f") && nbTriggers>=7
               || age > 30 && nbTriggers >=8 ){
            riskLevel = "Early onset";
        }else if ( age < 30 && sexe.equals("H") && nbTriggers>=3 || age < 30 && sexe.equals("f") && nbTriggers>=4){
            riskLevel = "In Danger";
        }else if ( age > 30 && nbTriggers>=2){
            riskLevel ="Borderline";
        }
        return riskLevel;
    }


    /**
     * returns the number of trigger terms found in a string note
     * @param allNotesOfPatient
     * @return
     */
    public Integer calculNbTriggerTerms(String allNotesOfPatient) {
        Integer nbTriggerTerms = 0;
        EnumSet<TriggerTerm> triggerTerms = EnumSet.allOf(TriggerTerm.class);

        String termesFind = "";
        for (TriggerTerm triggerTerm : triggerTerms) {
            if (utilityMethods.stringIsContainedAnotherString(triggerTerm.getAbreviation(), allNotesOfPatient)) {
                nbTriggerTerms++;
                termesFind = termesFind + "-" + triggerTerm.getAbreviation();
            }

        }
        System.out.println(" -- TermesFind: " + termesFind);
        return nbTriggerTerms;
    }
}


