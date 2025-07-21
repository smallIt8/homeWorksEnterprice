package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Person;

import java.util.Optional;

@RequiredArgsConstructor

public class StartServiceImpl implements StartService {
	private final PersonService personService;

	@Override
	public Optional<Person> entry() {
		return personService.entry();

	}

	@Override
	public void create() {
		personService.create();
	}

}