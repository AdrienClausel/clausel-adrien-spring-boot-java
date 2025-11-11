package com.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    public String firstName;
    public String lastName;
    public String birthdate;
    public List<String> medications;
    public List<String> allergies;
}
