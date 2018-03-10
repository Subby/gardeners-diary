package uk.ac.aston.gardnersdiary.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Denver on 01/12/2017.
 */
public class PropertyService {
    public static final String CONFIG_FILE = "config.properties";
    private Properties properties = new Properties();
    private static PropertyService propertyServiceInstance = null;

    private PropertyService() {
        loadProperties();
    }

    public static PropertyService getInstance() {
        if (propertyServiceInstance == null) {
            return new PropertyService();
        } else {
            return propertyServiceInstance;
        }
    }

    private void loadProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(CONFIG_FILE).getFile());
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
