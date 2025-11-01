package com.safetynet.api.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecord {
    public String firstName;
    public String lastName;
    public String birthdate;
    public List<String> medications;
    public List<String> allergies;
}
