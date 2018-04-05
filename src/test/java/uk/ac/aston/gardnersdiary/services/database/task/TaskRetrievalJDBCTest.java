package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.Task;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;

public class TaskRetrievalJDBCTest {
    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
        fixture.givenDatabaseConnectionsAreSetup();
    }

    @Test
    public void addTask() {
        Task task = fixture.givenTaskModelIsSetup();
        fixture.givenServiceIsSetup();
        String output = fixture.whenAddTaskIsCalled(task);
        fixture.thenTaskDataExistsInDb();
        fixture.thenCorrectOutputIsGenerated(output);
    }


    @After
    public void tearDown() {
        fixture.clearDownTestData();
        fixture.closeDatabaseConnections();
    }

    private class Fixture {
        private static final String NAME_COLUMN = "name";
        private static final String TASK_TYPE_ID_COLUMN = "task_type_id";
        private static final String PLANT_ID_COLUMN = "plant_id";
        private static final String EMAIL_REMINDER_COLUMN = "email_reminder";
        private static final String COMPLETED_COLUMN = "completed";

        private static final String TEST_TASK_NAME = "Test Task";

        private Connection connection;
        private TaskRetrieval taskRetrieval;

        public void givenDatabaseConnectionsAreSetup() {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gardenerdiarytest", "root", "");
            setupTestMySQLConnection();
        }

        private void setupTestMySQLConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/gardenerdiarytest?" + "user=root&password=");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void closeDatabaseConnections() {
            try {
                Base.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void clearDownTestData() {

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM plant where " + NAME_COLUMN + " = ?");
                statement.setString(1, TEST_TASK_NAME);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Task givenTaskModelIsSetup() {
            Task task = new Task();
            task.setName(TEST_TASK_NAME);
            task.setTaskTypeId(1);
            task.setPlantId(1);
            task.setEmailReminder(true);
            task.setCompleted(false);
            return task;
        }

        public void givenServiceIsSetup() {
            taskRetrieval = new TaskRetrievalJDBC();
        }

        public String whenAddTaskIsCalled(Task task) {
            return taskRetrieval.addTask(task);
        }

        public void thenTaskDataExistsInDb() {
            try {
                ResultSet result = findTaskDataInDb();
                assertEquals(TEST_TASK_NAME, result.getString(NAME_COLUMN));
                assertEquals(1, result.getInt(TASK_TYPE_ID_COLUMN));
                assertEquals(1, result.getInt(PLANT_ID_COLUMN));
                assertEquals(true, result.getBoolean(EMAIL_REMINDER_COLUMN));
                assertEquals(false, result.getBoolean(COMPLETED_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ResultSet findTaskDataInDb() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("SELECT " + NAME_COLUMN + ", " + TASK_TYPE_ID_COLUMN + ", " + PLANT_ID_COLUMN + ", " + EMAIL_REMINDER_COLUMN + ", " + COMPLETED_COLUMN + " FROM task WHERE name = ? LIMIT 1");
            statement.setString(1, TEST_TASK_NAME);
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }

        public void thenCorrectOutputIsGenerated(String output) {
            assertEquals("{\"status\":\"success\"}", output);
        }
    }
}