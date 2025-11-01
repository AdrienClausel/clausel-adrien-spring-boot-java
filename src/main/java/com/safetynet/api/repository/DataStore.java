package com.safetynet.api.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataStore {
    private List<Firestation> firestations = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
}
