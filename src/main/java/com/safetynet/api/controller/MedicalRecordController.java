package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les dossiers médicaux
 */
@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Ajout d'un dossier médical
     * @param medicalRecord dossier medical
     */
    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        log.debug("Requête POST /medicalRecord : {}", medicalRecord);
        medicalRecordService.add(medicalRecord);
        log.info("Ajout d'un dossier médical pour ({}/{})", medicalRecord.getLastName(), medicalRecord.getFirstName());
    }

    /**
     * Met à jour un dossier médical
     * @param lastName nom
     * @param firstName prénom
     * @param medicalRecord dossier medical
     */
    @PutMapping("/{lastName}/{firstName}")
    public void updateMedicalRecordByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName, @RequestBody MedicalRecord medicalRecord){
        log.debug("Requête PUT /medicalRecord/{}/{} : {}", lastName, firstName, medicalRecord);
        medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,medicalRecord);
        log.info("Modification du dossier médical de ({}/{})", lastName, firstName);
    }

    /**
     * Supprime un dossier medical
     * @param lastName nom
     * @param firstName prénom
     */
    @DeleteMapping("/{lastName}/{firstName}")
    public void deleteMedicalRecordByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName){
        log.debug("Requête DELETE /person/{}/{}", lastName, firstName);
        medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName);
        log.info("Suppression du dossier médical de ({}/{})", lastName, firstName);
    }
}