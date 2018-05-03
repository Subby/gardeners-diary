package uk.ac.aston.gardnersdiary.services.database.tasktype;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.TaskType;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;

public class TaskTypeRetrievalJDBCTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
        fixture.givenDatabaseConnectionsAreSetup();
    }

    @Test
    public void addTaskType() {
        TaskType taskType = fixture.givenTaskTypeModelIsSetup();
        fixture.givenServiceIsSetup();
        String statusMessage = fixture.whenAddTaskTypeIsCalled(taskType);
        fixture.thenTaskTypeExistsInDatabase();
        fixture.thenCorrectStatusIsReturned(statusMessage);
    }

    @Test
    public void getAllTaskTypeData() {
        fixture.givenServiceIsSetup();
        int id = fixture.givenTestDataIsInDatabase();
        String returnedJSON = fixture.whenGetAllTaskTypeDataIsCalled();
        fixture.thenCorrectJSONIsReturned(returnedJSON, id);
    }

    @Test
    public void deleteTaskType() {
        fixture.givenServiceIsSetup();
        int id = fixture.givenTestDataIsInDatabase();
        String returnedJSON = fixture.whenDeleteTaskTypeIsCalled(id);
        fixture.thenCorrectJSONIsReturned(returnedJSON);
        fixture.thenTaskTypeDoesNotExistInDb();
    }

    @Test
    public void updateTaskType() {
        fixture.givenServiceIsSetup();
        int id = fixture.givenTestDataIsInDatabase();
        String returnedJSON = fixture.whenUpdateTaskTypeIsCalled(id, Fixture.UPDATED_NAME);
        fixture.thenCorrectJSONIsReturned(returnedJSON);
        fixture.thenTaskTypeHasBeenUpdatedInDb();
    }

    @After
    public void tearDown() {
        fixture.clearDownTestData();
        fixture.closeDatabaseConnections();
    }

    private class Fixture {
        private static final String NAME_COLUMN = "name";
        private static final String DEFAULT_TASK_NAME = "Test Task Type";
        private static final String UPDATED_NAME = "Updated Test Task Type";

        private Connection connection;
        private TaskTypeRetrieval taskTypeRetrieval;

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

        public void clearDownTestData() {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM task_type where " + NAME_COLUMN + " IN ('" + DEFAULT_TASK_NAME + "', '" + UPDATED_NAME + "')");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void givenServiceIsSetup() {
            taskTypeRetrieval = new TaskTypeRetrievalJDBC();
        }

        public void closeDatabaseConnections() {
            try {
                Base.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public TaskType givenTaskTypeModelIsSetup() {
            TaskType taskType = new TaskType();
            taskType.setName(DEFAULT_TASK_NAME);
            return taskType;
        }

        public String whenAddTaskTypeIsCalled(TaskType taskType) {
            return taskTypeRetrieval.addTaskType(taskType);
        }

        public void thenTaskTypeExistsInDatabase() {
            try {
                ResultSet result = findTaskTypeDetailsInDb();
                assertEquals(DEFAULT_TASK_NAME, result.getString(NAME_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public int givenTestDataIsInDatabase() {
            PreparedStatement statement = null;
            int id = 0;
            try {
                statement = connection.prepareStatement("INSERT INTO `task_type` (name, created_at, updated_at) VALUES (?, ?, ?)", new String[]{"id"});
                statement.setString(1, DEFAULT_TASK_NAME);
                statement.setDate(2, Date.valueOf("2017-11-01"));
                statement.setDate(3, Date.valueOf("2017-11-01"));
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

        private ResultSet findTaskTypeDetailsInDb() throws SQLException{
            PreparedStatement statement = connection.prepareStatement("SELECT " + NAME_COLUMN + " FROM task_type WHERE name = ? LIMIT 1");
            statement.setString(1, DEFAULT_TASK_NAME);
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }

        public void thenCorrectStatusIsReturned(String status) {
            assertEquals("{\"status\":\"success\"}", status);
        }

        public String whenGetAllTaskTypeDataIsCalled() {
            return taskTypeRetrieval.getAllTaskTypeData();
        }

        public void thenCorrectJSONIsReturned(String returnedJSON, int id) {
            assertEquals("[\n" +
                    "  {\n" +
                    "    \"created_at\":\"2017-11-01\",\n" +
                    "    \"id\":" + id +",\n" +
                    "    \"name\":\"Test Task Type\",\n" +
                    "    \"updated_at\":\"2017-11-01\"\n" +
                    "  }\n" +
                    "]", returnedJSON);
        }

        public String whenDeleteTaskTypeIsCalled(int id) {
            return taskTypeRetrieval.deleteTaskType(id);
        }

        public void thenTaskTypeDoesNotExistInDb() {
            boolean exists = findTaskTypeInDatabase();
            assertEquals(false, exists);
        }

        public void thenCorrectJSONIsReturned(String returnedJSON) {
            assertEquals("{\"status\":\"success\"}", returnedJSON);
        }

        private boolean findTaskTypeInDatabase() {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT " +  NAME_COLUMN + " FROM task_type where " + NAME_COLUMN + " = ?");
                statement.setString(1, DEFAULT_TASK_NAME);
                ResultSet result = statement.executeQuery();
                return result.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }

        public String whenUpdateTaskTypeIsCalled(int id, String newName) {
            return taskTypeRetrieval.updateTaskType(id, newName);
        }

        public void thenTaskTypeHasBeenUpdatedInDb() {
            assertEquals(true, hasTaskTypeUpdatedWithNewName());
        }

        private boolean hasTaskTypeUpdatedWithNewName() {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT " +  NAME_COLUMN + " FROM task_type where " + NAME_COLUMN + " = ?");
                statement.setString(1, UPDATED_NAME);
                ResultSet result = statement.executeQuery();
                result.next();
                return UPDATED_NAME.equals(result.getString(NAME_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}