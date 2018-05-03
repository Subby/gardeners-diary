package uk.ac.aston.gardnersdiary.models;

/**
 * Used to represent Plant information.
 */
public class Plant extends Model{
    private String name;
    private String imageName;
    private String type;
    private int gardenId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int id) {
        this.gardenId = id;
    }
}
