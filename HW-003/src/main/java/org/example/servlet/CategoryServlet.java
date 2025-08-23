package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.service.CategoryService;
import org.example.util.MenuDependency;

import java.io.IOException;

import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_CATEGORY_MESSAGE;
import static org.example.util.jsp.JspHelper.getPath;
import static org.example.util.servlet.ServletGetUtil.actionGet;

@Slf4j
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

	private final CategoryService categoryService = MenuDependency.CATEGORY_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "category-menu");

		if (action == null)
			return;

		switch (action) {
			case "add" ->
					req.getRequestDispatcher(getPath("category", "category-add"))
							.forward(req, resp);
			case "update" ->
					req.getRequestDispatcher(getPath("category", "category-update"))
							.forward(req, resp);
			case "list" ->
					req.getRequestDispatcher(getPath("transaction", "category-list"))
							.forward(req, resp);
			case "delete" -> {
				req.setAttribute("warningMessage", WARNING_DELETE_CATEGORY_MESSAGE);
				req.getRequestDispatcher(getPath("category", "category-delete"))
						.forward(req, resp);
			}
			case "back", "category-menu" -> req.getRequestDispatcher(getPath("category", "category-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}