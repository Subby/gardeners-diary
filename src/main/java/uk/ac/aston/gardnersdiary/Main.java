package uk.ac.aston.gardnersdiary;

import org.javalite.activejdbc.Base;
import spark.Spark;
import uk.ac.aston.gardnersdiary.controllers.*;
import uk.ac.aston.gardnersdiary.services.property.ConfigFilePropertyService;
import uk.ac.aston.gardnersdiary.services.property.PropertyService;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

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
    private static String PLANT_ADD_PATH = "/plant/add";
    private static String PLANT_VIEW_PATH = "/plant/view/:plantid";
    private static String UPDATE_PLANT_PATH = "/plant/update";
    private static String PLANTS_DATA = "/plants/data";
    private static String DELETE_PLANT_PATH = "/plant/delete/:plantid";
    private static String TASK_TYPE_VIEW = "/tasktypes";

    private static PropertyService propertyService = ConfigFilePropertyService.getInstance();

    private static String DATABASE_HOST = propertyService.getProperty("db.host");
    private static String DATABASE_NAME = propertyService.getProperty("db.name");
    private static String DATABASE_USERNAME = propertyService.getProperty("db.user");
    private static String DATABASE_PASSWORD = propertyService.getProperty("db.password");



    public static void main(String[] args) {
        setupSpark();
        setupRoutes();
    }

    private static void setupRoutes() {
        setupDatabaseConnectionFilterRoutes();
        setupGardenRoutes();
        setupPlantsRoutes();
        setupTaskTypesRoutes();
        get(INDEX_PATH, IndexController.getInstance().getIndex);
        get(PLANT_INFO_PATH, PlantInformationController.getInstance().getIndex);
        get(TASKS_PATH, TaskController.getInstance().getIndex);
    }

    private static void setupPlantsRoutes() {
        get(PLANTS_PATH, PlantsController.getInstance().getIndex);
        post(PLANT_ADD_PATH, PlantsController.getInstance().postAddPlant);
        get(PLANT_VIEW_PATH, PlantsController.getInstance().getPlantView);
        post(UPDATE_PLANT_PATH, PlantsController.getInstance().postUpdatePlant);
        get(PLANTS_DATA, PlantsController.getInstance().getPlantData);
        delete(DELETE_PLANT_PATH, PlantsController.getInstance().deletePlant);
    }

    private static void setupGardenRoutes() {
        get(MANAGE_GARDEN_PATH, GardenController.getInstance().getManageGarden);
        get(IMPORT_GARDEN_PATH, GardenController.getInstance().getImportGarden);
        post(IMPORT_GARDEN_PATH, GardenController.getInstance().postImportGarden);
        post(SAVE_GARDEN_JSON_PATH, GardenController.getInstance().postSaveGardenData);
    }

    private static void setupTaskTypesRoutes() {
        get(TASK_TYPE_VIEW, TaskTypesController.getInstance().getIndex);
    }

    private static void setupDatabaseConnectionFilterRoutes() {
        PropertyService propertyService = ConfigFilePropertyService.getInstance();
        String databaseHost = propertyService.getProperty("db.host");
        String databaseName = propertyService.getProperty("db.name");
        String databaseUser = propertyService.getProperty("db.user");
        String databasePass = propertyService.getProperty("db.password");
        before("/*", (req, res) -> Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"+databaseHost+"/"+databaseName, databaseUser, databasePass));
        after("/*", (req, res) -> Base.close());
    }

    private static void setupSpark() {
        port(4567);
        staticFiles.location("/public");
        setupUploadsDir();
        staticFiles.expireTime(600L);
        setupSparkExceptionHandling();
    }

    private static void setupSparkExceptionHandling() {
        Spark.exception(Exception.class, (e, request, response) -> {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            System.err.println(sw.getBuffer().toString());
        });
    }

    private static void setupUploadsDir() {
        PropertyService propertyService = ConfigFilePropertyService.getInstance();
        String uploadDirectory = propertyService.getProperty("dir.uploads");
        File uploadDir = new File(uploadDirectory);
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        staticFiles.externalLocation(uploadDirectory);
    }
}
