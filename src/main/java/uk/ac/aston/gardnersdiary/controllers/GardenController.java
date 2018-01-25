package uk.ac.aston.gardnersdiary.controllers;

import com.mysql.jdbc.StringUtils;
import jdk.internal.util.xml.impl.Input;
import spark.Response;
import spark.Route;
import spark.Request;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.staticFiles;

/**
 * Created by Denver on 15/10/2017.
 */
public class GardenController extends Controller {

    private static GardenController instance;

    public static GardenController getInstance() {
        if(instance == null) {
            instance = new GardenController();
        }
        return instance;
    }

    public Route getManageGarden = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Manage Garden");
        return renderView(request, attributes, "managegarden");
    };

    public Route getImportGarden = (Request request, Response response) -> {
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Import Garden");
        return renderView(request, attributes, "importGarden");
    };

    public Route postImportGarden = (Request request, Response response) -> {
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("C:/HashiCorp"));
        String name = request.queryParams("name");
        InputStream file = request.raw().getPart("file").getInputStream();
        Map<String, Object> attributes = new HashMap();
        attributes.put("title", "Import Garden");
        if(validationSucessful(name, file)) {
            String fileUploadOutput = saveGardenImageFile(request);
            return fileUploadOutput;
        } else {
            attributes.put("title", "Import Garden Error");
            attributes.put("upload_error", true);
            return renderView(request, attributes, "importGarden");
        }
    };

    public boolean validationSucessful(String name, InputStream file) {
        if(StringUtils.isNullOrEmpty(name)) {
            return false;
        }
        if(file == null) {
            return false;
        }
        return true;
    }

    private String saveGardenImageFile(Request request) throws IOException, ServletException {
        File uploadDir = new File("upload");
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        try (InputStream input = request.raw().getPart("file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        //return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";
        return tempFile.getFileName().toString();
    }

}
