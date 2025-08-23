package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.service.BudgetService;
import org.example.util.MenuDependency;

import java.io.IOException;

import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_BUDGET_MESSAGE;
import static org.example.util.jsp.JspHelper.getPath;
import static org.example.util.servlet.ServletGetUtil.actionGet;

@Slf4j
@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {

	private final BudgetService budgetService = MenuDependency.BUDGET_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "budget-menu");

		if (action == null)
			return;

		switch (action) {
			case "add" ->
					req.getRequestDispatcher(getPath("budget", "budget-add"))
							.forward(req, resp);
			case "update" ->
					req.getRequestDispatcher(getPath("budget", "budget-update"))
							.forward(req, resp);
			case "list" ->
					req.getRequestDispatcher(getPath("budget", "budget-list"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_BUDGET_MESSAGE);
				req.getRequestDispatcher(getPath("budget", "budget-delete"))
						.forward(req, resp);
			}
			case "back", "budget-menu" -> req.getRequestDispatcher(getPath("budget", "budget-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}