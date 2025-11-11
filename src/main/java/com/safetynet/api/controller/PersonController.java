package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlleur REST pour gérer les personnes
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Ajoute une personne
     * @param person personne
     */
    @PostMapping
    public void createPerson(@RequestBody Person person){
        log.debug("Requête POST /person : {}", person);
        personService.add(person);
        log.info("Ajout d'une personne ({}/{})",person.getLastName(),person.getFirstName());
    }

    /**
     * Met à jour une personne
     * @param lastName nom
     * @param firstName prénom
     * @param person personne
     */
    @PutMapping("/{lastName}/{firstName}")
    public void updatePersonByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName, @RequestBody Person person){
        log.debug("Requête PUT /person/{}/{} : {}", lastName, firstName,person);
        personService.updateByLastNameAndFirstName(lastName, firstName, person);
        log.info("Modification de la personne ({}/{})", lastName, firstName);
    }

    /**
     * Supprime une personne
     * @param lastName nom
     * @param firstName prénom
     */
    @DeleteMapping("/{lastName}/{firstName}")
    public void deletePersonByLastNameAndFirstName(@PathVariable String lastName, @PathVariable String firstName){
        log.debug("Requête DELETE /person/{}/{}", lastName, firstName);
        personService.deleteByLastNameAndFirstName(lastName, firstName);
        log.info("Suppression de la personne ({}/{})", lastName, firstName);
    }
}