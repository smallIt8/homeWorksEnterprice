package org.example.service;

import org.example.model.Person;

import java.util.List;

public interface ComponentService<T, ID> extends Service<T, ID> {

	void createBatch();

	List<T> getAll();

	List<T> getAllByPerson(Person person);
}