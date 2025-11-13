package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.DataStore;
import com.safetynet.api.repository.IMedicalRecordRepository;
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
    private IMedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    public void testAdd_shouldCallRepository(){

        MedicalRecord newMedicalRecord = new MedicalRecord("prost","alain","12/28/2000",List.of("aznol:350mg","hydrapermazol:100mg"),List.of("nillacilan"));

        medicalRecordService.add(newMedicalRecord);

        verify(medicalRecordRepository,times(1)).add(newMedicalRecord);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_existingLastNameAndFirstName_shouldSuccess(){
        String lastName = "prost";
        String firstName = "alain";

        MedicalRecord updateMedicalRecord = new MedicalRecord("prost","alain","12/28/2000",List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),List.of("nillacilan"));

        when(medicalRecordRepository.updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord)).thenReturn(true);

        medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord);

        verify(medicalRecordRepository,times(1)).updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord);
    }

    @Test
    public void testUpdateByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException(){
        String lastName = "prost";
        String firstName = "alain";

        MedicalRecord updateMedicalRecord = new MedicalRecord("prost","alain","12/28/2000",List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"),List.of("nillacilan"));

        when(medicalRecordRepository.updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            medicalRecordService.updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord)
        );

        assertEquals("Dossier médical non trouvé", exception.getMessage());
        verify(medicalRecordRepository,times(1)).updateByLastNameAndFirstName(lastName,firstName,updateMedicalRecord);
    }

    @Test
    public void deleteByLastNameAndFirstName_existingLastNameAndFirstName_shouldSuccess(){
        String lastName = "prost";
        String firstName = "alain";

        when(medicalRecordRepository.deleteByLastNameAndFirstName(lastName,firstName)).thenReturn(true);

        medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName);

        verify(medicalRecordRepository,times(1)).deleteByLastNameAndFirstName(lastName,firstName);
    }

    @Test
    public void deleteByLastNameAndFirstName_lastNameAndFirstNameNotFound_shouldThrowException(){
        String lastName = "prost";
        String firstName = "alain";

        when(medicalRecordRepository.deleteByLastNameAndFirstName(lastName,firstName)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            medicalRecordService.deleteByLastNameAndFirstName(lastName,firstName)
        );

        assertEquals("Dossier médical non trouvé", exception.getMessage());
        verify(medicalRecordRepository,times(1)).deleteByLastNameAndFirstName(lastName,firstName);
    }
}
