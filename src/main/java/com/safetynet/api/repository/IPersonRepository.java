package com.safetynet.api.repository;

import com.safetynet.api.model.Person;

public interface IPersonRepository {
    void add(Person person);
    boolean updateByLastNameAndFirstName(String lastName, String firstName,Person person);
    boolean deleteByLastNameAndFirstName(String lastName, String firstName);
}
