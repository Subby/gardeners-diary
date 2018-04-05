package uk.ac.aston.gardnersdiary.services.database.task;

import uk.ac.aston.gardnersdiary.models.Task;

public interface TaskRetrieval {

    /**
     * Adds a {@Task task} into the database.
     * @param taskToAdd the task to add to the database
     * @return the status of the operation, used in the UI
     */
    String addTask(Task taskToAdd);

}
