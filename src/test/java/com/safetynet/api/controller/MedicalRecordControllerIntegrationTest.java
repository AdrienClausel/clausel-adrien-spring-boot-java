package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.FirestationService;
import com.safetynet.api.service.MedicalRecordService;
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
public class MedicalRecordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testCreateMedicalRecord() throws Exception {
        String json = """
                    {
                         "firstName": "alain",
                         "lastName": "prost",
                         "birthdate": "2020-01-15",
                         "medications": [
                             "aznol:350mg",
                             "hydrapermazol:100mg"
                         ],
                         "allergies": [
                             "nillacilan"
                         ]
                    }
                """;

        mockMvc.perform(
                        post("/medicalRecord")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1))
                .add(
                        argThat(m -> m.getLastName().equals("prost")
                                && m.getFirstName().equals("alain")
                        )
                );
    }

    @Test
    public void testUpdateMedicalRecordByLastNameAndFirstName() throws Exception{
        String firstName = "alain";
        String lastName = "prost";
        String json = """
                    {
                         "firstName": "alain",
                         "lastName": "prost",
                         "birthdate": "2020-01-15",
                         "medications": [
                             "aznol:350mg",
                             "hydrapermazol:100mg"
                         ],
                         "allergies": [
                             "nillacilan"
                         ]
                    }
                """;

        mockMvc.perform(
                        put("/medicalRecord/{lastName}/{firstName}", lastName,firstName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        verify(medicalRecordService, times(1))
                .updateByLastNameAndFirstName(eq(lastName),eq(firstName),any(MedicalRecord.class));
    }

    @Test
    public void testDeleteMedicalRecordByLastNameAndFirstName() throws Exception{
        String lastName = "prost";
        String firstName = "alain";

        mockMvc.perform(
                delete("/medicalRecord/{lastName}/{firstName}", lastName, firstName)
        ).andExpect(status().isOk());

        verify(medicalRecordService, times(1))
                .deleteByLastNameAndFirstName(lastName,firstName);
    }
}