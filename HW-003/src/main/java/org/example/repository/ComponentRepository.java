package org.example.repository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository<E, ID> {

	void create(E entity);

	void createBatch(List<E> entities);

	Optional<E> findById(ID entityId);

	List<E> findAll(ID currentPersonId);

	void update(E entity);

	void delete(ID entityId, ID currentPersonId);
}