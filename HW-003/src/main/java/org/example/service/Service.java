package org.example.service;

import java.util.Optional;

public interface Service<E, P, ID> {

	void create(P entityDto);

	Optional<E> update(P entityDto);

	void delete(ID value);
}