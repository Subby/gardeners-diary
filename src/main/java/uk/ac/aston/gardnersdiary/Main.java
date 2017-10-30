package uk.ac.aston.gardnersdiary;

import uk.ac.aston.gardnersdiary.controllers.ImportGardenController;
import uk.ac.aston.gardnersdiary.controllers.PlantInformationController;

import static spark.Spark.*;
/**
 * Created by Denver on 15/10/2017.
 */
public class Main {
    private static String IMPORT_GARDEN_PATH = "/importgarden";
    private static String PLANT_INFO_PATH = "/plantinfo/:plantname";

    public static void main(String[] args) {
        setupSpark();
        setupRoutes();
    }

    private static void setupRoutes() {
        get(IMPORT_GARDEN_PATH, ImportGardenController.getInstance().getIndex);
        get(PLANT_INFO_PATH, PlantInformationController.getInstance().getIndex);
    }

    private static void setupSpark() {
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
    }
}
