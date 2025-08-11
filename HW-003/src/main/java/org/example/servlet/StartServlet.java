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

import static org.example.util.jsp.JspStartHelper.*;
import static org.example.util.constant.ErrorMessageConstant.*;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet("/start")
public class StartServlet extends HttpServlet {

	private final PersonService personService = MenuDependency.PERSON_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");

		if ("auth".equals(action)) {
			req.getRequestDispatcher(getPath("auth-person"))
					.forward(req, resp);
		} else if ("register".equals(action)) {
			req.getRequestDispatcher(getPath("registration-person"))
					.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");

		if ("auth".equals(action)) {
			var userName = req.getParameter("userName");
			var password = req.getParameter("password");
			PersonDto personDto = new PersonDto(
					userName,
					password);
			try {
				Optional<PersonDto> personOpt = personService.entry(personDto);
				if (personOpt.isPresent()) {
					req.getSession()
							.setAttribute("currentPerson", personOpt.get());
					resp.sendRedirect(req.getContextPath() + "/person");
				} else {
					req.setAttribute("errorMessage", ERROR_ENTER_USER_NAME_OR_PASSWORD_MESSAGE);
					req.getRequestDispatcher(getPath("auth-person"))
							.forward(req, resp);
				}
			} catch (Exception e) {
				req.getRequestDispatcher(getPath("auth-person"))
						.forward(req, resp);
			}
		} else if ("register".equals(action)) {
			var userName = req.getParameter("userName");
			var password = req.getParameter("password");
			var firstName = req.getParameter("firstName");
			var lastName = req.getParameter("lastName");
			var email = req.getParameter("email");
			PersonDto personDto = new PersonDto(
					userName,
					password,
					firstName,
					lastName,
					email
			);

			try {
				personService.create(personDto);
				req.getRequestDispatcher(getPath("auth-person"))
						.forward(req, resp);
			} catch (Exception e) {
				req.getRequestDispatcher(getPath("registration-person"))
						.forward(req, resp);
			}
		}
	}
}