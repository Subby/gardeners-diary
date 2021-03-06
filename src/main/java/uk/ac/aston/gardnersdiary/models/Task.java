package uk.ac.aston.gardnersdiary.models;

import java.time.LocalDate;

public class Task extends Model {

    private String name;
    private int taskTypeId;
    private int plantId;
    private boolean emailReminder;
    private boolean completed;
    private LocalDate dueDate;

    public Task() {

    }

    public Task(String name, int taskTypeId, int plantId, boolean emailReminder, LocalDate dueDate) {
        this.name = name;
        this.taskTypeId = taskTypeId;
        this.plantId = plantId;
        this.emailReminder = emailReminder;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(int taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public boolean isEmailReminder() {
        return emailReminder;
    }

    public void setEmailReminder(boolean emailReminder) {
        this.emailReminder = emailReminder;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
