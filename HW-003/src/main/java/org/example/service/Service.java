package org.example.service;

import java.util.Optional;

public interface Service<T, ID> {

	void create();

	Optional<T> update(ID value);

	void delete(ID value);
}