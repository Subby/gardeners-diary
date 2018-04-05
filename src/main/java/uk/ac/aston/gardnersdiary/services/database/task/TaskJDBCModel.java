package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.Model;

public class TaskJDBCModel extends Model {

    private static final String NAME_COLUMN = "name";
    private static final String TASK_TYPE_ID_COLUMN = "task_type_id";
    private static final String PLANT_ID_COLUMN = "plant_id";
    private static final String EMAIL_REMINDER_COLUMN = "email_reminder";
    private static final String COMPLETED_COLUMN = "completed";

    public String getName() {
        return getString(NAME_COLUMN);
    }

    public void setName(String name) {
        set(NAME_COLUMN, name);
    }

    public int getTaskTypeId() {
        return getInteger(TASK_TYPE_ID_COLUMN);
    }

    public void setTaskTypeId(int taskTypeId) {
        set(TASK_TYPE_ID_COLUMN, taskTypeId);
    }

    public int getPlantId() {
        return getInteger(PLANT_ID_COLUMN);
    }

    public void setPlantId(int plantId) {
        set(PLANT_ID_COLUMN, plantId);
    }

    public boolean isEmailReminder() {
        return getBoolean(EMAIL_REMINDER_COLUMN);
    }

    public void setIsEmailReminder(boolean isEmailReminder) {
        set(EMAIL_REMINDER_COLUMN, isEmailReminder);
    }

    public boolean isCompleted() {
        return getBoolean(COMPLETED_COLUMN);
    }

    public void setIsCompleted(boolean isCompleted) {
        set(COMPLETED_COLUMN, isCompleted);
    }




}
