package uk.ac.aston.gardnersdiary.services.database.task;

import uk.ac.aston.gardnersdiary.models.Task;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

    /**
     * Updates a {@Task task} in the system given the id.
     * @param taskId the id to update by
     * @param newName the new task name
     * @param newTaskTypeId the new task type id
     * @param newDueDate the new date
     * @return the status of the operation, used in the UI
     */
    String updateTask(int taskId, String newName, int newTaskTypeId, boolean emailReminder, LocalDate newDueDate);

    /**
     * Deletes a {@Task task} from the database.
     * @param taskId the id to delete using
     * @return the status of the operation, used in the UI
     */
    String deleteTask(int taskId);

    /**
     * Marks a {@Task task} as complete in the database.
     * @param taskId the task id to mark complete
     * @return the status of the operation, used in the UI
     */
    String completeTask(int taskId);

    /**
     * Marks a {@Task task} as incomplete in the database.
     * @param TaskId
     * @return the status of the operation, used in the UI
     */
    String incompleteTask(int TaskId);

    /**
     * Returns the recently added tasks for the front page
     * @return the recently added tasks
     */
    List<Task> getFrontPagePlants();
}
