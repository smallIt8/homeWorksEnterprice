package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.service.TransactionService;
import org.example.util.MenuDependency;

import java.io.IOException;

import static org.example.util.constant.MenuConstant.WARNING_DELETE_TRANSACTION_MESSAGE;
import static org.example.util.jsp.JspHelper.getPath;
import static org.example.util.servlet.ServletGetUtil.actionGet;

@Slf4j
@WebServlet("/transact")
public class TransactionServlet extends HttpServlet {

	private final TransactionService transactionService = MenuDependency.TRANSACTION_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "transaction-menu");

		if (action == null)
			return;

		switch (action) {
			case "add" ->
					req.getRequestDispatcher(getPath("transaction", "transaction-add"))
							.forward(req, resp);
			case "update" ->
					req.getRequestDispatcher(getPath("transaction", "transaction-update"))
							.forward(req, resp);
			case "list" ->
					req.getRequestDispatcher(getPath("transaction", "transaction-list"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_TRANSACTION_MESSAGE);
				req.getRequestDispatcher(getPath("transaction", "transaction-delete"))
						.forward(req, resp);
			}
			case "back", "transaction-menu" -> req.getRequestDispatcher(getPath("transaction", "transaction-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}