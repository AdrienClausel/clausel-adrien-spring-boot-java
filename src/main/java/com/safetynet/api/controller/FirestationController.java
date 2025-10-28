package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/firestation")
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    /**
     * Ajout d'une nouvelle association caserne/adresse
     * @param firestation une association caserne/adresse
     */
    @PostMapping
    public void createFirestation(@RequestBody Firestation firestation){
        log.debug("Requête POST /firestation : {}", firestation);
        firestationService.add(firestation);
        log.info("Ajout avec succès d'une correspondance d'adresse({}) avec une caserne({})",firestation.getAddress(), firestation.getStation());
    }

    /**
     * Met à jour le numéro de caserne associé à une adresse
     * @param address une adresse
     * @param station une caserne
     */
    @PutMapping("/{address}")
    public void  updateStationByAddress(@PathVariable final String address, @RequestParam final String station){
        log.debug("Requête PUT /firestation : adresse = {}, station = {}", address, station);
        firestationService.updateStationByAddress(address, station);
        log.info("Modification du numéro de caserne ({}) pour l'adresse {} ", station, address);
    }

    /**
     * Supprime une association caserne/adresse
     * @param address une adresse
     * @param station une caserne
     */
    @DeleteMapping
    public void deleteFirestation(@RequestParam("address") final String address, @RequestParam("station") final String station){
        log.debug("Requête DELETE /firestation : adresse = {}, station = {}", address, station);
        firestationService.remove(address, station);
        log.info("Suppression de l'association caserne({})/adresse({}) ", station, address);
    }
}