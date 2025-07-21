package org.example.service;

import org.example.model.Person;

import java.util.Optional;

public interface StartService {

	Optional<Person> entry();

	void create();

}