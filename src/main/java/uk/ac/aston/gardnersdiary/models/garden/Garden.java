package uk.ac.aston.gardnersdiary.models.garden;

/**
 * Created by Denver on 15/11/2017.
 */
public class Garden extends Model {

    private String name;
    private String image;

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
}
