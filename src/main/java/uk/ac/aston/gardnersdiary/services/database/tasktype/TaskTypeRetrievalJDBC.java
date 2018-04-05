package uk.ac.aston.gardnersdiary.services.database.tasktype;

import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.models.TaskType;

public class TaskTypeRetrievalJDBC implements TaskTypeRetrieval {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";

    @Override
    public String addTaskType(TaskType taskType) {
        TaskTypeJDBCModel taskTypeJDBCModel = new TaskTypeJDBCModel();
        taskTypeJDBCModel.setName(taskType.getName());
        if(taskTypeJDBCModel.saveIt()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String getAllTaskTypeData() {
        return TaskTypeJDBCModel.findAll().toJson(true);
    }

    @Override
    public String deleteTaskType(int id) {
        TaskTypeJDBCModel taskTypeJDBCModel = TaskTypeJDBCModel.findFirst("id = ?", id);
        if(taskTypeJDBCModel.delete()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String updateTaskType(int id, String newName) {
        TaskTypeJDBCModel taskTypeJDBCModel = TaskTypeJDBCModel.findFirst("id = ?", id);
        taskTypeJDBCModel.set("name", newName);
        if(taskTypeJDBCModel.save()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    private String generateSuccessJSONOutput() {
        return new JSONStringer().object().key("status").value(SUCCESS_STATUS).endObject().toString();
    }

    private String generateFailedJSONOutput() {
        return new JSONStringer().object().key("status").value(FAILED_STATUS).endObject().toString();
    }
}
