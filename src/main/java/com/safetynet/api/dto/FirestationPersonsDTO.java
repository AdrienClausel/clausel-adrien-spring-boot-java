package com.safetynet.api.dto;

import java.util.List;

public record FirestationPersonsDTO(
    List<PersonDTO> persons,
    long adultCount,
    long childCount
) {
    public record PersonDTO(
        String lastName,
        String firstName,
        String address,
        String phone
    ){}
}
