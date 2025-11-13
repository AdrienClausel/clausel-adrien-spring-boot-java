package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonMedicalRecordRepositoryTest {
    @Mock
    private JsonFileRepository jsonFileRepository;

    @InjectMocks
    private JsonMedicalRecordRepository jsonMedicalRecordRepository;

    @Test
    public void testAdd_shouldAddAndWriteData(){
        MedicalRecord newMedicalRecord = new MedicalRecord();
        newMedicalRecord.setLastName("prost");
        newMedicalRecord.setFirstName("alain");
        newMedicalRecord.setBirthdate("12/28/2000");
        newMedicalRecord.setMedications(List.of("aznol:350mg","hydrapermazol:100mg"));
        newMedicalRecord.setAllergies(List.of("nillacilan"));

        DataStore dataStore = new DataStore();

        when(jsonFileRepository.readData()).thenReturn(dataStore);

        jsonMedicalRecordRepository.add(newMedicalRecord);

        assertEquals(1,dataStore.getMedicalrecords().size());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
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

        jsonMedicalRecordRepository.updateByLastNameAndFirstName(lastName,firstName,medicalRecordUpdate);

        assertEquals(medicalRecordUpdate.getMedications(),dataStore.getMedicalrecords().getFirst().getMedications());
        verify(jsonFileRepository).writeData(dataStore);
    }

    @Test
    public void testDeleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldUpdateAndWriteData(){
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

        jsonMedicalRecordRepository.deleteByLastNameAndFirstName(lastName,firstName);

        assertEquals(0,dataStore.getMedicalrecords().size());
        verify(jsonFileRepository).writeData(dataStore);
    }
}
