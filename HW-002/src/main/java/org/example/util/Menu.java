package org.example.util;

import org.example.controller.PersonControllerImpl;
import org.example.repository.PersonRepository;
import org.example.repository.PersonRepositoryImpl;
import org.example.service.PersonService;
import org.example.service.PersonServiceImpl;

public final class Menu {

    public static void start() {
        PersonRepository personRepository = new PersonRepositoryImpl();
        PersonService personService = new PersonServiceImpl(personRepository);
        PersonControllerImpl personControllerImpl = new PersonControllerImpl(personService);
        personControllerImpl.WelcomePerson();
    }

    private Menu() {
    }
}