package com.safetynet.api.controller;

import com.safetynet.api.dto.ChildAlertDTO;
import com.safetynet.api.dto.FirestationPersonsDTO;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UrlController {

    @Autowired
    UrlService urlService;

    @GetMapping("/firestation")
    public FirestationPersonsDTO getPersonsByStation(@RequestParam final int station){
        log.debug("Requête GET /firestation");
        var result = urlService.getPersonsByStation(station);
        log.info("Récupération d'une liste de personnes couvertes par la caserne({})",station);
        return result;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildAlertByAddress(@RequestParam final String address){
        log.debug("Requête GET /childAlert");
        ChildAlertDTO childAlert = urlService.getChildAlertByAddress(address);

        ResponseEntity<?> result;

        if (childAlert.children().isEmpty()) {
            result = ResponseEntity.ok("");
        } else {
            result = ResponseEntity.ok(childAlert);
        }

        log.info("Récupération d'une liste d'enfants pour l'adresse({})",address);
        return result;
    }
}
