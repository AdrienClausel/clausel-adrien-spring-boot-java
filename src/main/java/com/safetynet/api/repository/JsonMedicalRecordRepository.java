package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import com.safetynet.api.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JsonMedicalRecordRepository implements IMedicalRecordRepository {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    @Override
    public void add(MedicalRecord medicalRecord) {
        DataStore dataStore = jsonFileRepository.readData();
        dataStore.getMedicalrecords().add(medicalRecord);
        jsonFileRepository.writeData(dataStore);
    }

    @Override
    public boolean updateByLastNameAndFirstName(String lastName, String firstName, MedicalRecord medicalRecord) {
        DataStore dataStore = jsonFileRepository.readData();
        Optional<MedicalRecord> medicalRecordExisting = dataStore
                .getMedicalrecords()
                .stream()
                .filter(m ->
                        m.getLastName().equalsIgnoreCase(lastName)
                                && m.getFirstName().equalsIgnoreCase(firstName)
                )
                .findFirst();
        if (medicalRecordExisting.isPresent()) {
            medicalRecordExisting.get().setBirthdate(medicalRecord.getBirthdate());
            medicalRecordExisting.get().setMedications(medicalRecord.getMedications());
            medicalRecordExisting.get().setAllergies(medicalRecord.getAllergies());
            jsonFileRepository.writeData(dataStore);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByLastNameAndFirstName(String lastName, String firstName) {
        DataStore dataStore = jsonFileRepository.readData();
        boolean removed = dataStore.getMedicalrecords().removeIf(m ->
                m.lastName.equalsIgnoreCase(lastName)
                        && m.firstName.equalsIgnoreCase(firstName)
        );
        if (removed){
            jsonFileRepository.writeData(dataStore);
        }
        return removed;
    }
}
