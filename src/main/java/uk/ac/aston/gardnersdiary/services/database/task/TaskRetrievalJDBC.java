package uk.ac.aston.gardnersdiary.services.database.task;

import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.models.Task;

public class TaskRetrievalJDBC implements TaskRetrieval {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";

    @Override
    public String addTask(Task taskToAdd) {
        TaskJDBCModel taskJDBCModel = mapFromTaskModel(taskToAdd);
        if(taskJDBCModel.save()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    private TaskJDBCModel mapFromTaskModel(Task task) {
        TaskJDBCModel taskJDBCModel = new TaskJDBCModel();
        taskJDBCModel.setName(task.getName());
        taskJDBCModel.setTaskTypeId(task.getTaskTypeId());
        taskJDBCModel.setPlantId(task.getPlantId());
        taskJDBCModel.setIsEmailReminder(task.isEmailReminder());
        taskJDBCModel.setIsCompleted(task.isCompleted());
        return taskJDBCModel;
    }


    private String generateSuccessJSONOutput() {
        return new JSONStringer().object().key("status").value(SUCCESS_STATUS).endObject().toString();
    }

    private String generateFailedJSONOutput() {
        return new JSONStringer().object().key("status").value(FAILED_STATUS).endObject().toString();
    }

}
