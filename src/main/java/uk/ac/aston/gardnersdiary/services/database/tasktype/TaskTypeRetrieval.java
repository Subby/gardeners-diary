package uk.ac.aston.gardnersdiary.services.database.tasktype;

import uk.ac.aston.gardnersdiary.models.TaskType;

public interface TaskTypeRetrieval {

    /**
     * Adds a {@link TaskType task type} to the database.
     * @param taskType the task type to add
     * @return the status of the operation, used in the UI code     *
     */
    String addTaskType(TaskType taskType);

    /**
     * Returns all {@link TaskType task type} data from the database.
     * Used in the UI to populate task type table.
     * @return all the task type data in JSON form.
     */
    String getAllTaskTypeData();

    /**
     * Deletes a {@link TaskType task type} from  the database.
     * @param id the id of the task type to delete
     * @return the status of the operation, used in the UI code
     */
    String deleteTaskType(int id);
}
