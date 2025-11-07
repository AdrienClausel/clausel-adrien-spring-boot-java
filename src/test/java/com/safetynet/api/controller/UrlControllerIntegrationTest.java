package com.safetynet.api.controller;

import com.safetynet.api.dto.FirestationPersonsDTO;
import com.safetynet.api.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    UrlService urlService;

    @Test
    public void testGetPersonsByStation() throws Exception{
        int station = 3;

        FirestationPersonsDTO dto = new FirestationPersonsDTO(
                List.of(new FirestationPersonsDTO.PersonDTO("prost","alain","3 rue de la manche","0490253112")),
                12,
                5
        );

        when(urlService.getPersonsByStation(eq(station))).thenReturn(dto);

        mockMvc.perform(get("/firestation")
                .param("station",String.valueOf(station)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(12))
                .andExpect(jsonPath("$.childCount").value(5))
                .andExpect(jsonPath("$.persons[0].firstName").value("alain"))
                .andExpect(jsonPath("$.persons[0].lastName").value("prost"));

        verify(urlService, times(1)).getPersonsByStation(eq(station));

    }

}
