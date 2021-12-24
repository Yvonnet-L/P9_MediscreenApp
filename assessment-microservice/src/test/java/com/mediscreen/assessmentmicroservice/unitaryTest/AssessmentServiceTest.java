package com.mediscreen.assessmentmicroservice.unitaryTest;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientDTO;
import com.mediscreen.assessmentmicroservice.dto.PatientHistoryDTO;
import com.mediscreen.assessmentmicroservice.proxies.HistoryMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.proxies.PatientMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.service.AssessmentServiceImpl;
import com.mediscreen.assessmentmicroservice.tool.UtilityMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @InjectMocks
    AssessmentServiceImpl assessService;

    @Mock
    HistoryMicroserviceProxy historyMsProxy;

    @Mock
    PatientMicroserviceProxy patientMsProxy;

    @Mock
    UtilityMethods utilityMethods;

    private PatientDTO pat1Over30;
    private PatientDTO pat2Under30;
    private PatientHistoryDTO phDtoPat1;
    private PatientHistoryDTO phDtoPat2;
    private LocalDate birthDate = LocalDate.now();

    List<PatientDTO> patientDTOList;
    List<PatientHistoryDTO> phDTOList;

    @BeforeEach
    public void setup(){

        pat1Over30 = new PatientDTO(1,"LastName1","FirstName1",
                String.valueOf(birthDate.minusYears(40)), "M","address", "phoneNumber");
        pat2Under30 = new PatientDTO(2,"LastName1","FirstName2",
                String.valueOf(birthDate.minusYears(19)), "M","address", "phoneNumber");
        phDtoPat1 = new PatientHistoryDTO( "idPh", 1, "DateOfCreation",
                "2 terms Hémoglobine A1C  Taille" );
        phDtoPat2 = new PatientHistoryDTO( "idPh", 2, "DateOfCreation",
                "Vertige Rechute Réaction");
    }
    /** ----- list of TriggerTerms ---------------------------------------------
     ( Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur,
             Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp )
     */


    /**
     * ---- Test ------- getDiabeteAssessmentByIdPatient-------------------------------------------------------------
     */

    @Test
    @Tag("getDiabeteAssessmentByIdPatient")
    @DisplayName(" Risk level None with 4 Patients, 2 mem and 2 women with each one aged over 30 and the other under")
    public void AssessmentByIdWithPatientDiabeteRiskNone(){
        // Women < 30 years  - up to 3 TriggerTerms
            pat2Under30.setSex("F");
            phDtoPat2.setNotes(" 3 trigger terms: Taille, Poids, Fumeur");
            phDTOList = Arrays.asList(phDtoPat2);
             Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
             Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
             Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("None");
        // Women > 30 years  - up to 1 TriggerTerm
            pat1Over30.setSex("F");
            phDtoPat1.setNotes(" 1 trigger terms: Rechute");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("None");
        // Men < 30 years    - up to 2 TriggerTerms
            pat2Under30.setSex("M");
            phDtoPat2.setNotes(" 2 trigger terms: Réaction , Anticorp");
            phDTOList = Arrays.asList(phDtoPat2);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("None");
        // Men > 30 years    - up to 1 TriggerTerm
            pat1Over30.setSex("M");
            phDtoPat1.setNotes(" 1 trigger terms: Rechute");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("None");
    }
    @Test
    @Tag("getDiabeteAssessmentByIdPatient")
    @DisplayName(" Risk level Borderline Test with 2 Patients, 1 mem and 1 women with each one aged over 30")
    public void AssessmentByIdWithPatientDiabeteRiskBorderlineTest(){
        // Women < 30 years  - cannot be BorderLine, with 4 terms it goes directly to the In Danger risk

        // Women > 30 years  - up to 2-5 TriggerTerms
            pat1Over30.setSex("F");
            phDtoPat1.setNotes(" 2 trigger terms: Anormal , Cholestérol");

            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 2 - TriggerTerms
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
            // - 3 - TriggerTerms
                 phDtoPat1.setNotes(" 3 trigger terms: Anormal , Cholestérol, Taille");
                 phDTOList = Arrays.asList(phDtoPat1);
                 assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
             // - 4 - TriggerTerms
                phDtoPat1.setNotes(" 4 trigger terms: Anormal , Cholestérol, Taille Hémoglobine A1C");
                phDTOList = Arrays.asList(phDtoPat1);
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
             // - 5 - TriggerTerms differents + more occurence of Fumeur for verified that only one is taken
                phDtoPat1.setNotes(" 5 trigger terms: Anormal , Cholestérol, Taille Hémoglobine A1C Fumeur Fumeur Fumeur");
                phDTOList = Arrays.asList(phDtoPat1);
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");

        // Men < 30 years    - cannot be BorderLine, with 3 terms it goes directly to the In Danger risk

        // Men > 30 years    - up to 2-5 TriggerTerms
            pat1Over30.setSex("M");
            phDtoPat1.setNotes(" 2 trigger terms: Poids , Fumeur ");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 2 - TriggerTerms
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
            // - 3 - TriggerTerm
                phDtoPat1.setNotes(" 3 trigger terms: Poids , Fumeur Taille ");
                phDTOList = Arrays.asList(phDtoPat1);
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
            // - 5 - TriggerTerm
                phDtoPat1.setNotes(" 5 trigger terms: Poids , Fumeur Taille Hémoglobine A1C");
                phDTOList = Arrays.asList(phDtoPat1);
                assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Borderline");
    }

    @Test
    @Tag("getDiabeteAssessmentByIdPatient")
    @DisplayName(" In Danger Risk level Test, with 4 Patients, 2 mem and 2 women with each one aged over 30 and the other under")
    public void AssessmentByIdWithPatientDiabeteRiskInDangerTest(){
        // Women < 30 years  - up to 4-6 TriggerTerms
            pat2Under30.setSex("F");
            phDtoPat2.setNotes(" 4 trigger terms: Taille, Poids, Fumeur  Anormal");
            phDTOList = Arrays.asList(phDtoPat2);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
            // - 4 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
            // - 5 - TriggerTerms
            phDtoPat2.setNotes(" 5 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige ");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
            // - 6 - TriggerTerms
            phDtoPat2.setNotes(" 6 trigger terms: Anormal, Cholestérol Poids , Fumeur, Anticorp Taille");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
        // Women > 30 years  - up to 6-7 TriggerTerm
            pat1Over30.setSex("F");
            phDtoPat1.setNotes(" 6 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige, Anticorp");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 6 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
            // - 7 - TriggerTerms
            phDtoPat1.setNotes(" 7 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige, Anticorp Taille");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
        // Men < 30 years    - up to 3-4 TriggerTerm
            pat2Under30.setSex("M");
            phDtoPat2.setNotes(" 3 trigger terms: Réaction , Anticorp, Cholestérol");
            phDTOList = Arrays.asList(phDtoPat2);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
            // - 3 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
            // - 4 - TriggerTerms
            phDtoPat2.setNotes(" 3 trigger terms: Réaction , Anticorp, Cholestérol, Vertige");
            phDTOList = Arrays.asList(phDtoPat2);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
        // Men > 30 years    - up to 6-7 TriggerTerm
            pat1Over30.setSex("M");
            phDtoPat1.setNotes(" 6 trigger terms:  Cholestérol Poids , Fumeur, Vertige, Anticorp Taille");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 6 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");
            // - 7 - TriggerTerms
            phDtoPat1.setNotes(" 7 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige, Anticorp Taille");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("In Danger");

    }
    @Test
    @Tag("getDiabeteAssessmentByIdPatient")
    @DisplayName(" Early onset risk level Test, with 4 Patients, 2 mem and 2 women with each one aged over 30 and the other under")
    public void AssessmentByIdWithPatientDiabeteRiskNEarlysOnsetTest(){
        // Women < 30 years  - up to 7+ TriggerTerms
            pat2Under30.setSex("F");
            phDtoPat2.setNotes(" 7 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige, Anticorp Taille");
            phDTOList = Arrays.asList(phDtoPat2);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
            // - 7 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
            // - 11 - TriggerTerms
            phDtoPat2.setNotes(" 11 trigger terms: Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur," +
                                                 "Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp");
            phDTOList = Arrays.asList(phDtoPat2);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
        // Women > 30 years  - up to 8+ TriggerTerm
            pat1Over30.setSex("F");
            phDtoPat1.setNotes(" 8 trigger terms: Anormal, Cholestérol Poids , Fumeur, Vertige, Anticorp, " +
                                                    "Hémoglobine A1C, Microalbumine");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 8 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
            // - 11 - TriggerTerms
            phDtoPat1.setNotes(" 11 trigger terms: Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur," +
                                                    "Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
        // Men < 30 years    - up to 5+ TriggerTerm
            pat2Under30.setSex("M");
            phDtoPat2.setNotes(" 5 trigger terms: Réaction , Anticorp, Cholestérol, Hémoglobine A1C, Microalbumine,");
            phDTOList = Arrays.asList(phDtoPat2);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat2Under30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(19);
            // - 5 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
            // - 11 - TriggerTerms
            phDtoPat2.setNotes(" 11 trigger terms: Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur," +
                        "Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp");
            phDTOList = Arrays.asList(phDtoPat2);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
        // Men > 30 years    - up to 8+ TriggerTerm
            pat1Over30.setSex("M");
            phDtoPat1.setNotes(" 8 trigger terms: Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur," +
                                                            "Anormal, Cholestérol, Vertige");
            phDTOList = Arrays.asList(phDtoPat1);
            Mockito.when(patientMsProxy.getPatientById(any(Integer.class))).thenReturn(pat1Over30);
            Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);
            Mockito.when(utilityMethods.ageCalculator(any(LocalDate.class))).thenReturn(40);
            // - 8 - TriggerTerms
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");
            // - 11 - TriggerTerms
            phDtoPat1.setNotes(" 11 trigger terms: Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur," +
                                            "Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp");
            phDTOList = Arrays.asList(phDtoPat1);
            assertThat(assessService.getDiabeteAssessmentByIdPatient(2).getDiabetesRiskAssessment()).isEqualTo("Early onset");

    }


    /**
     * ---- Test ------- getDiabeteAssessmentByFamilyName ---------------------------------------------------------------------
     */
    @Test
    @Tag("getDiabeteAssessmentByFamilyName")
    public void getDiabeteAssessmentByFamilyNameWithPatientDiabeteRiskNoneTest(){
        patientDTOList = Arrays.asList(pat1Over30, pat2Under30);
        phDtoPat1.setNotes("1 terme: Vertige");
        phDTOList = Arrays.asList(phDtoPat1);

        Mockito.when(patientMsProxy.getPatientsStartingWith(any(String.class))).thenReturn(patientDTOList);

        Mockito.when(historyMsProxy.getPatientHistoryBypatientId(any(Integer.class))).thenReturn(phDTOList);

        Mockito.when(utilityMethods.ageCalculator(LocalDate.parse(pat1Over30.getDateOfBirth()))).thenReturn(40);
        Mockito.when(utilityMethods.ageCalculator(LocalDate.parse(pat2Under30.getDateOfBirth()))).thenReturn(19);

        List<AssessmentDTO> asListResult = assessService.getDiabeteAssessmentByFamilyName("LastName1");
        assertThat(assessService.getDiabeteAssessmentByFamilyName("LastName1")).size().isEqualTo(2);
        for(AssessmentDTO as: asListResult){
           assertThat(as.getDiabetesRiskAssessment()).isEqualTo("None");
        }
    }


    @Test
    @DisplayName("test of stringIsContainedAnotherString")
    public void stringIsContainedAnotherStringTest() {
        String textWithAllTermsResearch = "Texte qui comprend tous les termes Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur, " +
                " Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp";

        String textWithZeroTermResearch = "texte ne contenant aucun mot recherché. avec hémoglobine sans majuscule";

        List<String> worldResearchList = Arrays.asList("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
                "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorp");

        for (String s : worldResearchList){
            assertThat(assessService.stringIsContainedAnotherString(s, textWithAllTermsResearch).equals(true));
            assertThat(assessService.stringIsContainedAnotherString(s, textWithZeroTermResearch).equals(false));
        }

        assertThat(assessService.stringIsContainedAnotherString(null, null).equals(false));
        assertThat(assessService.stringIsContainedAnotherString(null, textWithAllTermsResearch));
        assertThat(assessService.stringIsContainedAnotherString("Réaction", null).equals(false));
    }

}
