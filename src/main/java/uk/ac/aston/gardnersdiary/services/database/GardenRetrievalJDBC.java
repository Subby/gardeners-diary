package uk.ac.aston.gardnersdiary.services.database;

import org.javalite.activejdbc.Model;

import uk.ac.aston.gardnersdiary.models.garden.Garden;

/**
 * Created by Denver on 16/11/2017.
 */
public class GardenRetrievalJDBC extends Model implements GardenRetrieval {

    public Garden getGardenById(int id) {
        GardenRetrievalJDBC gardenRecord = new GardenRetrievalJDBC();
        Garden garden = new Garden();
        garden.setName((String) gardenRecord.getString("name"));
        garden.setImage((String) gardenRecord.getString("image"));
        return garden;
    }

    public void saveGarden(Garden garden) {
        GardenRetrievalJDBC gardenRecord = new GardenRetrievalJDBC();
        gardenRecord.set("name", garden.getName());
        gardenRecord.set("image", garden.getName());
    }

}
