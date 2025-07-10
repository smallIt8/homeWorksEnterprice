package org.example.service;

import java.util.List;
import java.util.Optional;

public interface Service<T, ID> {
    void create();

    void createBatch();

    List<T> getAll();

    Optional<T> getById(ID value);

    Optional<T> updateById(ID value);

    void delete(ID value);
}