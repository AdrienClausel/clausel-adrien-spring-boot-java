package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class FirestationService {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    public void add(Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getFirestations().add(firestation);
        jsonFileRepository.writeData(dataStore);
    }

    public void updateStationByAddress(String address, Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        Optional<Firestation> firestationExisting = dataStore.getFirestations().stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst();
        if (firestationExisting.isPresent()) {
            firestationExisting.get().setStation(firestation.getStation());
        } else {
            log.error("Adresse {} non trouvé",address);
            throw new RuntimeException("Adresse non trouvé");
        }
        jsonFileRepository.writeData(dataStore);
    }

    public void remove(String address, String station) {
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getFirestations()
            .removeIf(f ->
                    f.getAddress().equalsIgnoreCase(address) &&
                    f.getStation().equalsIgnoreCase(station)
            );
        if (!removed) {
            throw new RuntimeException("Adresse non trouvée");
        }
        jsonFileRepository.writeData(dataStore);
    }
}
