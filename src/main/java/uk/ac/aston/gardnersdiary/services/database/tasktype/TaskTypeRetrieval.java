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
     * Deletes a {@link TaskType task type} from the database.
     * @param id the id of the task type to delete
     * @return the status of the operation, used in the UI code
     */
    String deleteTaskType(int id);

    /**
     * Updates a {@link TaskType task type} in the database.
     * @param id the id of the task tyope to update
     * @param newName the new name of the task type
     * @return the status of the operation, used in the UI code
     */
    String updateTaskType(int id, String newName);
}
