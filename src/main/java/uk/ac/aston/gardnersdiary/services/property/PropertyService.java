package uk.ac.aston.gardnersdiary.services.property;

public interface PropertyService {

    /**
     * Gets a property given its name.
     * @param propertyName the property name
     * @return the property value
     */
    String getProperty(String propertyName);
}
