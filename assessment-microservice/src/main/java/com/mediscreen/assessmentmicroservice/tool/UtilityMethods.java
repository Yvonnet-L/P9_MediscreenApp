package com.mediscreen.assessmentmicroservice.tool;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UtilityMethods {


    /**
     * Method which calculates the age according to a sent date of birth and the current date
     *
     * @param birthDate
     * @return age Integer
     */
    public Integer ageCalculator(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        if ( birthDate != null ){
            Integer age = Period.between(birthDate, now).getYears();
            return age;
        }
        return null;
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
