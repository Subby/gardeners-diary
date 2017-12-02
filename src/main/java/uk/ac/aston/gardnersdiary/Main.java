package uk.ac.aston.gardnersdiary;

import org.javalite.activejdbc.Base;
import uk.ac.aston.gardnersdiary.controllers.*;
import uk.ac.aston.gardnersdiary.models.garden.Garden;
import uk.ac.aston.gardnersdiary.services.database.GardenRetrievalJDBC;

import static spark.Spark.*;
/**
 * Created by Denver on 15/10/2017.
 */
public class Main {
    private static String INDEX_PATH  = "/";
    private static String IMPORT_GARDEN_PATH = "/importgarden";
    private static String GARDEN_PATH = "/managegarden";
    private static String PLANT_INFO_PATH = "/plantinfo/:plantname";
    private static String TASKS_PATH = "/tasks";
    private static String PLANTS_PATH = "/plants";

    public static void main(String[] args) {
        setupSpark();
        setupRoutes();
        setupAJDBCConnection();
    }

    private static void setupAJDBCConnection() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gardenerdiary", "root", "");
    }

    private static void setupRoutes() {
        get(INDEX_PATH, IndexController.getInstance().getIndex);
        get(GARDEN_PATH, GardenController.getInstance().getManageGarden);
        get(IMPORT_GARDEN_PATH, GardenController.getInstance().getImportGarden);
        get(PLANT_INFO_PATH, PlantInformationController.getInstance().getIndex);
        get(TASKS_PATH, TaskController.getInstance().getIndex);
        get(PLANTS_PATH, PlantsController.getInstance().getIndex);
    }

    private static void setupSpark() {
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
    }
}
