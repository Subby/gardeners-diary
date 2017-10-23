package uk.ac.aston.gardnersdiary.services.plantinformation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Denver on 23/10/2017.
 */
public class OpenFarmPlantInformationServiceTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
    }

    @Test
    public void getPlantInformation() {
        fixture.givenServiceIsSetup();
        String plantInformationJson = fixture.whenGetPlantInformationIsCalled();
        fixture.thenCorrectDataIsReturned(plantInformationJson);
    }

    private class Fixture {
        private OpenFarmPlantInformationService openFarmPlantInformationService;

        private void givenServiceIsSetup() {
            openFarmPlantInformationService = new OpenFarmPlantInformationService();
        }

        private String whenGetPlantInformationIsCalled() {
            return openFarmPlantInformationService.getPlantInformation("tomato");
        }

        private void thenCorrectDataIsReturned(String plantInformationJson) {
            String expectedJson = "{\"description\":\"The tomato is the fruit of the tomato plant, a member of the Nightshade family (Solanaceae). " +
                    "The fruit grows on a sprawling vine that is often trellised or caged to keep it upright. " +
                    "There are many kinds of tomatoes, including conventional, hybrid, heirloom, plum, grape, and cherry. " +
                    "Determinate or bush varieties do not need pruning and can be grown without trellises or cages. " +
                    "Indeterminate or climbing varieties benefit from pruning and should be trellised, caged, or staked.\"," +
                    "\"sun_requirements\":\"Full Sun\"," +
                    "\"sowing_method\":\"Direct seed indoors, transplant seedlings outside after hardening off\"," +
                    "\"row_spacing\":75," +
                    "\"height\":90," +
                    "\"status\":\"Ok\"}";
            assertEquals(expectedJson, plantInformationJson);
        }
    }
}