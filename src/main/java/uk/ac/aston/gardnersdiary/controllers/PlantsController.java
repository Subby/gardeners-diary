package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

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

}
