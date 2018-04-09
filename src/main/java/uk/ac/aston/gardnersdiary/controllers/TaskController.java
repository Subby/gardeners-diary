package uk.ac.aston.gardnersdiary.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import uk.ac.aston.gardnersdiary.models.Task;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantJDBCModel;
import uk.ac.aston.gardnersdiary.services.database.task.TaskJDBCModel;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrieval;
import uk.ac.aston.gardnersdiary.services.database.task.TaskRetrievalJDBC;
import uk.ac.aston.gardnersdiary.services.database.tasktype.TaskTypeJDBCModel;

import java.time.LocalDate;
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
        String name = request.queryParams("name");
        int taskType = Integer.valueOf(request.queryParams("taskTypeId"));
        int plantId = Integer.valueOf(request.queryParams("plantId"));
        boolean emailReminder = Boolean.valueOf(request.queryParams("emailReminder"));
        LocalDate dueDate = LocalDate.parse(request.queryParams("dueDate"));
        Task taskToAdd = buildTaskModel(name, taskType, plantId, emailReminder, dueDate);
        response.type("application/json");
        return taskRetrieval.addTask(taskToAdd);
    };

    public Route getTaskView = (Request request, Response response) -> {
        TaskRetrieval taskRetrieval = new TaskRetrievalJDBC();
        int taskId = Integer.valueOf((request.params(":taskId")));
        Task foundTask = taskRetrieval.getTaskById(taskId);
        String taskTypeName = findTaskTypeNameByTaskId(taskId);
        String plantName = findPlantNameByPlantId(taskId);
        Map<String, Object> attributes = new HashMap();
        if(foundTask != null) {
            attributes.put("title", "Manage task - " + foundTask.getName());
            attributes.put("task", foundTask);
            attributes.put("taskTypeName", taskTypeName);
            attributes.put("plantName", plantName);
        } else {
            attributes.put("title", "Manage task error");
            attributes.put("error", "task not found.");
        }
        return renderView(request, attributes, "task");
    };

    private String findTaskTypeNameByTaskId(int taskId) {
        TaskJDBCModel taskJDBCModel = TaskJDBCModel.findFirst("id = " + taskId);
        TaskTypeJDBCModel taskTypeJDBCModel = taskJDBCModel.parent(TaskTypeJDBCModel.class);
        return taskTypeJDBCModel.getName();
    }

    private String findPlantNameByPlantId(int plantId) {
        TaskJDBCModel taskJDBCModel = TaskJDBCModel.findFirst("id = " + plantId);
        PlantJDBCModel plantJDBCModel = taskJDBCModel.parent(PlantJDBCModel.class);
        return plantJDBCModel.getName();
    }

    private Task buildTaskModel(String name, int taskType, int plantId, boolean emailReminder, LocalDate dueDate) {
        return new Task(name, taskType, plantId, emailReminder, dueDate);
    }

}
