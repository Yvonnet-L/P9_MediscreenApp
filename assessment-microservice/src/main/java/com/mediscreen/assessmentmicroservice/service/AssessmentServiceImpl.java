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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Class containing the processing methods of the Assessment processing business logic
 */
@Service
public class AssessmentServiceImpl implements IAssessmentService {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

    @Autowired
    UtilityMethods utilityMethods;

    private static Logger logger = LogManager.getLogger(AssessmentServiceImpl.class);

    /**
     *
     * Method which orchestrates the creation of assessment
     * it calls on the patientmicroserviceProxy to retrieve the patient thanks to his id, in order to be able to
     * calculate his age by the ageCalculator method of UtilityMethods. It also calls the historyMicroserviceProxy
     * to retrieve all the notes thanks to the patientId, all the notes will be concatenated then send to the
     * calculNbTriggerTerms method.
     * Finally, thanks to the age, the number of Trigger terms and the sex of the person, the riskLevelForDiab method
     * is called.
     * Using all the parameters the Assessment is constructed to be returned.
     *
     * @param patientId
     * @return assessmentDTO  bilan result of level diabetes risks
     */
    @Override
    public AssessmentDTO getDiabeteAssessmentByIdPatient(Integer patientId) {

        logger.info(" ---> getDiabeteAssessmentByIdPatient with patientId: " +patientId);
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
    }

    /**
     * Method which orchestrates the creation of assessments for a List of Patient
     * This method follows the same logic as getDiabeteAssessmentByIdPatient but listing all patient find by
     * patientMicroserviceProxy.getPatientsStartingWith (familyName)
     *
     * @param familyName
     * @return  List<AssessmentDTO> resStringList -  bilans result of level diabetes risks
     */
    @Override
    public List<AssessmentDTO> getDiabeteAssessmentByFamilyName(String familyName) {
        logger.info(" ---> getDiabeteAssessmentByFamilyName with familyName: "+familyName);
        List<PatientDTO> patientDTOs = patientMicroserviceProxy.getPatientsStartingWith(familyName);
        List<AssessmentDTO> resStringList = new ArrayList<>();
        for(PatientDTO patientDTO: patientDTOs) {
            List<PatientHistoryDTO> patientHistoryDTOS = historyMicroserviceProxy.getPatientHistoryBypatientId(patientDTO.getId());
            // Concatenation of all patient notes into a single string
            String allNotesOfPatient = "";
            for (PatientHistoryDTO ph : patientHistoryDTOS) {
                allNotesOfPatient = allNotesOfPatient + " " + ph.getNotes();
            }
            int nbTriggerTermsFind = calculNbTriggerTerms(allNotesOfPatient);
            int age = utilityMethods.ageCalculator(LocalDate.parse(patientDTO.getDateOfBirth()));
            String sexe = patientDTO.getSex();

            String riskLevel = riskLevelForDiabetes(age, nbTriggerTermsFind, patientDTO.getSex());
            String resString = "Patient: " + patientDTO.getGivenName() + " " + patientDTO.getFamilyName() +
                                                  " (age " + age + ")" + " diabetes assessment is: " + riskLevel;
            System.out.println(resString);
            AssessmentDTO assessmentDTO = new AssessmentDTO(patientDTO,age,riskLevel);
            resStringList.add(assessmentDTO);
        }
        return resStringList;
    }

    /**
     * Primary method of assessing a patient's diabetes risk based on age, gender and the number of critical terms
     * found in the notes. It returns the patient's cricity level which can be 4 levels: None, Early onset, In Danger, Borderline
     *
     * @param age
     * @param nbTriggers
     * @param sexe
     * @return String riskLevel - 4lvls exist: None, Early onset, In Danger, Borderline
     */
    public String riskLevelForDiabetes (Integer age, Integer nbTriggers, String sexe){
        logger.info(" ---> riskLevelForDiabetes with age: " + age + " - nbTriggers: " + nbTriggers +" - sexe: "+sexe);
        /**
         * Early onset : 3 cas ( 2, <30 ans et 1 >30 ans )
         * In Danger   : 3 cas (  2, >30 ans et 1 >30 ans )
         * Borderline  : 1 cas ( > 30 ) */
        String riskLevel = "None";
        if ( age < 30 && sexe.equals("M") && nbTriggers>=5 || age < 30 && sexe.equals("F") && nbTriggers>=7
               || age > 30 && nbTriggers >=8 ){
            riskLevel = "Early onset";
        }else if ( age < 30 && sexe.equals("M") && nbTriggers>=3 || age < 30 && sexe.equals("F") && nbTriggers>=4
            || age >= 30 && nbTriggers>=6){
            riskLevel = "In Danger";
        }else if ( age > 30 && nbTriggers>=2){
            riskLevel ="Borderline";
        }
        return riskLevel;
    }

    /**
     * returns the number of trigger terms found in a string note
     *
     * @param allNotesOfPatient
     * @return Integer nbTriggerTerms
     */
    public Integer calculNbTriggerTerms(String allNotesOfPatient) {
        logger.info(" ---> calculNbTriggerTerms ");
        Integer nbTriggerTerms = 0;
        EnumSet<TriggerTerm> triggerTerms = EnumSet.allOf(TriggerTerm.class);

        String termesFind = "";
        for (TriggerTerm triggerTerm : triggerTerms) {
            if (stringIsContainedAnotherString(triggerTerm.getAbreviation(), allNotesOfPatient)) {
                nbTriggerTerms++;
                termesFind = termesFind + "-" + triggerTerm.getAbreviation();
            }
        }
        System.out.println(" -- TermesFind: " + termesFind);
        return nbTriggerTerms;
    }


    /**
     * this method allows to know if a character string is included in another string, by returning a boolean result.
     * Using indexOf, we retrieve the location of the start of the corresponding string, but to complete
     * the verification we retrieve the word by extracting it from the string using the index and the length
     * of the string sought to then compare them.
     *
     * @param sResearch String
     * @param doc       String
     * @return exist Boolean
     */
    // wordNoteList = new ArrayList<String>(Arrays.asList(allNotesOfPatient.split("[^a-zA-Z0-9]+")));
    public Boolean stringIsContainedAnotherString(String sResearch, String doc) {
        logger.info(" ---> stringIsContainedAnotherString ");
        Boolean exist = false;
        if(sResearch!=null & doc!=null){
            int index = doc.indexOf(sResearch);
            if (index >= 0) {
                String sousChaine = doc.substring(index, index + sResearch.length());
                System.out.println("-----------------     doc   : " + sousChaine);
                System.out.println("----------------- sResearch : " + sResearch);
                if (sResearch.equalsIgnoreCase(sousChaine)) {
                    exist = true;
                }
            }
        }
        return exist;
    }
}


