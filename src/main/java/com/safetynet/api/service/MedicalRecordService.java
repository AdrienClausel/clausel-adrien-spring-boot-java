package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.DataStore;
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
    private JsonFileRepository jsonFileRepository;

    /**
     * Insère un nouveau dossier médical
     * @param medicalRecord dossier médical
     */
    public void add(MedicalRecord medicalRecord){
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getMedicalrecords().add(medicalRecord);
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Met à jour un dossier médical
     * Déclenche une exception si le dossier médical n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     * @param medicalRecord dossier médical
     */
    public void updateByLastNameAndFirstName(String lastName, String firstName, MedicalRecord medicalRecord){
        DataStore dataStore = jsonFileRepository.readData();
        Optional<MedicalRecord> medicalRecordExisting = dataStore
                .getMedicalrecords()
                .stream()
                .filter(m ->
                        m.getLastName().equalsIgnoreCase(lastName)
                        && m.getFirstName().equalsIgnoreCase(firstName)
                )
                .findFirst();
        if (medicalRecordExisting.isPresent()) {
            medicalRecordExisting.get().setBirthdate(medicalRecord.getBirthdate());
            medicalRecordExisting.get().setMedications(medicalRecord.getMedications());
            medicalRecordExisting.get().setAllergies(medicalRecord.getAllergies());
        } else {
            log.error("Dossier médical {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Dossier médical non trouvé");
        }
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Supprime un dossier médical
     * Déclenche une exception si le dossier médical n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     */
    public void deleteByLastNameAndFirstName(String lastName, String firstName){
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getMedicalrecords().removeIf(m ->
                m.lastName.equalsIgnoreCase(lastName)
                && m.firstName.equalsIgnoreCase(firstName)
        );
        if (!removed){
            throw new RuntimeException("Dossier médical non trouvé");
        }
        jsonFileRepository.writeData(dataStore);
    }
}
