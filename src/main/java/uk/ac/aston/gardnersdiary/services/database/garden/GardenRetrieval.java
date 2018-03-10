package uk.ac.aston.gardnersdiary.services.database.garden;

import uk.ac.aston.gardnersdiary.models.Garden;

/**
 * Created by Denver on 16/11/2017.
 */
public interface GardenRetrieval {
    public Garden getGardenById(int id);
    public Garden getGarden();
    public void saveGarden(Garden garden);
    public void updateGardenJSON(String JSON);
}