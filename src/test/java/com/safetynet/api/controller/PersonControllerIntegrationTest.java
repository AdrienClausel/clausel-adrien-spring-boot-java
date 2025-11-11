package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
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
public class PersonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    PersonService personService;

    @Test
    public void testCreatePerson() throws Exception{

        String json = """
                {
                    "firstName": "alain",
                    "lastName": "prost",
                    "address": "10 rue de la mer",
                    "city": "Avignon",
                    "zip": "84000",
                    "phone": "043112565",
                    "email": "alain@email.com"
                }
                """;

        mockMvc.perform(
                        post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        verify(personService, times(1))
                .add(
                        argThat(p->p.getLastName().equals("prost")
                                && p.getFirstName().equals("alain")
                        )
                );
    }

    @Test
    public void testUpdatePersonByLastNameAndFirstName() throws Exception{
        String lastName = "prost";
        String firstName = "alain";
        String json = """
                {
                    "firstName": "alain",
                    "lastName": "prost",
                    "address": "10 rue de la mer",
                    "city": "Avignon",
                    "zip": "84000",
                    "phone": "043112565",
                    "email": "alain@email.com"
                }
                """;

        mockMvc.perform(
                        put("/person/{lastName}/{firstName}", lastName,firstName)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk());

        verify(personService, times(1))
                .updateByLastNameAndFirstName(eq(lastName), eq(firstName),any(Person.class));
    }

    @Test
    public void testDeletePersonByLastNameAndFirstName() throws Exception{
        String lastName = "prost";
        String firstName = "alain";

        mockMvc.perform(
                delete("/person/{lastName}/{firstName}", lastName, firstName)
        ).andExpect(status().isOk());

        verify(personService, times(1))
                .deleteByLastNameAndFirstName(lastName,firstName);
    }
}
