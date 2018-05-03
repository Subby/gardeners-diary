package uk.ac.aston.gardnersdiary.services.database.plant;

import uk.ac.aston.gardnersdiary.models.Plant;

import java.util.List;

public interface PlantRetrieval {

    /**
     * Adds a {@link Plant plant} to the database.
     * @param plantToAdd the plant to add
     * @return the status of the operation, used in the UI code
     */
    String addPlant(Plant plantToAdd);

    /**
     * Gets a {@link Plant plant} from the database given the id.
     * @param id the id of the plant to get
     * @return the plant returned from the database
     */
    Plant getPlantById(int id);

    /**
     * Gets all the {@link Plant plant} data from the database.
     * Used in the UI to populate the plant table.
     * @return all the plant data in JSON form.
     */
    String getAllPlantData();

    /**
     * Updates a {@link Plant plant} in the database given the id.
     * @param id the id the plant to update
     * @param name the new name to update to
     * @param type the type of plant to update to
     * @return the status of the operation, used in the UI code
     */
    String updatePlantDetails(int id, String name, String type);

    /**
     * Deletes a {@link Plant plant} in the database given the id.
     * @param id the id of the plant to delete
     * @return the status of the operation, used in the UI code
     */
    String deletePlant(int id);

    /**
     * Returns the recently planted plants for the front page.
     * @return the recently planted plants
     */
    List<Plant> getFrontPagePlants();
}
