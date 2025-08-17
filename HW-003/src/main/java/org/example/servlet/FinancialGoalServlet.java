package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.service.FinancialGoalService;
import org.example.util.MenuDependency;

import java.io.IOException;

import static org.example.util.constant.MenuConstant.WARNING_DELETE_FINANCIAL_GOAL_MESSAGE;
import static org.example.util.jsp.JspHelper.getPath;
import static org.example.util.servlet.ServletGetUtil.actionGet;

@Slf4j
@WebServlet("/financial-goal")
public class FinancialGoalServlet extends HttpServlet {

	private final FinancialGoalService financialGoalService = MenuDependency.FINANCIAL_GOAL_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "financial-goal-menu");

		if (action == null)
			return;

		switch (action) {
			case "add" ->
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-add"))
							.forward(req, resp);
			case "update" ->
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-update"))
							.forward(req, resp);
			case "list" ->
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-list"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_FINANCIAL_GOAL_MESSAGE);
				req.getRequestDispatcher(getPath("financialGoal", "financial-goal-delete"))
						.forward(req, resp);
			}
			case "back", "financial-goal-menu" -> req.getRequestDispatcher(getPath("financialGoal", "financial-goal-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}