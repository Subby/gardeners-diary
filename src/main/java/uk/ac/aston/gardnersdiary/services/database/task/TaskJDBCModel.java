package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Table("task")
public class TaskJDBCModel extends Model {

    private static final String NAME_COLUMN = "name";
    private static final String TASK_TYPE_ID_COLUMN = "task_type_id";
    private static final String PLANT_ID_COLUMN = "plant_id";
    private static final String EMAIL_REMINDER_COLUMN = "email_reminder";
    private static final String COMPLETED_COLUMN = "completed";
    private static final String DUE_DATE_COLUMN = "due_date";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

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


    public Date getUpdatedAt() {
        return getDate(CREATED_AT_COLUMN);
    }

    public Date getCreatedAt() {
        return getDate(UPDATED_AT_COLUMN);
    }

    public void setDueDate(LocalDate date) {
        setDate(DUE_DATE_COLUMN, convertLocalDateToDate(date));
    }

    public LocalDate getDueDate() {
        return convertDateToLocalDate(getDate(DUE_DATE_COLUMN));
    }

    //Source: https://stackoverflow.com/questions/29168494/how-to-convert-localdate-to-sql-date-java
    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    //Source: https://stackoverflow.com/questions/29168494/how-to-convert-localdate-to-sql-date-java
    private LocalDate convertDateToLocalDate(Date date) {
        return date.toLocalDate();
    }


}
