package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.Plant;
import uk.ac.aston.gardnersdiary.services.database.garden.GardenRetrieval;
import uk.ac.aston.gardnersdiary.services.database.garden.GardenRetrievalJDBC;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantRetrieval;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantRetrievalJDBC;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrieval;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrievalJDBC;

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

    public Route getPlantView = (Request request, Response response) -> {
      PlantRetrieval plantRetrieval = new PlantRetrievalJDBC();
      int plantId = Integer.valueOf((request.params(":plantid")));
      Plant foundPlant = plantRetrieval.getPlantById(plantId);
      Map<String, Object> attributes = new HashMap();
      if(foundPlant != null) {
          attributes.put("title", "Manage plant - " + foundPlant.getName());
          attributes.put("plant", foundPlant);
      } else {
          attributes.put("title", "Manage plant error");
          attributes.put("error", "Plant not found.");
      }
      return renderView(request, attributes, "plant");
    };

    public Route getPlantData = (Request request, Response response) -> {
        PlantRetrieval plantRetrieval = new PlantRetrievalJDBC();
        return plantRetrieval.getAllPlantData();
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

    public Route postUpdatePlant = (Request request, Response response) -> {
        PlantRetrieval plantRetrieval = new PlantRetrievalJDBC();
        GardenRetrieval gardenRetrieval = new GardenRetrievalJDBC();
        int id = Integer.valueOf(request.queryParams("id"));
        String name = request.queryParams("name");
        String type = request.queryParams("type");
        response.type("application/json");
        gardenRetrieval.updatePlantNameInJSON(id, name);
        return plantRetrieval.updatePlantDetails(id, name, type);
    };

    public Route deletePlant = (Request request, Response response) -> {
        PlantRetrieval plantRetrieval = new PlantRetrievalJDBC();
        GardenRetrieval gardenRetrieval = new GardenRetrievalJDBC();
        int id = Integer.valueOf((request.params(":plantid")));
        gardenRetrieval.deletePlantInJson(id);
        return plantRetrieval.deletePlant(id);
    };

    public Route getTasks = (Request request, Response response) -> {
        TaskRetrieval taskRetrieval = new TaskRetrievalJDBC();
        int id = Integer.valueOf((request.params(":plantid")));
        taskRetrieval.getTasksForGivenPlant(id);
        response.type("application/json");
        return taskRetrieval.getTasksForGivenPlant(id);
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
