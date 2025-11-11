package com.safetynet.api.service;

import com.safetynet.api.dto.*;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    public void getPersonsByStation_existingStation_shouldReturnAdultsAndChildren(){
        Firestation firestation = new Firestation("13 chemin de la mer", "4");

        Person adult = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person child = new Person("axel", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        MedicalRecord adultRecord = new MedicalRecord("alain", "prost", "12/25/2000", List.of("aznol:350mg","hydrapermazol:100mg"), List.of("illisoxian"));
        MedicalRecord childRecord = new MedicalRecord("axel", "prost", "01/12/2020", List.of(), List.of("xilliathal"));

        DataStore dataStore = new DataStore(List.of(firestation), List.of(adult,child), List.of(adultRecord,childRecord));

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        FirestationPersonsDTO result = urlService.getPersonsByStation(4);

        assertEquals("prost",result.persons().getFirst().lastName());
        verify(jsonFileRepository).readData();
    }

    @Test
    public void getChildAlertByAddress_existingAddress_shouldReturnChildAlert(){
        Person adult = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person child = new Person("axel", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        MedicalRecord adultRecord = new MedicalRecord("alain", "prost", "12/25/2000", List.of("aznol:350mg","hydrapermazol:100mg"), List.of("illisoxian"));
        MedicalRecord childRecord = new MedicalRecord("axel", "prost", "01/12/2020", List.of(), List.of("xilliathal"));

        DataStore dataStore = new DataStore(List.of(),List.of(adult,child),List.of(adultRecord,childRecord));

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        List<ChildAlertDTO> result = urlService.getChildAlertByAddress("13 chemin de la mer");

        assertEquals("axel",result.getFirst().firstName());
        assertEquals("alain",result.getFirst().houseOtherPerson().getFirst().firstName());
        verify(jsonFileRepository).readData();
    }

    @Test
    public void getPhoneAlertByStation_existingStation_shouldReturnPhoneAlert(){
        Person adult = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person child = new Person("axel", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        Firestation firestation = new Firestation("13 chemin de la mer", "4");

        DataStore dataStore = new DataStore(List.of(firestation),List.of(adult,child),List.of());

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        Set<String> result = urlService.getPhoneAlertByStation(4);

        assertEquals(1, result.size());
        assertTrue(result.contains("0490251246"));
        verify(jsonFileRepository).readData();
    }

    @Test
    public void getPersonsAndStationByAddress_existingAddress_shouldReturnPersonsAndStation(){
        String address = "13 chemin de la mer";

        Person adult = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person child = new Person("axel", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        Firestation firestation = new Firestation("13 chemin de la mer", "4");

        MedicalRecord adultRecord = new MedicalRecord("alain", "prost", "12/25/2000", List.of("aznol:350mg","hydrapermazol:100mg"), List.of("illisoxian"));
        MedicalRecord childRecord = new MedicalRecord("axel", "prost", "01/12/2020", List.of(), List.of("xilliathal"));

        DataStore dataStore = new DataStore(List.of(firestation),List.of(adult,child),List.of(adultRecord,childRecord));

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        FireDTO result = urlService.getPersonsAndStationByAddress(address);

        assertEquals("4", result.station());
        assertEquals("aznol:350mg", result.persons().getFirst().medications().getFirst());
        verify(jsonFileRepository).readData();
    }

    @Test
    public void getFloodByStations_existingStations_shouldReturnFlood(){
        List<String> stations = List.of("4");

        Person person1 = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person person2 = new Person("axel", "martin", "4 rue de la poste", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        Firestation firestation1 = new Firestation("13 chemin de la mer", "4");
        Firestation firestation2 = new Firestation("4 rue de la poste", "2");

        MedicalRecord person1Record = new MedicalRecord("alain", "prost", "12/25/2000", List.of("aznol:350mg","hydrapermazol:100mg"), List.of("illisoxian"));
        MedicalRecord person2Record = new MedicalRecord("axel", "prost", "01/12/2020", List.of(), List.of("xilliathal"));

        DataStore dataStore = new DataStore(List.of(firestation1,firestation2),List.of(person1,person2),List.of(person1Record,person2Record));

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        List<FloodDTO> result = urlService.getFloodByStations(stations);

        assertEquals("13 chemin de la mer",result.getFirst().address());
        assertEquals("prost",result.getFirst().persons().getFirst().lastName());

        verify(jsonFileRepository).readData();
    }

    @Test
    public void getPersonInfoLastName_existingLastName_shouldReturnPersonInfo() {
        String lastName = "prost";

        Person person1 = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person person2 = new Person("axel", "prost", "4 rue de la poste", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        MedicalRecord person1Record = new MedicalRecord("alain", "prost", "12/25/2000", List.of("aznol:350mg","hydrapermazol:100mg"), List.of("illisoxian"));
        MedicalRecord person2Record = new MedicalRecord("axel", "prost", "01/12/2020", List.of(), List.of("xilliathal"));

        DataStore dataStore = new DataStore(List.of(),List.of(person1,person2),List.of(person1Record,person2Record));

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        List<PersonInfoLastNameDTO> result = urlService.getPersonInfoLastName(lastName);

        assertEquals(2,result.size());
        assertEquals("xilliathal", result.get(1).allergies().getFirst());

        verify(jsonFileRepository).readData();
    }

    @Test
    public void getPersonsEmailByCity_existingCity_shouldReturnPersonsEmail(){
        String city = "avignon";

        Person person1 = new Person("alain", "prost", "13 chemin de la mer", "84000", "Avignon", "0490251246", "alain.prost@email.fr");
        Person person2 = new Person("axel", "prost", "4 rue de la poste", "84000", "Avignon", "0490251246", "axel.prost@email.fr");

        DataStore dataStore = new DataStore(List.of(),List.of(person1,person2),List.of());

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        List<String> result = urlService.getPersonsEmailByCity(city);

        assertEquals(2,result.size());
        assertEquals("axel.prost@email.fr",result.get(1));

        verify(jsonFileRepository).readData();
    }

}
