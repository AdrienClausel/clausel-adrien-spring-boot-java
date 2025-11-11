package com.safetynet.api.repository;

import com.safetynet.api.model.Person;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class JsonFileRepositoryTest {

    @Test
    public void testReadDataAndWriteData() throws IOException, NoSuchFieldException, IllegalAccessException {
        DataStore dataStore = new DataStore();
        Person p1 = new Person(){};
        p1.setLastName("prost");
        p1.setFirstName("alain");

        dataStore.setPersons(
                List.of(p1)
        );

        File tempFile = Files.createTempFile("test", ".json").toFile();
        tempFile.deleteOnExit();

        JsonFileRepository jsonFileRepository = new JsonFileRepository();

        Field field = JsonFileRepository.class.getDeclaredField("dataFileName");
        field.setAccessible(true);
        field.set(jsonFileRepository, tempFile.getAbsolutePath());

        jsonFileRepository.writeData(dataStore);

        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        DataStore dataStoreRead = jsonFileRepository.readData();

        assertNotNull(dataStoreRead);
        assertEquals(dataStore.getPersons().size(), dataStoreRead.getPersons().size());
        assertEquals(dataStore.getPersons().getFirst().getLastName(), dataStoreRead.getPersons().getFirst().getLastName());
        assertEquals(dataStore.getPersons().getFirst().getFirstName(), dataStoreRead.getPersons().getFirst().getFirstName());
    }
}
