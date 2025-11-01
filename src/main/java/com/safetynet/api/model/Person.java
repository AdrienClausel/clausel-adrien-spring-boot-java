package com.safetynet.api.model;

import lombok.Data;

@Data
public class Person {
    public String firstName;
    public String lastName;
    public String address;
    public String zip;
    public String city;
    public String phone;
    public String email;
}
