package uk.ac.aston.gardnersdiary.controllers;

import spark.Response;
import spark.Route;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 15/10/2017.
 */
public class GardenController extends Controller {

    private static GardenController instance;

    public static GardenController getInstance() {
        if(instance == null) {
            instance = new GardenController();
        }
        return instance;
    }

    public Route getManageGarden = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Manage Garden");
        return renderView(request, attributes, "garden");
    };

    public Route getImportGarden = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Import Garden");
        return renderView(request, attributes, "importGarden");
    };

}
