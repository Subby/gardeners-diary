package uk.ac.aston.gardnersdiary.services.database.garden;

import uk.ac.aston.gardnersdiary.models.Garden;

/**
 * Created by Denver on 16/11/2017.
 * Adapted from: http://javalite.io/documentation
 */
public interface GardenRetrieval {

    /**
     * Gets a {@link Garden garden} from the database.
     * @param id the of the garden to get
     * @return the garden with the given id
     */
    Garden getGardenById(int id);

    /**
     * Gets the latest {@link Garden garden} from the database.
     * @return the latest garden
     */
    Garden getGarden();

    /**
     * Saves a {@link Garden garden} into the database.
     * @param garden the garden to save
     */
    void saveGarden(Garden garden);

    /**
     * Updates the region data for the latest {@link Garden garden}.
     * @param JSON the new JSON to store
     */
    void updateGardenJSON(String JSON);

    /**
     * Updates a plant's name in the region data.
     * @param id the id of the plant to update
     * @param newName the new name of the plant
     */
    void updatePlantNameInJSON(int id, String newName);

    /**
     * Deletes a plant in the region data given an id.
     * @param id the id of the plant to delete
     */
    void deletePlantInJson(int id);
}
