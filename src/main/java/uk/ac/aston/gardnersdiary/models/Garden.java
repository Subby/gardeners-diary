package uk.ac.aston.gardnersdiary.models;

/**
 * Created by Denver on 15/11/2017.
 */
public class Garden extends Model {

    private String name;
    private String image;
    private String regionJson;

    public Garden() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegionJson() {
        return regionJson;
    }

    public void setRegionJson(String regionJson) {
        this.regionJson = regionJson;
    }
}
