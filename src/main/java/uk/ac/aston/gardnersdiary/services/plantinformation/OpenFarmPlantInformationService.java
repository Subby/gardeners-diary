package uk.ac.aston.gardnersdiary.services.plantinformation;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.services.rest.RestClient;
import uk.ac.aston.gardnersdiary.services.rest.UniRestClient;

/**
 * Created by Denver on 22/10/2017.
 */
public class OpenFarmPlantInformationService implements PlantInformationService {

    private static final String OPEN_FARM_API_URL = "http://openfarm.cc/api/v1/";
    private static final String STATUS_OKAY = "success";
    private static final String STATUS_NOT_FOUND = "error";

    private RestClient client;
    private String status;


    public OpenFarmPlantInformationService() {
        client = new UniRestClient();
    }

    @Override
    public String getPlantInformation(String plantName) {
        PlantInformation plant = extractPlantInformation(plantName);

        return convertPlantToJson(plant);
    }

    private PlantInformation extractPlantInformation(String plantName) {
        final HttpResponse<JsonNode> plantData = (HttpResponse) client.get(OPEN_FARM_API_URL + "crops?filter=" + plantName);
        PlantInformation plantInformation = null;
        JSONObject jsonData = plantData.getBody().getObject();
        JSONArray jsonDataArrayElement =  jsonData.getJSONArray("data");
        try {
            JSONObject jsonDataObjectElement = jsonDataArrayElement.getJSONObject(0);
            JSONObject jsonDataAttributesElement = jsonDataObjectElement.getJSONObject("attributes");
            String plantDescription = jsonDataAttributesElement.getString("description");
            String plantSunRequirements = jsonDataAttributesElement.getString("sun_requirements");
            String plantSowingMethod = jsonDataAttributesElement.getString("sowing_method");
            int plantRowSpacing = jsonDataAttributesElement.getInt("row_spacing");
            int plantHeight = jsonDataAttributesElement.getInt("height");
            String image = jsonDataAttributesElement.getString("main_image_path");
            plantInformation = new PlantInformation(plantDescription, plantSunRequirements, plantSowingMethod, plantRowSpacing, plantHeight, image);
            status = STATUS_OKAY;
        } catch (JSONException exception) {
            status = STATUS_NOT_FOUND;
        }
        return plantInformation;
    }

    private String convertPlantToJson(PlantInformation plantToConvert) {
        String jsonOutput = null;
        if(plantToConvert == null) {
            jsonOutput = new JSONStringer()
                    .object()
                    .key("status")
                    .value(status)
                    .endObject()
                    .toString();

        } else {
            jsonOutput = new JSONStringer()
                    .object()
                    .key("description")
                    .value(plantToConvert.getDescription())
                    .key("sun_requirements")
                    .value(plantToConvert.getSunRequirements())
                    .key("sowing_method")
                    .value(plantToConvert.getSowingMethod())
                    .key("row_spacing")
                    .value(plantToConvert.getRowSpacing())
                    .key("height")
                    .value(plantToConvert.getHeight())
                    .key("image")
                    .value(plantToConvert.getImage())
                    .key("status")
                    .value(status)
                    .endObject()
                    .toString();
        }
        return jsonOutput;
    }
}
