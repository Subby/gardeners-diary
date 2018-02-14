package uk.ac.aston.gardnersdiary.services.database.plant;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

@Table("plant")
public class PlantJDBCModel extends Model {


    private static final String NAME_COLUMN = "name";
    private static final String TYPE_COLUMN = "type";
    private static final String IMAGE_COLUMN = "image";
    private static final String GARDEN_ID_COLUMN = "garden_id";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

    public String getName() {
        return getString(NAME_COLUMN);
    }

    public void setName(String name) {
        set(NAME_COLUMN, name);
    }

    public String getType() {
        return getString(TYPE_COLUMN);
    }

    public void setType(String type) {
        set(TYPE_COLUMN, type);
    }

    public int getGardenId() {
        return getInteger(GARDEN_ID_COLUMN);
    }

    public void setGardenId(int gardenId) {
        set(GARDEN_ID_COLUMN, gardenId);
    }

    public String getImage() {
        return getString(IMAGE_COLUMN);
    }

    public void setImage(String image) {
        set(IMAGE_COLUMN, image);
    }

    public Date getUpdatedAt() {
        return getDate(CREATED_AT_COLUMN);
    }

    public Date getCreatedAt() {
        return getDate(UPDATED_AT_COLUMN);
    }

}
