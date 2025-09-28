package org.example.helper.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;

import java.io.IOException;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;

@Slf4j
@UtilityClass
public class ServletHelper {

	public static String actionGet(HttpServletRequest req, HttpServletResponse resp, String defaultAction) throws IOException {
		var action = req.getParameter("action");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		setAttribute(req, currentPersonDto, action);

		if (action == null) {
			action = defaultAction;
		}

		log.info("Пользователь '{}' в меню '{}' выбирает действие '{}'",
				 currentPersonDto.getFullName(),
				 req.getServletPath(),
				 action
		);
		return action;
	}

	private static void setAttribute(HttpServletRequest req, PersonDto currentPersonDto, String action) {
		req.setAttribute("personName", currentPersonDto.getFullName());
		req.setAttribute("person", currentPersonDto);
		req.setAttribute("action", action);
	}
}
