package uk.ac.aston.gardnersdiary.services.database.plant;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.Plant;

import java.sql.*;

import static junit.framework.TestCase.assertEquals;

public class PlantRetrievalJDBCTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
        fixture.givenDatabaseConnectionsAreSetup();
    }

    @Test
    public void addPlant() {
        Plant plant = fixture.givenPlantModelIsSetup();
        fixture.givenServiceIsSetup();
        String output = fixture.whenAddPlantIsCalled(plant);
        fixture.thenPlantDataExistsInDb();
        fixture.thenCorrectOutputIsGenerated(output);

    }

    @Test
    public void addPlantFailure() {

    }

    @After
    public void tearDown() {
        fixture.clearDownTestData();
        fixture.closeDatabaseConnections();
    }

    private class Fixture {

        private static final String NAME_COLUMN = "name";
        private static final String TYPE_COLUMN = "type";
        private static final String IMAGE_COLUMN = "image";
        private static final String GARDEN_ID_COLUMN = "garden_id";
        private Connection connection;
        private PlantRetrieval plantRetrieval;

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
                statement.setString(1, "Test Tomato");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Plant givenPlantModelIsSetup() {
            Plant plant = new Plant();
            plant.setName("Test Tomato");
            plant.setImageName("32323.png");
            plant.setType("Tomato");
            plant.setGardenId(1);
            return plant;
        }

        public String whenAddPlantIsCalled(Plant plant) {
            return plantRetrieval.addPlant(plant);
        }

        public void givenServiceIsSetup() {
            plantRetrieval = new PlantRetrievalJDBC();
        }

        public void thenPlantDataExistsInDb() {
            try {
                ResultSet result = findTestPlantDetailsInDb();
                assertEquals("Test Tomato", result.getString(NAME_COLUMN));
                assertEquals("Tomato", result.getString(TYPE_COLUMN));
                assertEquals("32323.png", result.getString(IMAGE_COLUMN));
                assertEquals(1, result.getInt(GARDEN_ID_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        private ResultSet findTestPlantDetailsInDb() throws SQLException{
            PreparedStatement statement = connection.prepareStatement("SELECT " + NAME_COLUMN + ", " + TYPE_COLUMN + ", " + IMAGE_COLUMN + ", " + GARDEN_ID_COLUMN + " FROM plant WHERE name = ? LIMIT 1");
            statement.setString(1, "Test Tomato");
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }


        public void thenCorrectOutputIsGenerated(String output) {
            assertEquals("{\"status\":\"success\",\"plant_name\":\"Test Tomato\",\"id\":", output.substring(0, 52));
        }
    }
}