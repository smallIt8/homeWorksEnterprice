package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.PersonDto;

import java.io.IOException;

@WebServlet("/person")
public class PersonServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersonDto currentPerson = (PersonDto) req.getSession().getAttribute("currentPerson");

		req.setAttribute("person", currentPerson.toNameString());

		req.getRequestDispatcher("/WEB-INF/jsp/menu/person/person-menu.jsp").forward(req, resp);
	}
}