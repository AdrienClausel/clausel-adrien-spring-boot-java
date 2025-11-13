package com.safetynet.api.repository;

import com.safetynet.api.model.Firestation;

public interface IFirestationRepository {
    void add(Firestation firestation);
    boolean remove(String address, String station);
    boolean updateStationByAddress(String address, Firestation firestation);
}
