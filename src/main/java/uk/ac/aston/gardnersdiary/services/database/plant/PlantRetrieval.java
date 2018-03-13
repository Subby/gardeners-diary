package uk.ac.aston.gardnersdiary.services.database.plant;

import uk.ac.aston.gardnersdiary.models.Plant;

public interface PlantRetrieval {

    public String addPlant(Plant plantToAdd);
    public Plant getPlantById(int id);
    public String getPlantNameForId(int id);
    public String updatePlantDetails(int id, String name, String type);
}
