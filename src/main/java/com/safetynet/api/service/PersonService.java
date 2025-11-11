package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service de gestions des personnes
 */
@Slf4j
@Service
public class PersonService {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    /**
     * Insère une nouvelle personne
     * @param person personne
     */
    public void add(Person person) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getPersons().add(person);
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Met à jour une personne
     * Déclenche une exception si la personne n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     * @param person personne
     */
    public void updateByLastNameAndFirstName(String lastName, String firstName ,Person person){
        DataStore dataStore = jsonFileRepository.readData();
        Optional<Person> personExisting = dataStore
                .getPersons()
                .stream()
                .filter(p ->
                        p.getLastName().equalsIgnoreCase(lastName)
                        && p.getFirstName().equalsIgnoreCase(firstName)
                        )
                .findFirst();
        if (personExisting.isPresent()) {
            personExisting.get().setAddress(person.getAddress());
            personExisting.get().setZip(person.getZip());
            personExisting.get().setCity(person.getCity());
            personExisting.get().setEmail(person.getEmail());
            personExisting.get().setPhone(person.getPhone());
        } else {
            log.error("Personne {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Personne non trouvée");
        }
        jsonFileRepository.writeData(dataStore);
    }

    /**
     * Supprime une personne
     * Déclenche une exception si la personne n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     */
    public void deleteByLastNameAndFirstName(String lastName, String firstName){
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getPersons().removeIf(p ->
                p.lastName.equalsIgnoreCase(lastName)
                && p.firstName.equalsIgnoreCase(firstName)
                );
        if (!removed){
            throw new RuntimeException("Personne non trouvée");
        }
        jsonFileRepository.writeData(dataStore);
    }
}
