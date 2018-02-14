package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.Garden;
import uk.ac.aston.gardnersdiary.services.database.GardenRetrieval;
import uk.ac.aston.gardnersdiary.services.database.GardenRetrievalJDBC;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 31/10/2017.
 */
public class IndexController extends Controller {

    private static IndexController indexController;

    private IndexController() {
    }

    public static IndexController getInstance() {
        if(indexController == null) {
            indexController = new IndexController();
        }
        return indexController;
    }

    public Route getIndex = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        GardenRetrieval gardenRetrieval = new GardenRetrievalJDBC();
        Garden garden = gardenRetrieval.getGarden();
        attributes.put("title", "Home");
        attributes.put("garden", garden);
        return renderView(request, attributes, "index");
    };

}
