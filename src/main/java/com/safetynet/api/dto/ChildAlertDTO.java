package com.safetynet.api.dto;

import java.util.List;

public record ChildAlertDTO(
    List<ChildrenDTO> children
) {
    public record ChildrenDTO(
        String lastName,
        String firstName,
        int age,
        List<HouseOtherPerson> houseOtherPerson
    ){
        public record HouseOtherPerson(
            String lastName,
            String firstName
        ){}
    }
}
