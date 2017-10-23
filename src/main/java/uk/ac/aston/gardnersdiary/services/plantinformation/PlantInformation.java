package uk.ac.aston.gardnersdiary.services.plantinformation;

/**
 * Created by Denver on 23/10/2017.
 */
public class PlantInformation {

    private String description;
    private String sunRequirements;
    private String sowingMethod;
    private int rowSpacing;
    private int height;

    public PlantInformation(String description, String sunRequirements, String sowingMethod, int rowSpacing, int height) {
        this.description = description;
        this.sunRequirements = sunRequirements;
        this.sowingMethod = sowingMethod;
        this.rowSpacing = rowSpacing;
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public String getSunRequirements() {
        return sunRequirements;
    }

    public String getSowingMethod() {
        return sowingMethod;
    }

    public int getRowSpacing() {
        return rowSpacing;
    }

    public int getHeight() {
        return height;
    }
}
