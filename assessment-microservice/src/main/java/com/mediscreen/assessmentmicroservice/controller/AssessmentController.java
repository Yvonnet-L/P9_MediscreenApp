package com.mediscreen.assessmentmicroservice.controller;

import com.mediscreen.assessmentmicroservice.dto.AssessmentDTO;
import com.mediscreen.assessmentmicroservice.proxies.HistoryMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.proxies.PatientMicroserviceProxy;
import com.mediscreen.assessmentmicroservice.service.IAssessmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "patient management ")
@RestController
public class AssessmentController {

    @Autowired
    HistoryMicroserviceProxy historyMicroserviceProxy;

    @Autowired
    PatientMicroserviceProxy patientMicroserviceProxy;

    @Autowired
    IAssessmentService assessmentService;

   private static Logger logger = LogManager.getLogger(AssessmentController.class);

    @ApiOperation(value="Return home page")
    @GetMapping("/")
    public String getHome(){
        /**
        String notes = " une note d'une medicin sur les symptomes d'un covidd covid19 impatient sur patient";
        List<String> strings =  Arrays.asList("patient", "le", "sur", "covid", "covid19", "un", "une");
        Integer resultat = 0;
        /**
        final String SEPARATEUR = " ";
        String mots[] = notes.split(SEPARATEUR);

        for (int i = 0; i < mots.length; i++) {
            System.out.println(mots[i]);
            for (String s: strings ) {
               boolean d = mots[i].contentEquals(s);
                if (notes.indexOf ("" + s + "")>= 0) {

                }
                System.out.println("/// --" + notes.indexOf (" " + s + " "));

                System.out.println("le mot "+ s + " -- est --" + d);
                if(d){ resultat++; }
            }
        }*/
/**
        for (String s: strings ){
            boolean b = notes.contains(s);
            int d = notes.indexOf(" "+ s +" ");
            int f = notes.indexOf(s);
            System.out.println(" *** mot ** "+ s +"** val **** d " + d );
            System.out.println(" *** val **** f " + d );
            if(b){ resultat++; }

        }
        System.out.println(" *** resultat **** " + resultat);
        Integer nbTerme=0;
        boolean ans = Boolean.FALSE;
        ArrayList<String> wordList = null;
        wordList = new ArrayList<String>(Arrays.asList(notes.split("[^a-zA-Z0-9]+")));
        for (String s: wordList) {
            System.out.println(" ***  " + s);

        }
        for (String s: strings ) {
            if (wordList.contains(s)) {
                ans = Boolean.TRUE;
                nbTerme++;
            }
            System.out.println(" *** resultat ans **** " + ans);
            ans = Boolean.FALSE;
        }
        System.out.println(" *** Nombre **** " + nbTerme);
            */
        //assessmentService.rechercheString();

        return  "assessmentService.rechercheString()";

        //return "Welcone on Assessment-Microservice";
    }
    ///-----------------------------------------------------------------------------------------------------
    @ApiOperation(value="Result Assessment for a patient by his id")
    @GetMapping("/assess/id/{id}")
    public AssessmentDTO getAssessmentForPatientByIdPatient(@PathVariable(name="id") Integer patientId){
        return assessmentService.diabeteAssessmentByIdPatient(patientId);
    }

    @ApiOperation(value="Result Assessment for a patient by FamilyName")
    @GetMapping("/assess/familyName/{familyName}")
    public List<AssessmentDTO> getAssessmentForPatientWithFamilyName(@PathVariable(name="familyName") String familyName){
        return assessmentService.diabeteAssessmentByFamilyName(familyName);
    }

}
