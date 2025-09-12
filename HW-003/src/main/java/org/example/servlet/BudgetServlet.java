package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BudgetDto;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.model.Budget;
import org.example.service.BudgetService;
import org.example.util.MenuDependency;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

import static org.example.mapper.BudgetMapper.modelToDto;
import static org.example.util.SessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_BUDGET_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {

	private final BudgetService budgetService = MenuDependency.budgetService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "budget-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				String type = req.getParameter("type");
				req.setAttribute("includeFrom", "budget");

				if (type == null) {
					type = "EXPENSE";
				}

				req.setAttribute("type", type);
				req.getRequestDispatcher("/category?action=list&type=" + type)
						.include(req, resp);
				req.setAttribute("action", "add");
				req.getRequestDispatcher(getPath("budget", "budget-add"))
						.forward(req, resp);
			}
			case "list", "update", "delete" -> {
				var budgets = budgetService.findAll(currentPersonDto);
				req.setAttribute("budgets", budgets);

				switch (action) {
					case "list" -> req.getRequestDispatcher(getPath("budget", "budget-list"))
							.forward(req, resp);
					case "update" -> req.getRequestDispatcher(getPath("budget", "budget-update"))
							.forward(req, resp);
					case "delete" -> req.getRequestDispatcher(getPath("budget", "budget-delete"))
							.forward(req, resp);
				}
			}
			case "update-budget" -> {
				UUID budgetId = UUID.fromString(req.getParameter("budgetId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Budget> budgetOpt = budgetService.findById(budgetId, currentPersonId);

				if (budgetOpt.isPresent()) {
					BudgetDto budgetDto = modelToDto(budgetOpt.get());

					String type = req.getParameter("type");
					req.setAttribute("includeFrom", "budget");

					if (type == null) {
						type = "EXPENSE";
					}

					req.setAttribute("budget", budgetDto);
					req.setAttribute("includeFrom", "budget");
					req.setAttribute("type", type);
					req.getRequestDispatcher("/category?action=list&type=" + type)
							.include(req, resp);
					req.setAttribute("action", "update-budget");
					req.getRequestDispatcher(getPath("budget", "budget-update"))
							.forward(req, resp);
				} else {
					log.warn("Бюджет для обновления с ID '{}' не найдена для пользователя '{}'", budgetId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
			}
			case "delete-budget" -> {
				UUID budgetId = UUID.fromString(req.getParameter("budgetId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Budget> budgetOpt = budgetService.findById(budgetId, currentPersonId);

				if (budgetOpt.isPresent()) {
					BudgetDto budgetDto = modelToDto(budgetOpt.get());

					req.setAttribute("budget", budgetDto);
					req.setAttribute("action", "delete-budget");
					req.setAttribute("warningMessage", WARNING_DELETE_BUDGET_MESSAGE);
					req.getRequestDispatcher(getPath("budget", "budget-delete"))
							.forward(req, resp);
				} else {
					log.warn("Бюджет для удаления с ID '{}' не найдена для пользователя '{}'", budgetId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
			}
			case "back", "budget-menu" -> req.getRequestDispatcher(getPath("budget", "budget-menu"))
					.forward(req, resp);
			default -> resp.sendRedirect(req.getContextPath() + "/main-person");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = req.getParameter("action");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		log.info("Пользователь '{}' в меню '{}' выбирает действие '{}'",
				 currentPersonDto.toNameString(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "add-budget" -> {
					BudgetDto budgetAddDto = buildBudgetDto(req, true, false, currentPersonDto);
					budgetService.create(budgetAddDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				case "updated-budget" -> {
					BudgetDto budgetUpdateDto = buildBudgetDto(req, false, false, currentPersonDto);
					budgetService.update(budgetUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				case "deleted-budget" -> {
					BudgetDto budgetToDeleteDto = buildBudgetDto(req, false, true, currentPersonDto);
					budgetService.delete(budgetToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/budget");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'", action, currentPersonDto.getUserName(), e);
			req.getRequestDispatcher(getPath("budget", "budget-menu"))
					.forward(req, resp);
			req.setAttribute("errorMessage", e.getMessage());
		}
	}

	private BudgetDto buildBudgetDto(HttpServletRequest req, boolean isAdd, boolean isDelete, PersonDto currentPersonDto) {
		if (isDelete) {
			var budgetId = UUID.fromString(req.getParameter("budgetId"));
			return BudgetDto.builder()
					.budgetId(budgetId)
					.build();
		}

		var name = req.getParameter("name");
		var categoryDto = CategoryDto.builder()
				.categoryId(UUID.fromString(req.getParameter("categoryId")))
				.build();
		var limit = new BigDecimal(req.getParameter("limit"));
		var period = YearMonth.parse(req.getParameter("period"));

		if (isAdd) {
			return BudgetDto.builder()
					.name(name)
					.categoryDto(categoryDto)
					.limit(limit)
					.period(YearMonth.from(period))
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var transactionId = UUID.fromString(req.getParameter("budgetId"));
			return BudgetDto.builder()
					.budgetId(transactionId)
					.name(name)
					.categoryDto(categoryDto)
					.limit(limit)
					.period(YearMonth.from(period))
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}