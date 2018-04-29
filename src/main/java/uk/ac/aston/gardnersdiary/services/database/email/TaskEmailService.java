package uk.ac.aston.gardnersdiary.services.database.email;

import uk.ac.aston.gardnersdiary.models.Task;

import java.util.Date;

public interface TaskEmailService {

    /**
     * Sends an email along
     * @param task
     * @param plantName
     * @param taskTypeName
     */
    void sendEmail(Task task, String taskTypeName, String plantName);
}
