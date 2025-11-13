package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonPersonRepositoryTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private JsonPersonRepository jsonPersonRepository;

    @Test
    public void testAdd_shouldAddAndWriteData(){
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

        jsonPersonRepository.add(person);

        assertEquals("prost", dataStore.getPersons().getFirst().getLastName());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
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

        jsonPersonRepository.updateByLastNameAndFirstName(lastName,firstName,updatePerson);

        assertEquals(updatePerson.getAddress(),dataStore.getPersons().getFirst().getAddress());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testDeleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldDeleteAndWriteData(){
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

        jsonPersonRepository.deleteByLastNameAndFirstName(lastName,firstName);

        assertEquals(0, dataStore.getPersons().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

}
