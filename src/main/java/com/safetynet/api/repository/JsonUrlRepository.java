package com.safetynet.api.repository;

import com.safetynet.api.model.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JsonUrlRepository implements IUrlRepository {

    @Autowired
    private JsonFileRepository jsonFileRepository;

    @Override
    public DataStore readData() {
        return jsonFileRepository.readData();
    }
}
