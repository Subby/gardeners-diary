package uk.ac.aston.gardnersdiary.models;

/**
 * Used to represent Task Type information.
 * Adapted from: http://javalite.io/documentation
 */
public class TaskType extends Model{

    private String name;

    public TaskType() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
