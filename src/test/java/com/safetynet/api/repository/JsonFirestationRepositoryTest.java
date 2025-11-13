package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.Firestation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsonFirestationRepositoryTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private JsonFirestationRepository jsonFirestationRepository;

    @Test
    public void testAdd_shouldAddAndWriteData(){
        Firestation newFireStation = new Firestation();
        newFireStation.setAddress("38 rue du clocher");
        newFireStation.setStation("3");

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        jsonFirestationRepository.add(newFireStation);

        assertEquals(newFireStation.getAddress(), dataStore.getFirestations().get(1).getAddress());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testUpdateStationByAddress_existingAddress_shouldUpdateAndWriteData(){
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

        jsonFirestationRepository.updateStationByAddress(address,updateFireStation);

        assertEquals(updateFireStation.getStation(), dataStore.getFirestations().getFirst().getStation());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testRemove_existingAddress_shouldRemoveAndWriteData(){
        String address = "chemin des falaises";
        String station = "1";

        DataStore dataStore = new DataStore();
        Firestation fireStation = new Firestation();
        fireStation.setAddress("chemin des falaises");
        fireStation.setStation("1");
        dataStore.getFirestations().add(fireStation);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        jsonFirestationRepository.remove(address,station);

        assertEquals(0, dataStore.getFirestations().size());
        verify(jsonFileRepository).writeData(dataStore);
    }
}
