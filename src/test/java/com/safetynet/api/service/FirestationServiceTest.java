package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private FirestationService firestationService;

    @Test
    public void add_shouldAddAndWriteData(){
        Firestation newFireStation = new Firestation();
        newFireStation.setAddress("38 rue du clocher");
        newFireStation.setStation("3");

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        firestationService.add(newFireStation);

        assertEquals(newFireStation.getAddress(), dataStore.getFirestations().get(1).getAddress());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateStationByAddress_existingAddress_shouldUpdateAndWriteData(){
        String address = "chemin des falaises";

        Firestation updateFireStation = new Firestation();
        updateFireStation.setAddress("chemin des falaises");
        updateFireStation.setStation("3");

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        firestationService.updateStationByAddress(address,updateFireStation);

        assertEquals(updateFireStation.getStation(), dataStore.getFirestations().getFirst().getStation());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateStationByAddress_addressNotFound_shouldThrowsException(){
        String address = "chemin des pins";

        Firestation updateFireStation = new Firestation();
        updateFireStation.setAddress("chemin des falaises");
        updateFireStation.setStation("3");

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                firestationService.updateStationByAddress(address,updateFireStation)
        );

        assertEquals("Adresse non trouvée", exception.getMessage());
        assertNotEquals(updateFireStation.getStation(), dataStore.getFirestations().getFirst().getStation());
        verify(jsonFileRepository, never()).writeData(dataStore);
    }

    @Test
    public void remove_existingAddress_shouldRemoveAndWriteData(){
        String address = "chemin des falaises";
        String station = "1";

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        firestationService.remove(address,station);

        assertEquals(0, dataStore.getFirestations().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void remove_addressNotFound_shouldThrowException(){
        String address = "chemin des pins";
        String station = "1";

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                firestationService.remove(address,station)
        );

        assertEquals("Adresse non trouvée", exception.getMessage());
        assertEquals(1, dataStore.getFirestations().size());
        verify(jsonFileRepository, never()).writeData(dataStore);
    }
}
