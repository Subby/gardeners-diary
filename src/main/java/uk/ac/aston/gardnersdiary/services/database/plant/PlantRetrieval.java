package uk.ac.aston.gardnersdiary.services.database.plant;

import uk.ac.aston.gardnersdiary.models.Plant;

public interface PlantRetrieval {

    public String addPlant(Plant plantToAdd);

    public Plant getPlantById(int id);

}
