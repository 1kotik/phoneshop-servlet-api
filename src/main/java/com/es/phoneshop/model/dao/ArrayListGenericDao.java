package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.model.GenericEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ArrayListGenericDao<T extends GenericEntity> implements GenericDao<T> {
    protected List<T> list;
    protected Long currentId;

    protected ArrayListGenericDao() {
        list = new ArrayList<>();
        currentId = 0L;
    }

    public synchronized Optional<T> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search. ID is null");
        }

        return (Optional<T>) list.stream()
                .filter(entity -> id.equals(entity.getId()))
                .findFirst()
                .map(T::clone);
    }

    public synchronized void save(T entity) {
        Optional<T> entityToUpdate;
        entityToUpdate = getEntityToUpdate(entity);
        if (entityToUpdate.isPresent()) {
            list.set(list.indexOf(entityToUpdate.get()), entity);
        } else {
            entity.setId(++currentId);
            list.add(entity);
        }
    }

    private Optional<T> getEntityToUpdate(T entity) {
        if (entity.getId() != null) {
            return list.stream()
                    .filter(listedEntity -> entity.getId().equals(listedEntity.getId()))
                    .findFirst();
        }
        return Optional.empty();
    }
}
