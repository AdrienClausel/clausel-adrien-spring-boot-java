package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JsonPersonRepository implements IPersonRepository {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    @Override
    public void add(Person person) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getPersons().add(person);
        jsonFileRepository.writeData(dataStore);
    }

    @Override
    public boolean updateByLastNameAndFirstName(String lastName, String firstName, Person person) {
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
            jsonFileRepository.writeData(dataStore);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByLastNameAndFirstName(String lastName, String firstName) {
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getPersons().removeIf(p ->
                p.lastName.equalsIgnoreCase(lastName)
                        && p.firstName.equalsIgnoreCase(firstName)
        );
        if (removed){
            jsonFileRepository.writeData(dataStore);
        }
        return removed;
    }
}
