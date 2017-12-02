package uk.ac.aston.gardnersdiary.services.database;

import org.javalite.activejdbc.Model;

import uk.ac.aston.gardnersdiary.models.garden.Garden;

/**
 * Created by Denver on 16/11/2017.
 */
public class GardenRetrievalJDBC implements GardenRetrieval {

    public Garden getGardenById(int id) {
        GardenJDBCModel gardenJDBCModel = GardenJDBCModel.findFirst("id = ?", id);
        Garden gardenModel = mapToGardenModel(gardenJDBCModel);
        return gardenModel;
    }

    private Garden mapToGardenModel(GardenJDBCModel gardenJDBCModel) {
        Garden gardenModel = new Garden();
        gardenModel.setId((Integer) gardenJDBCModel.getId());
        gardenModel.setName(gardenJDBCModel.getName());
        gardenModel.setImage(gardenJDBCModel.getImage());
        gardenModel.setRegionJson(gardenJDBCModel.getRegionJson());
        gardenModel.setCreatedAt(gardenJDBCModel.getCreatedAt());
        gardenModel.setUpdatedAt(gardenJDBCModel.getUpdatedAt());
        return gardenModel;
    }

    public void saveGarden(Garden garden) {
        GardenJDBCModel gardenJDBCModel = mapFromGardenModel(garden);
        gardenJDBCModel.saveIt();
    }

    private GardenJDBCModel mapFromGardenModel(Garden garden) {
        GardenJDBCModel gardenJDBCModel = new GardenJDBCModel();
        gardenJDBCModel.setName(garden.getName());
        gardenJDBCModel.setImage(garden.getImage());
        gardenJDBCModel.setRegionJson(garden.getRegionJson());
        return gardenJDBCModel;
    }

}
