package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.repository.IFirestationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private IFirestationRepository firestationRepository;

    @InjectMocks
    private FirestationService firestationService;

    @Test
    public void testAdd_shouldCallRepository(){
        Firestation newFireStation = new Firestation("38 rue du clocher","3");

        firestationService.add(newFireStation);

        verify(firestationRepository,times(1)).add(newFireStation);
    }

    @Test
    public void testUpdateStationByAddress_existingAddress_shouldSuccess(){
        String address = "chemin des falaises";

        Firestation updateFireStation = new Firestation(address,"2");

        when(firestationRepository.updateStationByAddress(address,updateFireStation)).thenReturn(true);

        firestationService.updateStationByAddress(address,updateFireStation);

        verify(firestationRepository,times(1)).updateStationByAddress(address,updateFireStation);
    }

    @Test
    public void testUpdateStationByAddress_addressNotFound_shouldThrowsException(){
        String address = "chemin des falaises";

        Firestation updateFireStation = new Firestation(address,"3");


        when(firestationRepository.updateStationByAddress(address,updateFireStation)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                firestationService.updateStationByAddress(address,updateFireStation)
        );

        assertEquals("Adresse non trouvée", exception.getMessage());
        verify(firestationRepository, times(1)).updateStationByAddress(address,updateFireStation);
    }

    @Test
    public void testRemove_existingAddress_shouldSuccess(){
        String address = "chemin des falaises";
        String station = "1";

        when(firestationRepository.remove(address,station)).thenReturn(true);

        firestationService.remove(address,station);

        verify(firestationRepository,times(1)).remove(address,station);
    }

    @Test
    public void testRemove_addressNotFound_shouldThrowException(){
        String address = "chemin des falaises";
        String station = "1";

        when(firestationRepository.remove(address,station)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                firestationService.remove(address,station)
        );

        assertEquals("Adresse non trouvée", exception.getMessage());
        verify(firestationRepository,times(1)).remove(address,station);
    }
}
