package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

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
        attributes.put("title", "Home");
        return renderView(request, attributes, "index");
    };

}
