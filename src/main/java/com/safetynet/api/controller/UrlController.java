package com.safetynet.api.controller;

import com.safetynet.api.dto.*;
import com.safetynet.api.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;

/**
 * Contrôleur REST pour gérer les extractions de données
 */
@Slf4j
@RestController
public class UrlController {

    @Autowired
    UrlService urlService;

    /**
     * Renvoie une liste de personnes couvertes par une caserne
     * @param station caserne
     * @return FirestationPersonsDTO liste des personnes
     */
    @GetMapping("/firestation")
    public FirestationPersonsDTO getPersonsByStation(@RequestParam final int station){
        log.debug("Requête GET /firestation");
        var result = urlService.getPersonsByStation(station);
        log.info("Récupération d'une liste de personnes couvertes par la caserne({})",station);
        return result;
    }

    /**
     * Renvoie une liste d'enfants habitant à une adresse
     * @param address adresse
     * @return FirestationPersonsDTO || "" Une liste d'enfants ou une chaine vide si aucun resultat
     */
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

    /**
     * Renvoie une liste de numéros de téléphone des résidents d'une caserne
     * @param station caserne
     * @return Set<String> Une liste de numéros de téléphone
     */
    @GetMapping("phoneAlert")
    public Set<String> getPhoneAlertByStation(@RequestParam final int station){
        log.debug("Requête GET /phoneAlert");
        var result = urlService.getPhoneAlertByStation(station);
        log.info("Récupération d'une liste de numéros de téléphone pour la caserne({})",station);
        return result;
    }

    /**
     * Retourne une liste des habitants vivant à une adresse ainsi que la caserne la desservant
     * @param address adresse
     * @return FireDTO Une liste des habitants avec la caserne associée
     */
    @GetMapping("/fire")
    public FireDTO getPersonsAndStationByAddress(@RequestParam final String address){
        log.debug("Requête GET /fire");
        var result = urlService.getPersonsAndStationByAddress(address);
        log.info("Récupération d'une liste de personnes et la caserne associé pour l'adresse({})",address);
        return result;
    }

    /**
     * Renvoie une liste de tous les foyers desservis par des casernes. Les personnes sont regroupées par adresse
     * @param stations casernes
     * @return List<FloodDTO> Une liste de foyers
     */
    @GetMapping("/flood")
    public List<FloodDTO> getFloodByStations(@RequestParam final List<String> stations){
        log.debug("Requête GET /flood");
        var result = urlService.getFloodByStations(stations);
        log.info("Récupération d'une liste de foyers pour une liste de caserne ({})",stations);
        return result;
    }

    /**
     * Renvoie une liste de personnes portant le nom correspondant
     * @param lastName nom
     * @return List<PersonInfoLastNameDTO> Une liste de personnes
     */
    @GetMapping("/personInfolastName")
    public List<PersonInfoLastNameDTO> getPersonInfoLastName(@RequestParam final String lastName){
        log.debug("Requête GET /personInfolastName");
        var result = urlService.getPersonInfoLastName(lastName);
        log.info("Récupération d'une liste de personnes portant le nom ({})",lastName);
        return result;
    }

    /**
     * Renvoie une liste des emails de tous les habitants d'une ville
     * @param city ville
     * @return List<String> Une liste d'emails
     */
    @GetMapping("/communityEmail")
    public List<String> getPersonsEmailByCity(@RequestParam final String city){
        log.debug("Requête GET /communityEmail");
        var result = urlService.getPersonsEmailByCity(city);
        log.info("Récupération d'une liste de mails des habitants de la ville({})",city);
        return result;
    }
}