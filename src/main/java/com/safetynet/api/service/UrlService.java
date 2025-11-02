package com.safetynet.api.service;

import com.safetynet.api.dto.ChildAlertDTO;
import com.safetynet.api.dto.FirestationPersonsDTO;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.DataStore;
import com.safetynet.api.repository.JsonFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UrlService {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    public FirestationPersonsDTO getPersonsByStation(final int station){
        DataStore dataStore = jsonFileRepository.readData();
        List<String> addressFilteredByStation = dataStore
                .getFirestations()
                .stream()
                .filter(f -> f.getStation().equals(String.valueOf(station)))
                .map(Firestation::getAddress)
                .toList();

        FirestationPersonsDTO firestationPersonsDTO = new FirestationPersonsDTO(
                new ArrayList<FirestationPersonsDTO.PersonDTO>(),
                0,
                0
        );

        List<FirestationPersonsDTO.PersonDTO> persons = new ArrayList<>();
        long childCount = 0;

        for (Person p: dataStore.getPersons()) {
            if (addressFilteredByStation.contains(p.getAddress())){
                persons.add(new FirestationPersonsDTO.PersonDTO(
                        p.getLastName(),
                        p.getFirstName(),
                        p.getAddress(),
                        p.getPhone()
                ));

                Optional<MedicalRecord> medicalRecord = dataStore
                        .getMedicalrecords()
                        .stream()
                        .filter(m ->
                                m.getLastName().equalsIgnoreCase(p.getLastName())
                                && m.getFirstName().equalsIgnoreCase(p.getFirstName())
                        )
                        .findFirst();

                if (medicalRecord.isPresent()){
                    if (isChild(medicalRecord.get().getBirthdate())){
                        childCount++;
                    }
                }
            }
        }

        long adultCount = persons.size() - childCount;

        return new FirestationPersonsDTO(persons,adultCount,childCount);
    }

    private boolean isChild(String stringBirthdate){
        return calculateAge(stringBirthdate) <= 18;
    }

    public ChildAlertDTO getChildAlertByAddress(String address) {
        DataStore dataStore = jsonFileRepository.readData();
        List<Person> persons = dataStore
                .getPersons()
                .stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();

        List<ChildAlertDTO.ChildrenDTO> children = new ArrayList<>();
        List<ChildAlertDTO.ChildrenDTO.HouseOtherPerson> houseOtherPerson = new ArrayList<>();

        for (Person p:persons){
            Optional<MedicalRecord> medicalRecord = dataStore
                    .getMedicalrecords()
                    .stream()
                    .filter(m ->
                            m.getLastName().equalsIgnoreCase(p.getLastName())
                                    && m.getFirstName().equalsIgnoreCase(p.getFirstName())
                    )
                    .findFirst();

            if (medicalRecord.isPresent()) {
                if (isChild(medicalRecord.get().getBirthdate())){
                    children.add(new ChildAlertDTO.ChildrenDTO(
                            p.getLastName(),
                            p.getFirstName(),
                            calculateAge(medicalRecord.get().getBirthdate()),
                            houseOtherPerson
                    ));
                } else {
                    houseOtherPerson.add(new ChildAlertDTO.ChildrenDTO.HouseOtherPerson(p.getLastName(), p.getFirstName()));
                }
            }
        }

        return new ChildAlertDTO(children);
    }

    private int calculateAge(String stringBirthdate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(stringBirthdate,dateTimeFormatter);
        return Period.between(birthDate,LocalDate.now()).getYears();
    }
}
