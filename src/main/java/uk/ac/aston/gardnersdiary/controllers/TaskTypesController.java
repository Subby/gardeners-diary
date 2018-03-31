package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 31/10/2017.
 */
public class TaskTypesController extends Controller {

    private static TaskTypesController taskTypesController;

    private TaskTypesController() {

    }

    public static TaskTypesController getInstance() {
        if(taskTypesController == null) {
            taskTypesController = new TaskTypesController();
        }
        return taskTypesController;
    }

    public Route getIndex = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Manage Task Types");
        return renderView(request, attributes, "tasktypes");
    };

}
