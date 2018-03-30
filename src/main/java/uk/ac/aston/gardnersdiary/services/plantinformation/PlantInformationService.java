package uk.ac.aston.gardnersdiary.services.plantinformation;

/**
 * Created by Denver on 22/10/2017.
 */
public interface PlantInformationService {

    /**
     * Gets plant information from an API given the plant name.
     * @param plantName the plant name
     * @return the plant information for the given name
     */
    String getPlantInformation(String plantName);

}
