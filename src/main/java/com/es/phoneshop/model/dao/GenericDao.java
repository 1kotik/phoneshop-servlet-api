package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.GenericEntity;

import java.util.Optional;

public interface GenericDao<T extends GenericEntity> {
    Optional<T> getById(Long id);
    void save(T entity);
}
