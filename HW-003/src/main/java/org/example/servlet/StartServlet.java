package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.service.PersonService;
import org.example.util.MenuDependency;

import java.io.IOException;
import java.util.Optional;

import static org.example.helper.jsp.JspStartHelper.getPath;

@Slf4j
@WebServlet("/start")
public class StartServlet extends HttpServlet {

	private final PersonService personService = MenuDependency.personService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");

		if ("auth".equals(action)) {
			log.info("Переход на страницу авторизации");
			req.getRequestDispatcher(getPath("auth-person"))
					.forward(req, resp);
		} else if ("register".equals(action)) {
			log.info("Переход на страницу регистрации");
			req.getRequestDispatcher(getPath("registration-person"))
					.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");

		if ("auth".equals(action)) {
			PersonDto personDto = buildPersonDto(req, false);
			log.info("Попытка входа пользователя '{}'", personDto.getUserName());
			try {
				Optional<PersonDto> personOpt = personService.entry(personDto);
				if (personOpt.isPresent()) {
					req.getSession()
							.setAttribute("currentPersonDto", personOpt.get());
					resp.sendRedirect(req.getContextPath() + "/main-person");
				}
			} catch (IllegalArgumentException e) {
				log.error("Ошибка при авторизации в сервлете'{}'", e.getMessage(), e);
				req.setAttribute("errorMessage", e.getMessage());
				req.getRequestDispatcher(getPath("auth-person"))
						.forward(req, resp);
			} catch (Exception e) {
				log.error("Ошибка при авторизации в сервлете'{}'", e.getMessage(), e);
				req.getRequestDispatcher(getPath("auth-person"))
						.forward(req, resp);
			}
		} else if ("register".equals(action)) {
			PersonDto personDto = buildPersonDto(req, true);
			try {
				personService.create(personDto);
				req.getRequestDispatcher(getPath("auth-person"))
						.forward(req, resp);
			} catch (Exception e) {
				log.error("Ошибка при регистрации в сервлете'{}'", e.getMessage(), e);
				req.getRequestDispatcher(getPath("registration-person"))
						.forward(req, resp);
			}
		}
	}

	private PersonDto buildPersonDto(HttpServletRequest req, boolean isRegistration) {
		var userName = req.getParameter("userName");
		var password = req.getParameter("password");

		if (isRegistration) {
			var firstName = req.getParameter("firstName");
			var lastName = req.getParameter("lastName");
			var email = req.getParameter("email");
			return PersonDto.builder()
					.userName(userName)
					.password(password)
					.firstName(firstName)
					.lastName(lastName)
					.email(email)
					.build();
		} else {
			return PersonDto.builder()
					.userName(userName)
					.password(password)
					.build();
		}
	}
}