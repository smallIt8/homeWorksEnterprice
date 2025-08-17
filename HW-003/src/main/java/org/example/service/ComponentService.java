package org.example.service;

import java.util.List;

public interface ComponentService<E, P, ID> extends Service<E, P, ID> {

	void createBatch();

	List<E> getAll();

	List<E> getAllByOwner(P entity);

}