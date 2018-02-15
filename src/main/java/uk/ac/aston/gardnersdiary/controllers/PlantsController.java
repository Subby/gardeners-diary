package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.Plant;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantRetrieval;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantRetrievalJDBC;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 31/10/2017.
 */
public class PlantsController extends Controller {

    private static PlantsController plantsController;

    private PlantsController() {

    }

    public static PlantsController getInstance() {
        if(plantsController == null) {
            plantsController = new PlantsController();
        }
        return plantsController;
    }

    public Route getIndex = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Manage Plants");
        return renderView(request, attributes, "plants");
    };

    public Route postAddPlant = (Request request, Response response) -> {
        PlantRetrieval plantRetrieval = new PlantRetrievalJDBC();
        String name = request.queryParams("name");
        String type = request.queryParams("type");
        int gardenId = Integer.valueOf(request.queryParams("gardenId"));
        Plant plant = generatePlantModel(name, type, gardenId);
        response.type("application/json");
        return plantRetrieval.addPlant(plant);
    };

    private Plant generatePlantModel(String name, String type, int gardenId) {
        Plant plant = new Plant();
        plant.setName(name);
        plant.setImageName("");
        plant.setType(type);
        plant.setGardenId(gardenId);
        return plant;
    }

}
