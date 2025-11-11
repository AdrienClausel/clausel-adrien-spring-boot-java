package com.safetynet.api.controller;

import com.safetynet.api.dto.*;
import com.safetynet.api.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void testGetChildAlertByAddress() throws Exception{
        String address = "3 rue de la manche";

        List<ChildAlertDTO> dto = List.of(
                new ChildAlertDTO(
                        "prost",
                        "alain",
                        45,
                        List.of(
                            new ChildAlertDTO.HouseOtherPerson("veil","simone")
                        )
                )
        );

        when(urlService.getChildAlertByAddress(eq(address))).thenReturn(dto);

        mockMvc.perform(get("/childAlert")
            .param("address",address))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].lastName").value("prost"))
            .andExpect(jsonPath("$.[0].firstName").value("alain"))
            .andExpect(jsonPath("$.[0].age").value(45))
            .andExpect(jsonPath("$.[0].houseOtherPerson[0].firstName").value("simone"))
            .andExpect(jsonPath("$.[0].houseOtherPerson[0].lastName").value("veil"));

        verify(urlService, times(1)).getChildAlertByAddress(eq(address));
    }

    @Test
    public void testGetChildAlertByAddressReturnEmpty() throws Exception{
        String address = "3 rue de la manche";

        List<ChildAlertDTO> dto = new ArrayList<>();

        when(urlService.getChildAlertByAddress(eq(address))).thenReturn(dto);

        mockMvc.perform(get("/childAlert")
            .param("address",address))
            .andExpect(status().isOk())
            .andExpect(content().string(""));

        verify(urlService, times(1)).getChildAlertByAddress(eq(address));
    }

    @Test
    public void testGetPhoneAlertByStation() throws Exception{
        int station = 3;

        Set<String> dto = new LinkedHashSet<>();
        dto.add("0490311245");
        dto.add("0490311289");

        when(urlService.getPhoneAlertByStation(eq(station))).thenReturn(dto);

        mockMvc.perform(get("/phoneAlert")
            .param("station",String.valueOf(station)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0]").value("0490311245"))
            .andExpect(jsonPath("$.[1]").value("0490311289"));

        verify(urlService, times(1)).getPhoneAlertByStation(eq(station));
    }

    @Test
    public void testGetPersonsAndStationByAddress() throws Exception{
        String address = "3 rue de la manche";

        FireDTO dto = new FireDTO(
                List.of(
                        new FireDTO.FirePersonDTO(
                                "prost",
                                "0489321245",
                                45,
                                List.of("aznol:350mg", "hydrapermazol:100mg"),
                                List.of("xilliathal")
                        )
                ),
                "10"
        );

        when(urlService.getPersonsAndStationByAddress(eq(address))).thenReturn(dto);

        mockMvc.perform(get("/fire")
            .param("address",address))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.persons[0].lastName").value("prost"))
            .andExpect(jsonPath("$.persons[0].phone").value("0489321245"))
            .andExpect(jsonPath("$.persons[0].age").value(45))
            .andExpect(jsonPath("$.persons[0].medications[0]").value("aznol:350mg"))
            .andExpect(jsonPath("$.persons[0].medications[1]").value("hydrapermazol:100mg"))
            .andExpect(jsonPath("$.persons[0].allergies[0]").value("xilliathal"))
            .andExpect(jsonPath("$.station").value("10"));

        verify(urlService, times(1)).getPersonsAndStationByAddress(eq(address));
    }

    @Test
    public void testGetFloodByStations() throws Exception{
        List<String> stations = List.of("3","2","4");

        List<FloodDTO> dto = List.of(
                new FloodDTO(
                        "3 rue de la mer",
                        List.of(
                                new FloodDTO.FloodPersonDTO(
                                        "prost",
                                        "0490541236",
                                        45,
                                        List.of("aznol:350mg", "hydrapermazol:100mg"),
                                        List.of("xilliathal")
                                        )
                        )
                )
        );

        when(urlService.getFloodByStations(eq(stations))).thenReturn(dto);

        mockMvc.perform(get("/flood")
            .param("stations",stations.toArray(new String[0])))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].address").value("3 rue de la mer"))
            .andExpect(jsonPath("$.[0].persons[0].lastName").value("prost"))
            .andExpect(jsonPath("$.[0].persons[0].phone").value("0490541236"))
            .andExpect(jsonPath("$.[0].persons[0].age").value(45))
            .andExpect(jsonPath("$.[0].persons[0].medications[0]").value("aznol:350mg"))
            .andExpect(jsonPath("$.[0].persons[0].medications[1]").value("hydrapermazol:100mg"))
            .andExpect(jsonPath("$.[0].persons[0].allergies[0]").value("xilliathal"));

        verify(urlService, times(1)).getFloodByStations(eq(stations));
    }

    @Test
    public void testGetPersonInfoLastName() throws Exception{
        String lastName = "prost";

        List<PersonInfoLastNameDTO> dto = List.of(
            new PersonInfoLastNameDTO(
                    "prost",
                    "10 chemin de la poste",
                    54,
                    "alain.prost@email.fr",
                    List.of("aznol:350mg", "hydrapermazol:100mg"),
                    List.of("xilliathal")
            )
        );

        when(urlService.getPersonInfoLastName(eq(lastName))).thenReturn(dto);

        mockMvc.perform(get("/personInfolastName")
            .param("lastName",lastName))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].lastName").value("prost"))
            .andExpect(jsonPath("$.[0].address").value("10 chemin de la poste"))
            .andExpect(jsonPath("$.[0].age").value(54))
            .andExpect(jsonPath("$.[0].email").value("alain.prost@email.fr"))
            .andExpect(jsonPath("$.[0].medications[0]").value("aznol:350mg"))
            .andExpect(jsonPath("$.[0].medications[1]").value("hydrapermazol:100mg"))
            .andExpect(jsonPath("$.[0].allergies[0]").value("xilliathal"));

        verify(urlService, times(1)).getPersonInfoLastName(eq(lastName));
    }

    @Test
    public void testGetPersonsEmailByCity() throws Exception{
        String city = "marseille";

        List<String> dto = List.of(
                "alain.prost@email.fr",
                "simone.veil@email.fr"
        );

        when(urlService.getPersonsEmailByCity(eq(city))).thenReturn(dto);

        mockMvc.perform(get("/communityEmail")
                        .param("city",city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value("alain.prost@email.fr"))
                .andExpect(jsonPath("$.[1]").value("simone.veil@email.fr"));

        verify(urlService, times(1)).getPersonsEmailByCity(eq(city));
    }

}
