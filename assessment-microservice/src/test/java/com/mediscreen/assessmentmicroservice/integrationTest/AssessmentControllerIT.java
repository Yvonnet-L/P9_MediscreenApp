package com.mediscreen.assessmentmicroservice.integrationTest;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssessmentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port;
    private String baseUrl;


    @BeforeEach
    public void setUpWebDriver() {
        baseUrl = "http://localhost:" + port + "/assess/";
    }

    @Test
    public void getAssessmentForPatientByIdPatientRequest_thenReturnOkStatus() {
        ResponseEntity<AssessmentDTO> response = restTemplate.getForEntity(baseUrl + "id/1", AssessmentDTO.class);
        assertEquals("",HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void getAssessmentForPatientWithFamilyNameRequest_thenReturnOkStatus() {
        List<AssessmentDTO> assessmentDTOS = new ArrayList<>();
        ResponseEntity<Object> response = restTemplate.getForEntity(baseUrl +"familyName/Test",Object.class);
        assertEquals("",HttpStatus.OK, response.getStatusCode());

    }
}
