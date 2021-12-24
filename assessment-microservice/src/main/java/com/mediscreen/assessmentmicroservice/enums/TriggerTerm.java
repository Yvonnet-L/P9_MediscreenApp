package com.mediscreen.assessmentmicroservice.enums;

/**
 * List of all abbreviations useful in assessing the patient's risk of diabetes.
 * list:"Hémoglobine A1C, Microalbumine, Taille, Poids, Fumeur,
 *      Anormal, Cholestérol, Vertige, Rechute, Réaction, Anticorp";
 */
public enum TriggerTerm {

    HEMOGLOBINE_A1C("Hémoglobine A1C"),
    MICROALBUMINE("Microalbumine"),
    TAILLE("Taille"),
    POIDS("Poids"),
    FUMEUR("Fumeur"),
    ANORMAL("Anormal"),
    CHOLESTEROL("Cholestérol"),
    VERTIGE("Vertige"),
    RECHUTE("Rechute"),
    REACTION("Réaction"),
    ANTICORP("Anticorp");

    private String abreviation ;

    private TriggerTerm(String abreviation) {
        this.abreviation = abreviation ;
    }

    public String getAbreviation() {
        return  this.abreviation ;
    }

}

