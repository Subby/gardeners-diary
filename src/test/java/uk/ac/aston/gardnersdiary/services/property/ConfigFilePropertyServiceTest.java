package uk.ac.aston.gardnersdiary.services.property;

import org.junit.Before;
import org.junit.Test;
import uk.ac.aston.gardnersdiary.services.property.ConfigFilePropertyService;
import uk.ac.aston.gardnersdiary.services.property.PropertyService;

import static org.junit.Assert.*;

/**
 * Created by Denver on 01/12/2017.
 */
public class ConfigFilePropertyServiceTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
    }

    @Test
    public void getProperty() {
        fixture.givenPropertyServiceIsSetup();
        String dbNameValue = fixture.whenGetPropertyIsCalled("db.name");
        String openfarmUrlValue = fixture.whenGetPropertyIsCalled("api.openfarm.url");
        fixture.thenCorrectPropertyValueIsReturned(dbNameValue, "testdb");
        fixture.thenCorrectPropertyValueIsReturned(openfarmUrlValue, "http://openfarm.org");
    }


    private class Fixture {
        private PropertyService propertyService;


        public void givenPropertyServiceIsSetup() {
            propertyService = ConfigFilePropertyService.getInstance();
        }

        public String whenGetPropertyIsCalled(String property) {
            return propertyService.getProperty(property);
        }

        public void thenCorrectPropertyValueIsReturned(String propertyValue, String correctValue) {
            assertEquals(propertyValue, correctValue);
        }
    }

}