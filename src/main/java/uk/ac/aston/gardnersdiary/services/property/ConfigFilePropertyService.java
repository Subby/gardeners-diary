package uk.ac.aston.gardnersdiary.services.property;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFilePropertyService implements PropertyService {

    private static final String CONFIG_FILE = "config.properties";

    private static PropertyService instance = new ConfigFilePropertyService();
    private Properties properties = new Properties();

    private ConfigFilePropertyService() {
        loadProperties();
    }

    public static PropertyService getInstance() {
        if(instance == null) {
            return new ConfigFilePropertyService();
        } else {
            return instance;
        }
    }

    private void loadProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(CONFIG_FILE).toURI());
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
