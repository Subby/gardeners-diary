package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 31/10/2017.
 */
public class TaskController extends Controller {

    private static TaskController taskController;

    private TaskController() {

    }

    public static TaskController getInstance() {
        if(taskController == null) {
            taskController = new TaskController();
        }
        return taskController;
    }

    public Route getIndex = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Manage Tasks");
        return renderView(request, attributes, "tasks");
    };

}
