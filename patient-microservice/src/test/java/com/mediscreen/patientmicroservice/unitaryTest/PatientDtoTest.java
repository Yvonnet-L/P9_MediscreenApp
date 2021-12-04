package com.mediscreen.patientmicroservice.unitaryTest;

import com.mediscreen.patientmicroservice.dto.PatientDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PatientDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //---------- All Good---------------------------------------------------------------------------------
    @Test
    @DisplayName("PatientDTO constraints test with params valid")
    public void patientDtoAllParamsGoodTest() {
        // String familyName, String givenName, LocalDate dateOfBirth, String sex, String address, String phone)
        LocalDate date = LocalDate.ofEpochDay(1966-12-31);
        PatientDTO patientDTO = new PatientDTO( "TestNone", "Test", date,
                                                "F", "1 Brookside St", "100-222-3333");

        Set<ConstraintViolation<PatientDTO>> constraintViolations = validator.validate(patientDTO);

        assertEquals(0, constraintViolations.size());
    }

    //---------- Params Nulls---------------------------------------------------------------------------------
    @Test
    @DisplayName("PatientDTO constraints test with params valid")
    public void patientDtoPasramsNullTest() {
        PatientDTO patientDTO = new PatientDTO( null, null, null,
                "F", "1 Brookside St", "100-222-3333");

        Set<ConstraintViolation<PatientDTO>> constraintViolations = validator.validate(patientDTO);

        assertEquals(3, constraintViolations.size());
    }

    //---------- Params Empty---------------------------------------------------------------------------------
    @Test
    @DisplayName("PatientDTO constraints test with params valid")
    public void patientDtoPasramsEmptylTest() {
        PatientDTO patientDTO = new PatientDTO( "", "", null,
                "F", "1 Brookside St", "100-222-3333");

        Set<ConstraintViolation<PatientDTO>> constraintViolations = validator.validate(patientDTO);

        assertEquals(3, constraintViolations.size());
    }
}
