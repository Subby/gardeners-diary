package uk.ac.aston.gardnersdiary.services.database.tasktype;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("task_type")
public class TaskTypeJDBC extends Model {

    private static String NAME_COLUMN = "name";

    public String getName() {
        return getString(NAME_COLUMN);
    }

    public void setName(String name) {
        set(NAME_COLUMN, name);
    }

}
