package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonRepository;
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
    private IPersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    @Test
    public void testAdd_shouldCallRepository(){
        Person newPerson = new Person("prost","alain","12 chemin de la mer","84000","Avignon","0490452365","alain.prost@email.fr");

        personService.add(newPerson);

        verify(personRepository,times(1)).add(newPerson);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_existingLastNameAndFirstName_shouldSuccess(){
        String lastName = "prost";
        String firstName = "alain";

        Person updatePerson = new Person("prost","alain","12 chemin de la mer","84000","Avignon","0490452365","alain.prost@email.fr");

        when(personRepository.updateByLastNameAndFirstName(lastName,firstName,updatePerson)).thenReturn(true);

        personService.updateByLastNameAndFirstName(lastName,firstName,updatePerson);

        verify(personRepository,times(1)).updateByLastNameAndFirstName(lastName,firstName,updatePerson);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException(){
        String lastName = "prost";
        String firstName = "alain";

        Person updatePerson = new Person("prost","alain","12 chemin de la mer","84000","Avignon","0490452365","alain.prost@email.fr");

        when(personRepository.updateByLastNameAndFirstName(lastName,firstName,updatePerson)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            personService.updateByLastNameAndFirstName(lastName,firstName,updatePerson)
        );

        assertEquals("Personne non trouvée", exception.getMessage());
        verify(personRepository,times(1)).updateByLastNameAndFirstName(lastName,firstName,updatePerson);
    }

    @Test
    public void testDeleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldSuccess() {
        String lastName = "prost";
        String firstName = "alain";

        when(personRepository.deleteByLastNameAndFirstName(lastName,firstName)).thenReturn(true);

        personService.deleteByLastNameAndFirstName(lastName,firstName);

        verify(personRepository,times(1)).deleteByLastNameAndFirstName(lastName,firstName);
    }

    @Test
    public void testDeleteByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException() {
        String lastName = "prost";
        String firstName = "alain";

        when(personRepository.deleteByLastNameAndFirstName(lastName,firstName)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            personService.deleteByLastNameAndFirstName(lastName,firstName)
        );

        assertEquals("Personne non trouvée", exception.getMessage());
        verify(personRepository,times(1)).deleteByLastNameAndFirstName(lastName,firstName);
    }

}
