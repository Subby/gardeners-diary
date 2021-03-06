package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.services.plantinformation.OpenFarmPlantInformationService;

/**
 * Created by Denver on 24/10/2017.
 */
public class PlantInformationController {

    private static PlantInformationController instance;
    private OpenFarmPlantInformationService openFarmPlantInformationService;

    private PlantInformationController() {
        openFarmPlantInformationService = new OpenFarmPlantInformationService();
    }

    public static PlantInformationController getInstance() {
        if(instance == null) {
            instance = new PlantInformationController();
        }
        return instance;
    }

    public Route getIndex = (Request request, Response response) -> {
        response.type("application/json");
        openFarmPlantInformationService = new OpenFarmPlantInformationService();
        return openFarmPlantInformationService.getPlantInformation(request.params(":plantname"));
    };

}
