package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JsonFirestationRepository implements IFirestationRepository{

    @Autowired
    private JsonFileRepository jsonFileRepository;

    @Override
    public void add(Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getFirestations().add(firestation);
        jsonFileRepository.writeData(dataStore);
    }

    @Override
    public boolean updateStationByAddress(String address, Firestation firestation) {
        DataStore dataStore = jsonFileRepository.readData();
        Optional<Firestation> firestationExisting = dataStore.getFirestations().stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .findFirst();
        if (firestationExisting.isPresent()) {
            firestationExisting.get().setStation(firestation.getStation());
            jsonFileRepository.writeData(dataStore);
            return true;
        }

        return false;
//        } else {
//            log.error("Adresse {} non trouvée",address);
//            throw new RuntimeException("Adresse non trouvée");
//        }
//        jsonFileRepository.writeData(dataStore);
    }

    @Override
    public boolean remove(String address, String station) {
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getFirestations()
                .removeIf(f ->
                        f.getAddress().equalsIgnoreCase(address) &&
                                f.getStation().equalsIgnoreCase(station)
                );
        if (removed) {
            jsonFileRepository.writeData(dataStore);
        }
        return removed;
    }
}
