package uk.ac.aston.gardnersdiary;

import org.javalite.activejdbc.Base;
import uk.ac.aston.gardnersdiary.controllers.*;

import java.io.File;

import static spark.Spark.*;

/**
 * Created by Denver on 15/10/2017.
 */
public class Main {
    private static String INDEX_PATH  = "/";
    private static String IMPORT_GARDEN_PATH = "/importgarden";
    private static String MANAGE_GARDEN_PATH = "/managegarden";
    private static String SAVE_GARDEN_JSON_PATH = "/savegardenjson";
    private static String PLANT_INFO_PATH = "/plantinfo/:plantname";
    private static String TASKS_PATH = "/tasks";
    private static String PLANTS_PATH = "/plants";

    public static void main(String[] args) {
        setupSpark();
        setupRoutes();
    }

    private static void setupRoutes() {
        setupDatabaseConnectionFilterRoutes();
        get(INDEX_PATH, IndexController.getInstance().getIndex);
        get(MANAGE_GARDEN_PATH, GardenController.getInstance().getManageGarden);
        get(IMPORT_GARDEN_PATH, GardenController.getInstance().getImportGarden);
        post(IMPORT_GARDEN_PATH, GardenController.getInstance().postImportGarden);
        post(SAVE_GARDEN_JSON_PATH, GardenController.getInstance().postSaveGardenData);
        get(PLANT_INFO_PATH, PlantInformationController.getInstance().getIndex);
        get(TASKS_PATH, TaskController.getInstance().getIndex);
        get(PLANTS_PATH, PlantsController.getInstance().getIndex);
    }

    private static void setupDatabaseConnectionFilterRoutes() {
        before("/*", (req, res) -> Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/gardenerdiary", "root", ""));
        after("/*", (req, res) -> Base.close());
    }

    private static void setupSpark() {
        port(4567);
        staticFiles.location("/public");
        setupUploadsDir();
        staticFiles.expireTime(600L);
    }

    private static void setupUploadsDir() {
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        staticFiles.externalLocation("upload");
    }
}
