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

import static org.example.util.jsp.JspHelper.*;

import java.io.IOException;

@Slf4j
@WebServlet("/person")
public class PersonServlet extends HttpServlet {

	private final PersonService personService = MenuDependency.PERSON_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersonDto currentPerson = (PersonDto) req.getSession().getAttribute("currentPerson");

		if (currentPerson == null) {
			resp.sendRedirect(req.getContextPath() + "/start");
			return;
		}

		req.setAttribute("personName", currentPerson.toNameString());

		req.getRequestDispatcher(getPath("person","person-menu")).forward(req, resp);
	}
}