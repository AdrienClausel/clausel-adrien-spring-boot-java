package com.safetynet.api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;

/**
 * Classe de gestion de la couche repository avec accès à un fichier Json comme source de données
 */
@Slf4j
@Component
public class JsonFileRepository {

    @Value("${data.file.name}")
    private String dataFileName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Lecture des données depuis de fichier Json
     * @return DataStore objet contenant les données du fichier Json
     */
    public DataStore readData() {
       try {
           File file = new File(dataFileName);
           log.debug("Ouverture du fichier pour la lecture");
           var dataStore = objectMapper.readValue(file, new TypeReference<DataStore>() {});
           log.debug("Lecture du fichier et copie dans le stockage des données");
           return dataStore;
        } catch (Exception e) {
            throw new RuntimeException("Erreur de lecture du fichier Json",e);
        }
    }

    /**
     * Ecriture des données dans le fichier Json
     * @param dataStore objet contenant les données à ecrire dans le fichier Json
     */
    public void writeData(DataStore dataStore){
        try {
            File file = new File(dataFileName);
            log.debug("Ouverture du fichier pour l'écriture");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,dataStore);
            log.debug("Écriture du stockage des données dans le fichier");
        } catch (Exception e) {
            throw new RuntimeException("Erreur d'écriture dans le fichier Json");
        }
    }

}
