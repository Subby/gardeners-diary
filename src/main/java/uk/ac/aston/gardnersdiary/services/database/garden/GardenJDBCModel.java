package uk.ac.aston.gardnersdiary.services.database.garden;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.Date;

/**
 * Created by Denver on 01/12/2017.
 */
@Table("garden")
public class GardenJDBCModel extends Model {

    private static final String NAME_COLUMN = "name";
    private static final String IMAGE_COLUMN = "image";
    private static final String REGION_JSON_COLUMN = "region_json";
    private static final String CREATED_AT_COLUMN = "created_at";
    private static final String UPDATED_AT_COLUMN = "updated_at";

    public String getName() {
        return getString(NAME_COLUMN);
    }

    public String getImage() {
         return getString(IMAGE_COLUMN);
    }

    public String getRegionJson() {
        return getString(REGION_JSON_COLUMN);
    }

    public Date getUpdatedAt() {
        return getDate(CREATED_AT_COLUMN);
    }

    public Date getCreatedAt() {
        return getDate(UPDATED_AT_COLUMN);
    }

    public void setName(String name) {
        set(NAME_COLUMN, name);
    }

    public void setImage(String image) {
        set(IMAGE_COLUMN, image);
    }

    public void setRegionJson(String regionJson) {
        set(REGION_JSON_COLUMN, regionJson);
    }
}
