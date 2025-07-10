package org.example.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void create(T value);

    List<T> getAll();

    Optional<T> getById(ID value);

    void updateById(T value);

    void delete(ID value);
}