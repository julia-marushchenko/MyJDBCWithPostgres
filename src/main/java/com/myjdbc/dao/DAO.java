package com.myjdbc.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// DAO interface.
public interface DAO<T, Id extends UUID> {
    List<T> getAll();
    T create(T entity);
    Optional<T> getOne(Id id);
    T update(T entity);
    void delete(Id id);
}
