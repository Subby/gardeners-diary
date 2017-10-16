package uk.ac.aston.gardnersdiary.controllers;

import spark.Response;
import spark.Route;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 15/10/2017.
 */
public class ImportGardenController extends Controller {

    private static ImportGardenController instance;

    public static ImportGardenController getInstance() {
        if(instance == null) {
            instance = new ImportGardenController();
        }
        return instance;
    }

    public Route getIndex = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Import Garden");
        return renderView(request, attributes, "importGarden");
    };

}
