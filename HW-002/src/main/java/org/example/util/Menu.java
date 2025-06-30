package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.controller.PersonController;
import org.example.repository.PersonRepository;
import org.example.repository.PersonRepositoryImpl;
import org.example.service.PersonService;
import org.example.service.PersonServiceImpl;

@UtilityClass

public class Menu {

    public void start() {
        PersonRepository personRepository = new PersonRepositoryImpl();
        PersonService personService = new PersonServiceImpl(personRepository);
        PersonController personController = new PersonController(personService);
        personController.welcomePerson();
    }
}