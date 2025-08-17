package org.example.util.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.example.util.SessionUtil.*;

@Slf4j
@UtilityClass
public class ServletGetUtil {

	public String actionGet(HttpServletRequest req, HttpServletResponse resp, String defaultAction) throws IOException {
		var action = req.getParameter("action");
		var currentPerson = presenceCurrentPersonDto(req, resp);

		if (currentPerson == null) {
			return null;
		}

		req.setAttribute("personName", currentPerson.toNameString());
		req.setAttribute("person", currentPerson);
		req.setAttribute("action", action);

		if (action == null)
			action = defaultAction;

		log.info("Пользователь '{}' в меню '{}' выбирает действие '{}'",
				 currentPerson.toNameString(),
				 req.getServletPath(),
				 action
		);
		return action;
	}
}