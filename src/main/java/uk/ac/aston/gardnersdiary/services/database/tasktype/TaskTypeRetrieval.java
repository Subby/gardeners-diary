package uk.ac.aston.gardnersdiary.services.database.tasktype;

import uk.ac.aston.gardnersdiary.models.TaskType;

public interface TaskTypeRetrieval {

    /**
     * Adds a {@link TaskType task type} to the database.
     * @param taskType the task type to add
     */
    void addTaskType(TaskType taskType);

}
