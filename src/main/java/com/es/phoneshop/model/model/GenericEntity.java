package com.es.phoneshop.model.model;

import java.io.Serializable;

public class GenericEntity implements Cloneable, Serializable {
    protected Long id;

    public GenericEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public GenericEntity clone() {
        GenericEntity clone;
        try {
            clone = (GenericEntity) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
