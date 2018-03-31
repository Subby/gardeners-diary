package uk.ac.aston.gardnersdiary.services.database.tasktype;

import org.json.JSONStringer;
import uk.ac.aston.gardnersdiary.models.TaskType;

public class TaskTypeRetrievalJDBC implements TaskTypeRetrieval {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAILED_STATUS = "failed";

    @Override
    public String addTaskType(TaskType taskType) {
        TaskTypeJDBC taskTypeJDBC = new TaskTypeJDBC();
        taskTypeJDBC.setName(taskType.getName());
        if(taskTypeJDBC.saveIt()) {
            return generateSuccessJSONOutput();
        }
        return generateFailedJSONOutput();
    }

    @Override
    public String getAllTaskTypeData() {
        return TaskTypeJDBC.findAll().toJson(true);
    }

    private String generateSuccessJSONOutput() {
        return new JSONStringer().object().key("status").value(SUCCESS_STATUS).endObject().toString();
    }

    private String generateFailedJSONOutput() {
        return new JSONStringer().object().key("status").value(FAILED_STATUS).endObject().toString();
    }
}
