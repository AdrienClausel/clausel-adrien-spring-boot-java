package com.safetynet.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    public String firstName;
    public String lastName;
    public String address;
    public String zip;
    public String city;
    public String phone;
    public String email;
}
