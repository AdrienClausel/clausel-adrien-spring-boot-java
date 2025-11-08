package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private FirestationService firestationService;

    @Test
    public void testAdd(){
        Firestation newStation = new Firestation();
        newStation.setAddress("38 rue du clocher");
        newStation.setStation("3");

        DataStore dataStore = new DataStore();
        Firestation station = new Firestation();
        newStation.setAddress("chemin des falaises");
        newStation.setStation("1");
        dataStore.getFirestations().add(station);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        firestationService.add(newStation);

        assertEquals(newStation.getAddress(), dataStore.getFirestations().get(1).getAddress());
    }
}
