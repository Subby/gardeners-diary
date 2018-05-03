package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.TaskType;
import uk.ac.aston.gardnersdiary.services.database.tasktype.TaskTypeRetrieval;
import uk.ac.aston.gardnersdiary.services.database.tasktype.TaskTypeRetrievalJDBC;

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

    public Route postAdd = (Request request, Response response) -> {
        TaskTypeRetrieval taskTypeRetrieval = new TaskTypeRetrievalJDBC();
        String name = request.queryParams("name");
        TaskType taskType = generateTaskType(name);
        response.type("application/json");
        return taskTypeRetrieval.addTaskType(taskType);
    };

    private TaskType generateTaskType(String name) {
        TaskType taskType = new TaskType();
        taskType.setName(name);
        return taskType;
    }

    public Route getTaskTypes = (Request request, Response response) -> {
        TaskTypeRetrieval taskTypeRetrieval = new TaskTypeRetrievalJDBC();
        response.type("application/json");
        return taskTypeRetrieval.getAllTaskTypeData();
    };

    public Route deleteTaskType = (Request request, Response response) -> {
        TaskTypeRetrieval taskTypeRetrieval = new TaskTypeRetrievalJDBC();
        int id = Integer.valueOf((request.params(":tasktypeid")));
        response.type("application/json");
        return taskTypeRetrieval.deleteTaskType(id);
    };

    public Route updateTaskType = (Request request, Response response) -> {
        TaskTypeRetrieval taskTypeRetrieval = new TaskTypeRetrievalJDBC();
        int id = Integer.valueOf(request.queryParams("id"));
        String name = request.queryParams("name");
        response.type("application/json");
        return taskTypeRetrieval.updateTaskType(id, name);
    };


}
