package org.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ComponentService<D, E, P, ID> {

	void create(D entityDto);

	Map<D, String> createBatch(List<D> entitiesDto);

	Optional<E> findById(ID entityId, ID currentPersonId);

	List<D> findAll(P currentPersonDto);

	Optional<D> update(D entityDto, P currentPersonDto);

	void delete(D entityDto, P currentPersonDto);

}