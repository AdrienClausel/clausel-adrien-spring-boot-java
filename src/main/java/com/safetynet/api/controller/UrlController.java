package com.safetynet.api.controller;

import com.safetynet.api.dto.ChildAlertDTO;
import com.safetynet.api.dto.FireDTO;
import com.safetynet.api.dto.FirestationPersonsDTO;
import com.safetynet.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
        List<ChildAlertDTO> childAlert = urlService.getChildAlertByAddress(address);

        ResponseEntity<?> result;

        if (childAlert.isEmpty()) {
            result = ResponseEntity.ok("");
        } else {
            result = ResponseEntity.ok(childAlert);
        }

        log.info("Récupération d'une liste d'enfants pour l'adresse({})",address);
        return result;
    }

    @GetMapping("phoneAlert")
    public Set<String> getPhoneAlertByStation(@RequestParam final int station){
        log.debug("Requête GET /phoneAlert");
        var result = urlService.getPhoneAlertByStation(station);
        log.info("Récupération d'une liste de numéros de téléphone pour la caserne({})",station);
        return result;
    }

    @GetMapping("/fire")
    public FireDTO getPersonsAndStationByAddress(@RequestParam final String address){
        log.debug("Requête GET /fire");
        var result = urlService.getPersonsAndStationByAddress(address);
        log.info("Récupération d'une liste de personnes et la caserne associé pour l'adresse({})",address);
        return result;
    }
}
