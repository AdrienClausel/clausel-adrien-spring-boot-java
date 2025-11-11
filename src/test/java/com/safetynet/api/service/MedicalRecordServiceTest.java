package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    public void add_shouldAddAndWriteData(){
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setLastName("prost");
        newMedicalRecord.setFirstName("alain");
        newMedicalRecord.setBirthdate("12/28/2000");
        newMedicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        newMedicalRecord.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        medicalRecordService.add(newMedicalRecord);

        assertEquals(1,dataStore.getMedicalrecords().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
        String lastName = "prost";
        String firstName = "alain";

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setLastName("prost");
        medicalRecord.setFirstName("alain");
        medicalRecord.setBirthdate("12/28/2000");
        medicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));

        MedicalRecord medicalRecordUpdate = new MedicalRecord();
        medicalRecordUpdate.setLastName("prost");
        medicalRecordUpdate.setFirstName("alain");
        medicalRecordUpdate.setBirthdate("12/28/2000");
        medicalRecordUpdate.setMedications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"));
        medicalRecordUpdate.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();
        dataStore.getMedicalrecords().add(medicalRecord);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,medicalRecordUpdate);

        assertEquals(medicalRecordUpdate.getMedications(),dataStore.getMedicalrecords().getFirst().getMedications());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void updateByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException(){
        String lastName = "veil";
        String firstName = "simone";

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setLastName("prost");
        medicalRecord.setFirstName("alain");
        medicalRecord.setBirthdate("12/28/2000");
        medicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));

        MedicalRecord medicalRecordUpdate = new MedicalRecord();
        medicalRecordUpdate.setLastName("veil");
        medicalRecordUpdate.setFirstName("simone");
        medicalRecordUpdate.setBirthdate("12/28/2000");
        medicalRecordUpdate.setMedications(List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"));
        medicalRecordUpdate.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();
        dataStore.getMedicalrecords().add(medicalRecord);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,medicalRecordUpdate)
        );

        assertEquals("Dossier médical non trouvé", exception.getMessage());
        assertNotEquals(medicalRecordUpdate.getMedications(),dataStore.getMedicalrecords().getFirst().getMedications());
        verify(jsonFileRepository,never()).writeData(dataStore);
    }

    @Test
    public void deleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
        String lastName = "prost";
        String firstName = "alain";

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setLastName("prost");
        medicalRecord.setFirstName("alain");
        medicalRecord.setBirthdate("12/28/2000");
        medicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();
        dataStore.getMedicalrecords().add(medicalRecord);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName);

        assertEquals(0,dataStore.getMedicalrecords().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void deleteByLastNameAndFirstName_existingNotFound_shouldThrowException(){
        String lastName = "veil";
        String firstName = "simone";

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setLastName("prost");
        medicalRecord.setFirstName("alain");
        medicalRecord.setBirthdate("12/28/2000");
        medicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();
        dataStore.getMedicalrecords().add(medicalRecord);

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName)
        );

        assertEquals("Dossier médical non trouvé", exception.getMessage());
        assertEquals(1,dataStore.getMedicalrecords().size());
        verify(jsonFileRepository,never()).writeData(dataStore);
    }
}
