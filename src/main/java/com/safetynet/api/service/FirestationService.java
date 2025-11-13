package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.DataStore;
import com.safetynet.api.repository.IFirestationRepository;
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
    private IFirestationRepository firestationRepository;

    /**
     * Insère une nouvelle association caserne/adresse
     * @param firestation association caserne/adresse
     */
    public void add(Firestation firestation) {
        firestationRepository.add(firestation);
    }

    /**
     * Met à jour une association caserne / adresse
     * Déclenche une exception si l'adresse n'est pas trouvée
     * @param address adresse
     * @param firestation objet caserne/adresse
     */
    public void updateStationByAddress(String address, Firestation firestation) {
        boolean updated = firestationRepository.updateStationByAddress(address,firestation);

        if (!updated) {
            log.error("Adresse {} non trouvée",address);
            throw new RuntimeException("Adresse non trouvée");
        }
    }

    /**
     * Supprime une association caserne/adresse
     * Déclenche une exception si l'adresse n'est pas trouvée
     * @param address adresse
     * @param station caserne
     */
    public void remove(String address, String station) {
        boolean removed = firestationRepository.remove(address,station);

        if (!removed){
            log.error("Adresse {} non trouvée",address);
            throw new RuntimeException("Adresse non trouvée");
        }
    }
}
