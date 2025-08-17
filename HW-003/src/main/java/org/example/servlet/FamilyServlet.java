package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FamilyService;
import org.example.util.MenuDependency;

import java.io.IOException;

import static org.example.util.constant.MenuConstant.WARNING_DELETE_FAMILY_MESSAGE;
import static org.example.util.jsp.JspHelper.getPath;
import static org.example.util.servlet.ServletGetUtil.actionGet;

@Slf4j
@WebServlet("/family")
public class FamilyServlet extends HttpServlet {

	private final FamilyService familyService = MenuDependency.FAMILY_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "family-menu");

		if (action == null)
			return;

		switch (action) {
			case "add" ->
					req.getRequestDispatcher(getPath("family", "family-add"))
							.forward(req, resp);
			case "update" ->
					req.getRequestDispatcher(getPath("family", "family-update"))
							.forward(req, resp);
			case "list" ->
					req.getRequestDispatcher(getPath("family", "family-list"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_FAMILY_MESSAGE);
				req.getRequestDispatcher(getPath("family", "family-delete"))
						.forward(req, resp);
			}
			case "back", "family-menu" -> req.getRequestDispatcher(getPath("family", "family-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
