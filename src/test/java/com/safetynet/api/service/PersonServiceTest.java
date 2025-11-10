package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    PersonService personService;

    @Test
    public void add_shouldAddAndWriteData(){
        Person person = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("12 chemin de la mer");
        person.setZip("84000");
        person.setCity("Avignon");

        DataStore dataStore = new DataStore();

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        personService.add(person);

        assertEquals("prost", dataStore.getPersons().getFirst().getLastName());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
        String lastName = "prost";
        String firstName = "alain";

        Person person = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("12 chemin de la mer");
        person.setZip("84000");
        person.setCity("Avignon");

        Person updatePerson = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("3 rue de la poste");
        person.setZip("13000");
        person.setCity("Marseille");

        DataStore dataStore = new DataStore();
        dataStore.getPersons().add(person);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        personService.updateByLastNameAndFirstName(lastName,firstName,updatePerson);

        assertEquals(updatePerson.getAddress(),dataStore.getPersons().getFirst().getAddress());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException(){
        String lastName = "veil";
        String firstName = "simone";

        Person person = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("12 chemin de la mer");
        person.setZip("84000");
        person.setCity("Avignon");

        Person updatePerson = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("3 rue de la poste");
        person.setZip("13000");
        person.setCity("Marseille");

        DataStore dataStore = new DataStore();
        dataStore.getPersons().add(person);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                personService.updateByLastNameAndFirstName(lastName,firstName,updatePerson)
        );

        assertEquals("Personne non trouvée", exception.getMessage());
        assertNotEquals(updatePerson.getAddress(),dataStore.getPersons().getFirst().getAddress());
        verify(jsonFileRepository,never()).writeData(dataStore);
    }

    @Test
    public void deleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldDeleteAndWriteData() {
        String lastName = "prost";
        String firstName = "alain";

        Person person = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("12 chemin de la mer");
        person.setZip("84000");
        person.setCity("Avignon");

        DataStore dataStore = new DataStore();
        dataStore.getPersons().add(person);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        personService.deleteByLastNameAndFirstName(lastName,firstName);

        assertEquals(0, dataStore.getPersons().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void deleteByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException() {
        String lastName = "veil";
        String firstName = "simone";

        Person person = new Person();
        person.setLastName("prost");
        person.setFirstName("alain");
        person.setEmail("alain.prost@email.fr");
        person.setPhone("0490452365");
        person.setAddress("12 chemin de la mer");
        person.setZip("84000");
        person.setCity("Avignon");

        DataStore dataStore = new DataStore();
        dataStore.getPersons().add(person);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                personService.deleteByLastNameAndFirstName(lastName,firstName)
        );

        assertEquals("Personne non trouvée", exception.getMessage());
        assertEquals(1, dataStore.getPersons().size());
        verify(jsonFileRepository,never()).writeData(dataStore);
    }

}
