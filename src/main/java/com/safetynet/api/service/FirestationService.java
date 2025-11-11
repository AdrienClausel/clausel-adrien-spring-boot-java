package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service de gestion des casernes de pompiers
 */
@Slf4j
@Service
public class FirestationService {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    /**
     * Insère une nouvelle association caserne/adresse
     * @param firestation association caserne/adresse
     */
    public void add(Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getFirestations().add(firestation);
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Met à jour une association caserne / adresse
     * Déclenche une exception si l'adresse n'est pas trouvée
     * @param address adresse
     * @param firestation objet caserne/adresse
     */
    public void updateStationByAddress(String address, Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        Optional<Firestation> firestationExisting = dataStore.getFirestations().stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst();
        if (firestationExisting.isPresent()) {
            firestationExisting.get().setStation(firestation.getStation());
        } else {
            log.error("Adresse {} non trouvée",address);
            throw new RuntimeException("Adresse non trouvée");
        }
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Supprime une association caserne/adresse
     * Déclenche une exception si l'adresse n'est pas trouvée
     * @param address adresse
     * @param station caserne
     */
    public void remove(String address, String station) {
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getFirestations()
            .removeIf(f ->
                    f.getAddress().equalsIgnoreCase(address) &&
                    f.getStation().equalsIgnoreCase(station)
            );
        if (!removed) {
            log.error("Adresse {} non trouvée",address);
            throw new RuntimeException("Adresse non trouvée");
        }
        jsonFileRepository.writeData(dataStore);
    }
}
