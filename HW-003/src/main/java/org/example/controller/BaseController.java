package org.example.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.Person;

@Data
@RequiredArgsConstructor

public abstract class BaseController {
	protected Person currentPerson;
}