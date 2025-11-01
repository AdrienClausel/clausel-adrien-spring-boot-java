package com.safetynet.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Firestation {
    private String address;
    private String station;
}
