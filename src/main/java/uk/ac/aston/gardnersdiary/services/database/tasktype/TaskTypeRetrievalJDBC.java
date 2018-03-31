package uk.ac.aston.gardnersdiary.services.database.tasktype;

import uk.ac.aston.gardnersdiary.models.TaskType;

public class TaskTypeRetrievalJDBC implements TaskTypeRetrieval {

    @Override
    public void addTaskType(TaskType taskType) {
        TaskTypeJDBC taskTypeJDBC = new TaskTypeJDBC();
        taskTypeJDBC.setName(taskType.getName());
        taskTypeJDBC.saveIt();
    }
}
