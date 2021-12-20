package com.mediscreen.assessmentmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Data Transfer Object of the assessment of a patient's risk of diabetes
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {
    /**
     * @See PatientDTO
     */
    private PatientDTO patientDTO;
    /**
     * age of patient
     */
    private Integer age;
    /**
     * String result of the evaluation which can have 4 levels:
     */
    private String diabetesRiskAssessment;

    //resString = "Patient: " + patientDTO.getGivenName() + " " + patientDTO.getFamilyName() + " (age " + age + ")" + " diabetes assessment is: " + diabetesRiskAssessment;
    @Override
    public String toString() {
        return "AssessmentDTO{" +
                "patient:" + patientDTO.getGivenName() +  " " + patientDTO.getFamilyName() +
                " (age " + age + ")  diabetes assessment is: "+ diabetesRiskAssessment + '\'' +
                '}';
    }

}
