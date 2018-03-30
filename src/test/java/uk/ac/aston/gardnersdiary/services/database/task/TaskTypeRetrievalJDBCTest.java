package uk.ac.aston.gardnersdiary.services.database.task;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.TaskType;
import uk.ac.aston.gardnersdiary.services.database.plant.PlantRetrievalJDBCTest;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

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
        fixture.whenAddTaskTypeIsCalled(taskType);
        fixture.thenTaskTypeExistsInDatabase();
    }

    @After
    public void tearDown() {
        fixture.clearDownTestData();
        fixture.closeDatabaseConnections();
    }

    private class Fixture {
        private static final String NAME_COLUMN = "name";
        private static final String DEFAULT_TASK_NAME = "Test Task Type";

        private Connection connection;
        private TaskTypeRetrieval taskTypeRetrieval;

        public void givenDatabaseConnectionsAreSetup() {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gardenerdiary", "root", "");
            setupTestMySQLConnection();
        }

        private void setupTestMySQLConnection() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/gardenerdiary?" + "user=root&password=");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void clearDownTestData() {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM task_type where " + NAME_COLUMN + " = ?");
                statement.setString(1, DEFAULT_TASK_NAME);
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

        public void whenAddTaskTypeIsCalled(TaskType taskType) {
            taskTypeRetrieval.addTaskType(taskType);
        }

        public void thenTaskTypeExistsInDatabase() {
            try {
                ResultSet result = findTaskTypeDetailsInDb();
                assertEquals(DEFAULT_TASK_NAME, result.getString(NAME_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private ResultSet findTaskTypeDetailsInDb() throws SQLException{
            PreparedStatement statement = connection.prepareStatement("SELECT " + NAME_COLUMN + " FROM task_type WHERE name = ? LIMIT 1");
            statement.setString(1, DEFAULT_TASK_NAME);
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }
    }

}