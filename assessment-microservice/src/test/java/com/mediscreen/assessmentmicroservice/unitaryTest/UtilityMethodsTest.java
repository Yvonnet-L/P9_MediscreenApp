package com.mediscreen.assessmentmicroservice.unitaryTest;


import com.mediscreen.assessmentmicroservice.tool.UtilityMethods;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UtilityMethodsTest {

    @InjectMocks
    UtilityMethods utilityMethods;

    //----------------- ageCalculator --------------------------------------------------------------------------------------------------
    @Test
    @DisplayName("ageCalculator Test ")
    public void ageCalculatorTest(){
        LocalDate birthDate = LocalDate.now();
        assertEquals(utilityMethods.ageCalculator(birthDate.minusYears(21)),21);
        assertEquals(utilityMethods.ageCalculator(birthDate.minusYears(1)),1);
        assertEquals(utilityMethods.ageCalculator(birthDate.minusYears(78)),78);
        assertEquals(utilityMethods.ageCalculator(null),null);
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
            assertThat(utilityMethods.stringIsContainedAnotherString(s, textWithAllTermsResearch).equals(true));
            assertThat(utilityMethods.stringIsContainedAnotherString(s, textWithZeroTermResearch).equals(false));
        }

        assertThat(utilityMethods.stringIsContainedAnotherString(null, null).equals(false));
        assertThat(utilityMethods.stringIsContainedAnotherString(null, textWithAllTermsResearch));
        assertThat(utilityMethods.stringIsContainedAnotherString("Réaction", null).equals(false));
    }


}
