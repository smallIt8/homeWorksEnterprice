package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;

@Slf4j
@Component
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var currentPerson = presenceCurrentPersonDto(req, resp);

		log.info("Пользователь '{}' с ID '{}' вышел из системы",
				 currentPerson.getFullName(),
				 currentPerson.getPersonId());

		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
}
