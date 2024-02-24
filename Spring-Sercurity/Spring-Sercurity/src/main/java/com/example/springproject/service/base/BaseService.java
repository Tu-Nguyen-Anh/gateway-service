package com.example.springproject.service.base;

import java.util.List;

/**
 * Base service interface defining common CRUD operations for entities.
 *
 * @param <T> The type of entity managed by the service.
 */
public interface BaseService<T> {
    T create(T t);

    T update(T t);

    void delete(String id);

    T get(String id);

    List<T> list();
}