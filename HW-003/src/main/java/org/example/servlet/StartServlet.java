package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Maper.PersonMapper;
import org.example.dto.PersonDto;
import org.example.model.Person;
import org.example.service.PersonService;
import org.example.util.MenuDependency;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/start")
public class StartServlet extends HttpServlet {

	private final PersonService personService = MenuDependency.PERSON_SERVICE;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");

		if("auth".equals(action)) {
			req.getRequestDispatcher("jsp/menu/start/auth-person.jsp").forward(req, resp);
		} else if("register".equals(action)) {
			req.getRequestDispatcher("jsp/menu/start/registration-person.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("auth".equals(action)) {
			String userName = req.getParameter("userName");
			String password = req.getParameter("password");
			try {

				Optional<Person> personOpt = personService.entry(userName, password);
				if (personOpt.isPresent()) {
					PersonDto personDto = PersonMapper.modelToDto(personOpt.get());
					req.getSession().setAttribute("currentPerson", personDto);
					resp.sendRedirect(req.getContextPath() + "/person");
				} else {
					req.getRequestDispatcher("jsp/menu/start/auth-person.jsp").forward(req, resp);
				}
			} catch (Exception e) {
				req.getRequestDispatcher("jsp/menu/start/auth-person.jsp").forward(req, resp);
			}
		} else if("register".equals(action)) {
			String userName = req.getParameter("userName");
			String password = req.getParameter("password");
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");
			PersonDto personDto = new PersonDto(
					userName,
					password,
					firstName,
					lastName,
					email
			);

			try {
				personService.create(personDto);
				resp.sendRedirect(req.getContextPath() + "/start?action=auth");
			} catch (Exception e) {
				req.getRequestDispatcher("jsp/menu/start/registration-person.jsp").forward(req, resp);
			}
		}
	}
}