package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.models.Task;
import uk.ac.aston.gardnersdiary.services.database.email.TaskEmailService;
import uk.ac.aston.gardnersdiary.services.database.email.MailGunTaskEmailService;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantJDBCModel;
import uk.ac.aston.gardnersdiary.services.database.tasktype.TaskTypeJDBCModel;

import java.time.LocalDate;

@BelongsToParents({
        @BelongsTo(foreignKeyName="task_type_id",parent=TaskTypeJDBCModel.class),
        @BelongsTo(foreignKeyName="plant_id",parent=PlantJDBCModel.class)
})
public class TaskRetrievalJDBC implements TaskRetrieval {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";

    @Override
    public String addTask(Task taskToAdd) {
        TaskJDBCModel taskJDBCModel = mapFromTaskModel(taskToAdd);
        if(taskJDBCModel.save()) {
            if(taskToAdd.isEmailReminder()) {
                //If email reminders are set, send email reminders
                PlantJDBCModel plantJDBCModel = taskJDBCModel.parent(PlantJDBCModel.class);
                TaskTypeJDBCModel taskTypeJDBCModel = taskJDBCModel.parent(TaskTypeJDBCModel.class);
                TaskEmailService taskEmailService = new MailGunTaskEmailService();
                taskEmailService.sendEmail(taskToAdd, plantJDBCModel.getName(), taskTypeJDBCModel.getName());
            }
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String getTasksForGivenPlant(int plantId) {
        String returnedJson = TaskJDBCModel.find("plant_id = " + plantId).toJson(true, "id", "name", "task_type_id", "completed", "due_date", "created_at", "updated_at");
        if(returnedJson == null) {
            return "[]";
        }
        return returnedJson;
    }

    @Override
    public Task getTaskById(int taskId) {
        TaskJDBCModel taskJDBCModel = TaskJDBCModel.findFirst("id = " + taskId);
        return mapToTaskModel(taskJDBCModel);
    }

    @Override
    public String updateTask(int taskId, String newName, int newTaskTypeId, boolean emailReminder, LocalDate newDueDate) {
        TaskJDBCModel taskTypeJDBCModel = TaskJDBCModel.findFirst("id = ?", taskId);
        taskTypeJDBCModel.setName(newName);
        taskTypeJDBCModel.setTaskTypeId(newTaskTypeId);
        taskTypeJDBCModel.setDueDate(newDueDate);
        if(taskTypeJDBCModel.save()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String deleteTask(int taskId) {
        TaskJDBCModel taskTypeJDBCModel = TaskJDBCModel.findFirst("id = ?", taskId);
        if(taskTypeJDBCModel.delete()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String completeTask(int taskId) {
        TaskJDBCModel taskTypeJDBCModel = TaskJDBCModel.findFirst("id = ?", taskId);
        taskTypeJDBCModel.setIsCompleted(true);
        if(taskTypeJDBCModel.save()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String incompleteTask(int taskId) {
        TaskJDBCModel taskTypeJDBCModel = TaskJDBCModel.findFirst("id = ?", taskId);
        taskTypeJDBCModel.setIsCompleted(false);
        if(taskTypeJDBCModel.save()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    private Task mapToTaskModel(TaskJDBCModel taskJDBCModel) {
        Task task = new Task();
        task.setId((Integer) (taskJDBCModel.getId()));
        task.setName(taskJDBCModel.getName());
        task.setTaskTypeId(taskJDBCModel.getTaskTypeId());
        task.setPlantId(taskJDBCModel.getPlantId());
        task.setCompleted(taskJDBCModel.isCompleted());
        task.setEmailReminder(taskJDBCModel.isEmailReminder());
        task.setDueDate(taskJDBCModel.getDueDate());
        task.setCreatedAt(taskJDBCModel.getCreatedAt());
        task.setUpdatedAt(taskJDBCModel.getUpdatedAt());
        return task;
    }

    private TaskJDBCModel mapFromTaskModel(Task task) {
        TaskJDBCModel taskJDBCModel = new TaskJDBCModel();
        taskJDBCModel.setName(task.getName());
        taskJDBCModel.setTaskTypeId(task.getTaskTypeId());
        taskJDBCModel.setPlantId(task.getPlantId());
        taskJDBCModel.setIsEmailReminder(task.isEmailReminder());
        taskJDBCModel.setIsCompleted(task.isCompleted());
        taskJDBCModel.setDueDate(task.getDueDate());
        return taskJDBCModel;
    }


    private String generateSuccessJSONOutput() {
        return new JSONStringer().object().key("status").value(SUCCESS_STATUS).endObject().toString();
    }

    private String generateFailedJSONOutput() {
        return new JSONStringer().object().key("status").value(FAILED_STATUS).endObject().toString();
    }

}
