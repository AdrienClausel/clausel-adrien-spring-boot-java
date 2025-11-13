package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;

public interface IMedicalRecordRepository {
    void add(MedicalRecord medicalRecord);
    boolean updateByLastNameAndFirstName(String lastName, String firstName, MedicalRecord medicalRecord);
    boolean deleteByLastNameAndFirstName(String lastName, String firstName);
}

