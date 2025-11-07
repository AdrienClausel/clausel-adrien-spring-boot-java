package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FirestationService firestationService;

    @Test
    public void testCreateFirestation() throws Exception{
        String json = """
            {
                "address": "adresse de test",
                "station": "10"
            }
        """;

        mockMvc.perform(
                post("/firestation")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                )
                .andExpect(status().isOk());

        verify(firestationService, times(1))
                .add(
                        argThat(f->f.getAddress().equals("adresse de test")
                        && f.getStation().equals("10")
                        )
                );
    }

    @Test
    public void testUpdateStationByAddress() throws Exception{
        String address = "adresse de test";
        String json = """
                {
                    "station":"5"
                }
                """;

        mockMvc.perform(
                put("/firestation/{address}", address)
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk());

        verify(firestationService, times(1))
                .updateStationByAddress(eq(address),any(Firestation.class));
    }

    @Test
    public void testDeleteFirestation() throws Exception{
        String address = "adresse de test";
        String station = "5";

        mockMvc.perform(
                delete("/firestation/{address}/{station}", address, station)
        ).andExpect(status().isOk());

        verify(firestationService, times(1))
                .remove(address,station);
    }
}
