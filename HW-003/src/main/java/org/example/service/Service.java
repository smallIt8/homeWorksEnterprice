package org.example.service;

import java.util.Optional;

public interface Service<E, P, ID> {

	void create();

	Optional<E> update(P entity);

	void delete(ID value);
}