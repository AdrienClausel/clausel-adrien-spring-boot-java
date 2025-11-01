package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    public void add(Person person) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getPersons().add(person);
        jsonFileRepository.writeData(dataStore);
    }

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
