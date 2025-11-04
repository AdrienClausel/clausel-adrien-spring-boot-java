package com.safetynet.api.dto;

import java.util.List;

public record FireDTO(
    List<FirePersonDTO> persons,
    String station
) {
    public record FirePersonDTO(
            String lastName,
            String phone,
            int age,
            List<String> medications,
            List<String> allergies
    ){}
}