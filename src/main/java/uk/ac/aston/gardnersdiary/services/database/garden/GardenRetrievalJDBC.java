package uk.ac.aston.gardnersdiary.services.database.garden;

import com.google.gson.*;
import org.json.JSONObject;
import uk.ac.aston.gardnersdiary.models.Garden;

import java.util.List;

/**
 * Created by Denver on 16/11/2017.
 * Adapted from: http://javalite.io/documentation
 */
public class GardenRetrievalJDBC implements GardenRetrieval {

    private static final String REGION_JSON_CHILDREN_ARRAY = "children";
    private static final String REGION_JSON_ATTRS_OBJECT = "attrs";
    private static final String REGION_JSON_PLANTID_ELEMENT = "plantId";
    private static final String REGION_JSON_REGIONNAME_ELEMENT = "regionName";

    @Override
    public Garden getGardenById(int id) {
        Garden gardenModel = null;
        GardenJDBCModel gardenJDBCModel = GardenJDBCModel.findFirst("id = ?", id);
        //If a matching record with given id is found, then map information from the database to model
        if(gardenJDBCModel != null) {
            gardenModel = mapToGardenModel(gardenJDBCModel);
        }
        return gardenModel;
    }

    @Override
    public Garden getGarden() {
        Garden gardenModel = null;
        GardenJDBCModel gardenJDBCModel = GardenJDBCModel.findFirst("id = (select max(id) from garden)");
        if(gardenJDBCModel != null) {
            gardenModel = mapToGardenModel(gardenJDBCModel);
        }
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

    @Override
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

    @Override
    public void updateGardenJSON(String JSON) {
        GardenJDBCModel gardenJDBCModel = GardenJDBCModel.findFirst("id = (select max(id) from garden)");
        gardenJDBCModel.setRegionJson(JSON);
        gardenJDBCModel.saveIt();
    }

    @Override
    public void updatePlantNameInJSON(int id, String newName) {
        String existingRegionJSON = getExistingRegionJSON();
        JsonObject modifiedRootObj = replaceExistingNameWithNewName(id, newName, existingRegionJSON);
        String newJson = modifiedRootObj.toString();
        updateGardenJSON(newJson);
    }

    @Override
    public void deletePlantInJson(int id) {
        String existingRegionJSON = getExistingRegionJSON();
        JsonObject modifiedRootObj = findAndDeletePlantInJson(id, existingRegionJSON);
        String newJson = modifiedRootObj.toString();
        updateGardenJSON(newJson);
    }

    //Adapted from: https://stackoverflow.com/a/37427484
    private JsonObject replaceExistingNameWithNewName(int id, String newName, String existingRegionJSON) {
        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(existingRegionJSON).getAsJsonObject();
        JsonArray childrenArray = rootObj.getAsJsonArray(REGION_JSON_CHILDREN_ARRAY);
        //Search through the children array
        for(JsonElement currentElement : childrenArray) {
            JsonObject currentElementAsObject = currentElement.getAsJsonObject();
            JsonObject currentAttrsObject = currentElementAsObject.get(REGION_JSON_ATTRS_OBJECT).getAsJsonObject();
            //Make sure the children array has the appropriate element
            if(currentAttrsObject.has(REGION_JSON_PLANTID_ELEMENT)) {
                int plantIdElement = currentAttrsObject.get(REGION_JSON_PLANTID_ELEMENT).getAsInt();
                if(plantIdElement == id) {
                    //Overwrite the element value with the new name value
                    currentAttrsObject.addProperty(REGION_JSON_REGIONNAME_ELEMENT, newName);
                    break;
                }
            }
        }
        return rootObj;
    }

    //Adapted from: https://stackoverflow.com/a/37427484
    private JsonObject findAndDeletePlantInJson(int id, String existingRegionJSON) {
        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(existingRegionJSON).getAsJsonObject();
        JsonArray childrenArray = rootObj.getAsJsonArray(REGION_JSON_CHILDREN_ARRAY);
        //Loop through the children array
        for(int i=0; i < childrenArray.size(); i++) {
            JsonObject currentElementAsObject = (JsonObject) childrenArray.get(i);
            JsonObject currentAttrsObject = currentElementAsObject.get(REGION_JSON_ATTRS_OBJECT).getAsJsonObject();
            //Make sure the children array has the appropriate element
            if(currentAttrsObject.has(REGION_JSON_PLANTID_ELEMENT)) {
                int plantIdElement = currentAttrsObject.get(REGION_JSON_PLANTID_ELEMENT).getAsInt();
                if(plantIdElement == id) {
                    //Remove the required array element
                    childrenArray.remove(i);
                }
            }
        }
        return rootObj;
    }

    private String getExistingRegionJSON() {
        Garden latestGarden = getGarden();
        return latestGarden.getRegionJson();
    }

}
