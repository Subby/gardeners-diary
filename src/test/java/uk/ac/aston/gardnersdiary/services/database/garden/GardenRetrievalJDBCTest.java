package uk.ac.aston.gardnersdiary.services.database.garden;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.Garden;

import java.sql.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Denver on 01/12/2017.
 */
public class GardenRetrievalJDBCTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
        fixture.givenDatabaseConnectionsAreSetup();
    }

    @Test
    public void getGardenById() {
        int id = fixture.givenTestDataIsInDatabase();
        fixture.givenServiceIsSetup();
        Garden garden = fixture.whenGetGardenByIdIsCalled(id);
        fixture.thenCorrectGardenModelIsBuilt(garden, id);
    }

    @Test
    public void getGarden() {
        fixture.givenTestDataIsInDatabase();
        fixture.givenServiceIsSetup();
        Garden garden = fixture.whenGetGardenIsCalled();
        fixture.thenCorrectGardenModelIsBuilt(garden, garden.getId());
    }

    @Test
    public void saveGarden() {
        fixture.givenServiceIsSetup();
        Garden garden = fixture.givenGardenModelIsSetup();
        fixture.whenSaveGardenIsCalled(garden);
        fixture.thenCorrectDetailsExistInDb();
    }

    @Test
    public void updatePlantNameInJSON() {
        fixture.givenServiceIsSetup();
        fixture.givenJSONTestDataIsInDatabase();
        fixture.whenUpdatePlantNameInJSONIsCalled(24, "Sick plant");
        fixture.thenNameHasBeenCorrectlyUpdated();
    }

    @Test
    public void deletePlantInJson() {
        fixture.givenServiceIsSetup();
        fixture.givenJSONTestDataIsInDatabase();
        fixture.whenDeletePlantInJSONIsCalled(24);
        fixture.thenPlantHasBeenDeletedInJson();
    }

    @After
    public void tearDown() {
        fixture.clearDownTestData();
        fixture.closeDatabaseConnections();
    }

    private class Fixture {

        private Connection connection;
        private GardenRetrieval gardenRetrieval;

        private static final String NAME_COLUMN = "name";
        private static final String IMAGE_COLUMN = "image";
        private static final String REGION_JSON_COLUMN = "region_json";

        public void givenServiceIsSetup() {
            gardenRetrieval = new GardenRetrievalJDBC();
        }

        public int givenTestDataIsInDatabase() {
            PreparedStatement statement = null;
            int id = 0;
            try {
                statement = connection.prepareStatement("INSERT INTO `garden` (name, image, region_json, created_at, updated_at) VALUES (?, ?, ?, ?, ?)", new String[]{"id"});
                statement.setString(1, "Test Garden");
                statement.setString(2, "garden.jpg");
                statement.setString(3, "testjson");
                statement.setDate(4, Date.valueOf("2017-11-01"));
                statement.setDate(5, Date.valueOf("2017-11-01"));
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


        public void givenJSONTestDataIsInDatabase() {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("INSERT INTO `garden` (name, image, region_json, created_at, updated_at) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, "Test Garden");
                statement.setString(2, "garden.jpg");
                statement.setString(3, "{\"attrs\":{},\"className\":\"Layer\",\"children\":[{\"attrs\":{\"x\":50,\"y\":50,\"height\":250,\"width\":500,\"id\":\"gardenImage\"},\"className\":\"Image\"},{\"attrs\":{\"x\":252,\"y\":174,\"width\":34,\"height\":25,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"\",\"plantId\":21},\"className\":\"Rect\"},{\"attrs\":{\"x\":447,\"y\":189,\"width\":49,\"height\":38,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Plant\",\"plantId\":24},\"className\":\"Rect\"},{\"attrs\":{\"x\":227,\"y\":204,\"width\":25,\"height\":32,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Test\",\"plantId\":25},\"className\":\"Rect\"},{\"attrs\":{\"x\":288,\"y\":227,\"width\":26,\"height\":34,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Cucumber\",\"plantId\":46},\"className\":\"Rect\"},{\"attrs\":{\"x\":373,\"y\":200,\"width\":33,\"height\":44,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"zx\",\"plantId\":65},\"className\":\"Rect\"},{\"attrs\":{\"x\":131,\"y\":231,\"width\":55,\"height\":42,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Mango\",\"plantId\":84},\"className\":\"Rect\"}]}");
                statement.setDate(4, Date.valueOf("2017-11-01"));
                statement.setDate(5, Date.valueOf("2017-11-01"));
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Garden givenGardenModelIsSetup() {
            Garden model = new Garden();
            model.setName("Test Garden");
            model.setImage("garden.jpg");
            model.setRegionJson("testjson");
            return model;
        }

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

        public Garden whenGetGardenByIdIsCalled(int id) {
            return gardenRetrieval.getGardenById(id);
        }

        public Garden whenGetGardenIsCalled() { return gardenRetrieval.getGarden(); }


        public void whenSaveGardenIsCalled(Garden garden) {
            gardenRetrieval.saveGarden(garden);
        }

        public void whenUpdatePlantNameInJSONIsCalled(int id, String newName) {
            gardenRetrieval.updatePlantNameInJSON(id, newName);
        }

        public void whenDeletePlantInJSONIsCalled(int id) {
            gardenRetrieval.deletePlantInJson(id);
        }

        public void thenCorrectGardenModelIsBuilt(Garden garden, int id) {
            assertEquals(id, garden.getId());
            assertEquals("Test Garden", garden.getName());
            assertEquals("garden.jpg", garden.getImage());
            assertEquals("testjson", garden.getRegionJson());
            assertEquals("2017-11-01", garden.getCreatedAt().toString());
            assertEquals("2017-11-01", garden.getUpdatedAt().toString());
        }

        public void thenCorrectDetailsExistInDb() {
            try {
                ResultSet result = findTestGardenDetailsInDb();
                assertEquals("Test Garden", result.getString(NAME_COLUMN));
                assertEquals("garden.jpg", result.getString(IMAGE_COLUMN));
                assertEquals("testjson", result.getString(REGION_JSON_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private ResultSet findTestGardenDetailsInDb() throws SQLException {
            PreparedStatement statement = connection.prepareStatement("SELECT "
                    + NAME_COLUMN + "," + IMAGE_COLUMN + "," + REGION_JSON_COLUMN +
                    " FROM garden WHERE name = ? LIMIT 1");
            statement.setString(1, "Test Garden");
            ResultSet result = statement.executeQuery();
            result.next();
            return result;
        }

        public void thenNameHasBeenCorrectlyUpdated() {
            try {
                ResultSet result = findTestGardenDetailsInDb();
                assertEquals("{\"attrs\":{},\"className\":\"Layer\",\"children\":[{\"attrs\":{\"x\":50,\"y\":50,\"height\":250,\"width\":500,\"id\":\"gardenImage\"},\"className\":\"Image\"},{\"attrs\":{\"x\":252,\"y\":174,\"width\":34,\"height\":25,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"\",\"plantId\":21},\"className\":\"Rect\"},{\"attrs\":{\"x\":447,\"y\":189,\"width\":49,\"height\":38,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Sick plant\",\"plantId\":24},\"className\":\"Rect\"},{\"attrs\":{\"x\":227,\"y\":204,\"width\":25,\"height\":32,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Test\",\"plantId\":25},\"className\":\"Rect\"},{\"attrs\":{\"x\":288,\"y\":227,\"width\":26,\"height\":34,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Cucumber\",\"plantId\":46},\"className\":\"Rect\"},{\"attrs\":{\"x\":373,\"y\":200,\"width\":33,\"height\":44,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"zx\",\"plantId\":65},\"className\":\"Rect\"},{\"attrs\":{\"x\":131,\"y\":231,\"width\":55,\"height\":42,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Mango\",\"plantId\":84},\"className\":\"Rect\"}]}", result.getString(REGION_JSON_COLUMN));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void thenPlantHasBeenDeletedInJson() {
            try {
                ResultSet result = findTestGardenDetailsInDb();
                assertEquals("{\"attrs\":{},\"className\":\"Layer\",\"children\":[{\"attrs\":{\"x\":50,\"y\":50,\"height\":250,\"width\":500,\"id\":\"gardenImage\"},\"className\":\"Image\"},{\"attrs\":{\"x\":252,\"y\":174,\"width\":34,\"height\":25,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"\",\"plantId\":21},\"className\":\"Rect\"},{\"attrs\":{\"x\":227,\"y\":204,\"width\":25,\"height\":32,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Test\",\"plantId\":25},\"className\":\"Rect\"},{\"attrs\":{\"x\":288,\"y\":227,\"width\":26,\"height\":34,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Cucumber\",\"plantId\":46},\"className\":\"Rect\"},{\"attrs\":{\"x\":373,\"y\":200,\"width\":33,\"height\":44,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"zx\",\"plantId\":65},\"className\":\"Rect\"},{\"attrs\":{\"x\":131,\"y\":231,\"width\":55,\"height\":42,\"stroke\":\"black\",\"name\":\"regionRect\",\"regionName\":\"Mango\",\"plantId\":84},\"className\":\"Rect\"}]}", result.getString(REGION_JSON_COLUMN));
            } catch (SQLException e) {
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
                PreparedStatement statement = connection.prepareStatement("DELETE FROM garden where name = ?");
                statement.setString(1, "Test Garden");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}