package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de gestions des personnes
 */
@Slf4j
@Service
public class PersonService {

    @Autowired
    private IPersonRepository personRepository;

    /**
     * Insère une nouvelle personne
     * @param person personne
     */
    public void add(Person person) {
        personRepository.add(person);
    }

    /**
     * Met à jour une personne
     * Déclenche une exception si la personne n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     * @param person personne
     */
    public void updateByLastNameAndFirstName(String lastName, String firstName ,Person person){
        boolean updated = personRepository.updateByLastNameAndFirstName(lastName,firstName,person);

        if (!updated) {
            log.error("Personne {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Personne non trouvée");
        }
    }

    /**
     * Supprime une personne
     * Déclenche une exception si la personne n'a pas été trouvée
     * @param lastName nom
     * @param firstName prénom
     */
    public void deleteByLastNameAndFirstName(String lastName, String firstName){
        boolean deleted = personRepository.deleteByLastNameAndFirstName(lastName,firstName);

        if (!deleted) {
            log.error("Personne {}/{} non trouvé",lastName, firstName);
            throw new RuntimeException("Personne non trouvée");
        }
    }
}
