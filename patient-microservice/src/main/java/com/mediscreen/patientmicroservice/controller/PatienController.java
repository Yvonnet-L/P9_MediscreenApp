package com.mediscreen.patientmicroservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatienController {


    @GetMapping("/index")
    public String index(Model model){

        return "index: Welcome Here !!!!";

    }
}
