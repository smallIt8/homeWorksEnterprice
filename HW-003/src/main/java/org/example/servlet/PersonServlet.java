package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.service.PersonService;
import org.example.util.DependencyInjection;

import java.io.IOException;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.ErrorMessageConstant.ERROR_UPDATE_PERSON_PASSWORD_MESSAGE;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_PERSON_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@WebServlet("/main-person")
public class PersonServlet extends HttpServlet {

	private final PersonService personService = DependencyInjection.personService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "person-menu");

		switch (action) {
			case "update", "update-person", "update-password" ->
					req.getRequestDispatcher(getPath("person", "person-update"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_PERSON_MESSAGE);
				req.getRequestDispatcher(getPath("person", "person-delete"))
						.forward(req, resp);
			}
			case "back", "person-menu" -> req.getRequestDispatcher(getPath("person", "person-menu"))
					.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		log.info("Пользователь '{}' в меню '{}' выбирает действие '{}'",
				 currentPersonDto.getFullName(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "updated-person" -> {
					PersonDto personUpdateDto = buildPersonDto(req, currentPersonDto, true);
					personService.update(personUpdateDto);
					req.getSession().setAttribute("currentPersonDto", personUpdateDto);
					resp.sendRedirect(req.getContextPath() + "/main-person");
				}
				case "updated-password" -> {
					PersonDto personUpdatePassDto = buildPersonDto(req, currentPersonDto, false);
					var updatePasswordOpt = personService.updatePassword(personUpdatePassDto);
					if (updatePasswordOpt.isEmpty()) {
						req.setAttribute("errorMessage", ERROR_UPDATE_PERSON_PASSWORD_MESSAGE);
					}
					resp.sendRedirect(req.getContextPath() + "/main-person");
				}
				case "delete-person" -> {
					personService.delete(currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/logout");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/main-person");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
			req.getRequestDispatcher(getPath("person", "person-update"))
					.forward(req, resp);
		}
	}

	private PersonDto buildPersonDto(HttpServletRequest req, PersonDto currentPerson, boolean isUpdate) {
		if (isUpdate) {
			var firstName = req.getParameter("firstName");
			var lastName = req.getParameter("lastName");
			var email = req.getParameter("email");
			return PersonDto.builder()
					.personId(currentPerson.getPersonId())
					.firstName(firstName)
					.lastName(lastName)
					.email(email)
					.build();
		} else {
			var password = req.getParameter("password");
			return PersonDto.builder()
					.personId(currentPerson.getPersonId())
					.password(password)
					.build();
		}
	}
}
