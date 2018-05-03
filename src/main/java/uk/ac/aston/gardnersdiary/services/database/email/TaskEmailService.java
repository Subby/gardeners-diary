package uk.ac.aston.gardnersdiary.services.database.email;

import uk.ac.aston.gardnersdiary.models.Task;

import java.util.Date;

public interface TaskEmailService {

    /**
     * Sends an email reminder for a task.
     * @param task the task to send an email for
     * @param plantName the plant name associated with this task
     * @param taskTypeName the task type name associated with this task
     */
    void sendEmail(Task task, String taskTypeName, String plantName);
}
