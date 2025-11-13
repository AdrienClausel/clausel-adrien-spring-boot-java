package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.DataStore;
import com.safetynet.api.repository.IMedicalRecordRepository;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service de gestion des dossiers médicaux
 */
@Slf4j
@Service
public class MedicalRecordService {

    @Autowired
    private IMedicalRecordRepository medicalRecordRepository;

    /**
     * Insère un nouveau dossier médical
     * @param medicalRecord dossier médical
     */
    public void add(MedicalRecord medicalRecord){
        medicalRecordRepository.add(medicalRecord);
    }

    /**
     * Met à jour un dossier médical
     * Déclenche une exception si le dossier médical n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     * @param medicalRecord dossier médical
     */
    public void updateByLastNameAndFirstName(String lastName, String firstName, MedicalRecord medicalRecord){
        boolean updated = medicalRecordRepository.updateByLastNameAndFirstName(lastName,firstName,medicalRecord);

        if (!updated) {
            log.error("Dossier médical {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Dossier médical non trouvé");
        }
    }

    /**
     * Supprime un dossier médical
     * Déclenche une exception si le dossier médical n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     */
    public void deleteByLastNameAndFirstName(String lastName, String firstName){
        boolean deleted = medicalRecordRepository.deleteByLastNameAndFirstName(lastName,firstName);

        if (!deleted){
            log.error("Dossier médical {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Dossier médical non trouvé");
        }
    }
}
