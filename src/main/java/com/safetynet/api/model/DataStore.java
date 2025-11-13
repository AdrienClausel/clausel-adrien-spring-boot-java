package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
@NoArgsConstructor
@AllArgsConstructor
public class DataStore {
    private List<Firestation> firestations = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<MedicalRecord> medicalrecords = new ArrayList<>();
}
