package uk.ac.aston.gardnersdiary.services.database;

import com.sun.corba.se.impl.orb.PrefixParserAction;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.models.garden.Garden;

import java.sql.*;
import java.text.DateFormat;

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
    public void saveGarden() {
        fixture.givenServiceIsSetup();
        Garden garden = fixture.givenGardenModelIsSetup();
        fixture.whenSaveGardenIsCalled(garden);
        fixture.thenCorrectDetailsExistInDb();
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


        public void whenSaveGardenIsCalled(Garden garden) {
            gardenRetrieval.saveGarden(garden);
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