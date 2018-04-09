package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.Task;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrieval;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrievalJDBC;

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

    public Route postAdd = (Request request, Response response) -> {
        TaskRetrieval taskRetrieval = new TaskRetrievalJDBC();
        String name = request.queryParams("taskName");
        int taskType = Integer.valueOf(request.queryParams("taskTypeId"));
        int plantId = Integer.valueOf(request.queryParams("plantId"));
        boolean emailReminder = Boolean.valueOf(request.queryParams("emailReminder"));
        Task taskToAdd = buildTaskModel(name, taskType, plantId, emailReminder);
        response.type("application/json");
        return taskRetrieval.addTask(taskToAdd);
    };

    private Task buildTaskModel(String name, int taskType, int plantId, boolean emailReminder) {
        return new Task(name, taskType, plantId, emailReminder);
    }

}
