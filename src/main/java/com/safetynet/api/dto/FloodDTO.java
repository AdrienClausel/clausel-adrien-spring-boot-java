package com.safetynet.api.dto;

import java.util.List;

public record FloodDTO(
        String address,
        List<FloodPersonDTO> persons
) {
    public record FloodPersonDTO(
            String lastName,
            String phone,
            int age,
            List<String> medications,
            List<String> allergies
    ){}
}
