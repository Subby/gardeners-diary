package uk.ac.aston.gardnersdiary.models;

import java.util.Date;

/**
 * Created by Denver on 01/12/2017.
 * Used as a base class for all the models (see other classes in this package).
 */
public abstract class Model {

    private int id;
    private Date createdAt;
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
