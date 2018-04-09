package uk.ac.aston.gardnersdiary.services.database.task;

import uk.ac.aston.gardnersdiary.models.Task;

public interface TaskRetrieval {

    /**
     * Adds a {@Task task} into the database.
     * @param taskToAdd the task to add to the database
     * @return the status of the operation, used in the UI
     */
    String addTask(Task taskToAdd);

    /**
     * Gets {@Task tasks} from the database given a associated plant id.
     * @param plantId the plant id to retrieve the task using
     * @return the matching data from the data
     */
    String getTasksForGivenPlant(int plantId);

    /**
     * Gets a {@Task task} given the id.
     * @param taskId the task id to retrieve the task by
     * @return the task
     */
    Task getTaskById(int taskId);

}
