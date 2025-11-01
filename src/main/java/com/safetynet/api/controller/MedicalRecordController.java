package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        log.debug("Requête POST /medicalRecord : {}", medicalRecord);
        medicalRecordService.add(medicalRecord);
        log.info("Ajout d'un dossier médical pour ({}/{})", medicalRecord.getLastName(), medicalRecord.getFirstName());
    }

    @PutMapping("/{lastName}/{firstName}")
    public void updateMedicalRecordByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName, @RequestBody MedicalRecord medicalRecord){
        log.debug("Requête PUT /medicalRecord/{}/{} : {}", lastName, firstName, medicalRecord);
        medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,medicalRecord);
        log.info("Modification du dossier médical de ({}/{})", lastName, firstName);
    }

    @DeleteMapping("/{lastName}/{firstName}")
    public void deleteMedicalRecordByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName){
        log.debug("Requête DELETE /person/{}/{}", lastName, firstName);
        medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName);
        log.info("Suppression du dossier médical de ({}/{})", lastName, firstName);

    }
}
