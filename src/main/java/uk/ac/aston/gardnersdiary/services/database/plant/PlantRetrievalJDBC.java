package uk.ac.aston.gardnersdiary.services.database.plant;

import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.models.Plant;

public class PlantRetrievalJDBC implements  PlantRetrieval {

    private static final String SUCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";

    @Override
    public String addPlant(Plant plantToAdd) {
        PlantJDBCModel plantJDBCModel = mapFromModel(plantToAdd);
        if(plantJDBCModel.save()) {
            return generateSuccessJsonOutput(plantToAdd.getName(), (Long) plantJDBCModel.getId());
        }
        return generateFailedJSONOutput();
    }

    @Override
    public Plant getPlantById(int id) {
        Plant plantModel = null;
        PlantJDBCModel plantJDBCModel = PlantJDBCModel.findFirst("id = ?", id);
        if(plantJDBCModel != null) {
            plantModel = mapToModel(plantJDBCModel);
        }
        return plantModel;
    }

    @Override
    public String getPlantNameForId(int id) {
        PlantJDBCModel plantJDBCModel = PlantJDBCModel.findFirst("id = ?", id);
        return (String) plantJDBCModel.get("name");
    }

    @Override
    public String updatePlantDetails(int id, String name, String type) {
        PlantJDBCModel plantJDBCModel = PlantJDBCModel.findFirst("id = ?", id);
        plantJDBCModel.set("name", name);
        plantJDBCModel.set("type", type);
        if(plantJDBCModel.save()) {
            return generateSucessJSON();
        }
        return generateFailedJSONOutput();
    }

    private String generateSucessJSON() {
        return new JSONStringer().object().key("status").value(SUCESS_STATUS).endObject().toString();
    }

    @Override
    public String deletePlant(int id) {
        PlantJDBCModel plantJDBCModel = PlantJDBCModel.findFirst("id = ?", id);
        if(plantJDBCModel.delete()) {
            return SUCESS_STATUS;
        }
        return generateFailedJSONOutput();
    }

    private String generateSuccessJsonOutput(String plantName, long id) {
        return new JSONStringer()
                .object()
                .key("status")
                .value(SUCESS_STATUS)
                .key("plant_name")
                .value(plantName)
                .key("id")
                .value(id)
                .endObject()
                .toString();
    }

    private PlantJDBCModel mapFromModel(Plant plant) {
        PlantJDBCModel plantJDBCModel = new PlantJDBCModel();
        plantJDBCModel.setName(plant.getName());
        plantJDBCModel.setType(plant.getType());
        plantJDBCModel.setImage(plant.getImageName());
        plantJDBCModel.setGardenId(plant.getGardenId());
        return plantJDBCModel;
    }

    private Plant mapToModel(PlantJDBCModel plantJDBCModel) {
        Plant plant = new Plant();
        plant.setId((Integer) plantJDBCModel.getId());
        plant.setName(plantJDBCModel.getName());
        plant.setImageName(plantJDBCModel.getImage());
        plant.setType(plantJDBCModel.getType());
        plant.setGardenId(plantJDBCModel.getGardenId());
        plant.setCreatedAt(plantJDBCModel.getCreatedAt());
        plant.setUpdatedAt(plantJDBCModel.getUpdatedAt());
        return plant;
    }

    private String generateFailedJSONOutput() {
        return new JSONStringer().object().key("status").value(FAILED_STATUS).endObject().toString();
    }
}
