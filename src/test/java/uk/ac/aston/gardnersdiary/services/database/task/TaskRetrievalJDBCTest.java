package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.Task;

import java.sql.*;
import java.time.LocalDate;

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

    @Test
    public void getTasksForGivenPlant() {
        fixture.givenServiceIsSetup();
        int id = fixture.givenTestDataIsInDatabase();
        String JSONOutput = fixture.whenGetTasksForGivenPlantIsCalled();
        fixture.thenCorrectJSONOutputIsReturned(JSONOutput, id);
    }

    @Test
    public void getTaskById() {
        fixture.givenServiceIsSetup();
        int taskId = fixture.givenTestDataIsInDatabase();
        Task task = fixture.whenGetTaskByIdIsCalled(taskId);
        fixture.thenCorrectModelIsBuilt(taskId, task);
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
        private static final String DUE_DATE_COLUMN = "due_date";

        private static final String TEST_TASK_NAME = "Test Task";
        private static final int TEST_TASK_PLANT_ID = 2;
        private final LocalDate TEST_DUE_DATE = LocalDate.of(2018, 12, 26);

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
                PreparedStatement statement = connection.prepareStatement("DELETE FROM task where " + NAME_COLUMN + " = ?");
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
            task.setDueDate(TEST_DUE_DATE);
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
                assertEquals(Date.valueOf(TEST_DUE_DATE), result.getDate(DUE_DATE_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ResultSet findTaskDataInDb() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("SELECT " + NAME_COLUMN + ", " + TASK_TYPE_ID_COLUMN + ", " + PLANT_ID_COLUMN + ", " + EMAIL_REMINDER_COLUMN + ", " + COMPLETED_COLUMN + "," + DUE_DATE_COLUMN + " FROM task WHERE name = ? LIMIT 1");
            statement.setString(1, TEST_TASK_NAME);
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }

        public void thenCorrectOutputIsGenerated(String output) {
            assertEquals("{\"status\":\"success\"}", output);
        }

        public int givenTestDataIsInDatabase() {
            PreparedStatement statement = null;
            int id = 0;
            try {
                statement = connection.prepareStatement("INSERT INTO `task` (name, task_type_id, plant_id, email_reminder, completed, due_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"id"});
                statement.setString(1, TEST_TASK_NAME);
                statement.setInt(2, 1);
                statement.setInt(3, TEST_TASK_PLANT_ID);
                statement.setBoolean(4, true);
                statement.setBoolean(5, false);
                statement.setDate(6,  Date.valueOf(TEST_DUE_DATE));
                statement.setDate(7, Date.valueOf("2017-11-01"));
                statement.setDate(8, Date.valueOf("2017-11-01"));
                id = statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    id = result.getByte(1);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return id;
        }

        public String whenGetTasksForGivenPlantIsCalled() {
            return taskRetrieval.getTasksForGivenPlant(TEST_TASK_PLANT_ID);
        }

        public void thenCorrectJSONOutputIsReturned(String jsonOutput, int id) {
            assertEquals("[\n" +
                    "  {\n" +
                    "    \"id\":" + id + ",\n" +
                    "    \"name\":\"Test Task\",\n" +
                    "    \"task_type_id\":1,\n" +
                    "    \"completed\":false,\n" +
                    "    \"due_date\":\"2018-12-26\",\n" +
                    "    \"created_at\":\"2017-11-01\",\n" +
                    "    \"updated_at\":\"2017-11-01\"\n" +
                    "  }\n" +
                    "]", jsonOutput);
        }

        public Task whenGetTaskByIdIsCalled(int taskId) {
            return taskRetrieval.getTaskById(taskId);
        }

        public void thenCorrectModelIsBuilt(int taskId, Task task) {
            assertEquals(taskId, task.getId());
            assertEquals(TEST_TASK_NAME, task.getName());
            assertEquals(1, task.getTaskTypeId());
            assertEquals(TEST_TASK_PLANT_ID, task.getPlantId());
            assertEquals(true, task.isEmailReminder());
            assertEquals(false, task.isCompleted());
            assertEquals(TEST_DUE_DATE, task.getDueDate());
        }

    }
}