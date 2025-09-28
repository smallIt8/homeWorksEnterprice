package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BudgetDto;
import org.example.dto.CategoryDto;
import org.example.dto.PersonDto;
import org.example.mapper.BudgetMapper;
import org.example.model.Budget;
import org.example.service.BudgetService;
import org.example.util.ValidationDtoUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_BUDGET_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@RequiredArgsConstructor
@Component
@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {

	private final BudgetService budgetService;
	private final BudgetMapper budgetMapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "budget-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				var type = req.getParameter("type");
				req.setAttribute("includeFrom", "budget");

				if (type == null) {
					type = "EXPENSE";
				}

				req.setAttribute("type", type);

				var budgetInput = req.getSession().getAttribute("budgetInput");
				var budgetWarns = req.getSession().getAttribute("budgetWarns");

				if (budgetInput != null) {
					req.setAttribute("budget", budgetInput);
					req.getSession().removeAttribute("budgetInput");
				}

				if (budgetWarns != null) {
					req.setAttribute("warn", budgetWarns);
					req.getSession().removeAttribute("budgetWarns");
				}

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
				var budgetId = UUID.fromString(req.getParameter("budgetId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				var budgetInput = req.getSession().getAttribute("budgetInput");
				var budgetWarns = req.getSession().getAttribute("budgetWarns");

				if (budgetInput != null) {
					req.setAttribute("budget", budgetInput);
					req.getSession().removeAttribute("budgetInput");
				} else {
					Optional<Budget> budgetOpt = budgetService.findById(budgetId, currentPersonId);
					if (budgetOpt.isPresent()) {
						req.setAttribute("budget", budgetMapper.mapModelToDto(budgetOpt.get()));
					} else {
						log.warn("Бюджет для обновления с ID '{}' не найдена для пользователя '{}'",
								 budgetId,
								 currentPersonId);
						resp.sendRedirect(req.getContextPath() + "/budget");
						return;
					}
				}

				if (budgetWarns != null) {
					req.setAttribute("warn", budgetWarns);
					req.getSession().removeAttribute("budgetWarns");
				}

				var type = req.getParameter("type");

				if (type == null) {
					type = "EXPENSE";
				}

				req.setAttribute("includeFrom", "budget");
				req.setAttribute("type", type);
				req.getRequestDispatcher("/category?action=list&type=" + type)
						.include(req, resp);
				req.setAttribute("action", "update-budget");
				req.getRequestDispatcher(getPath("budget", "budget-update"))
						.forward(req, resp);
			}
			case "delete-budget" -> {
				var budgetId = UUID.fromString(req.getParameter("budgetId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Budget> budgetOpt = budgetService.findById(budgetId, currentPersonId);

				if (budgetOpt.isPresent()) {
					BudgetDto budgetDto = budgetMapper.mapModelToDto(budgetOpt.get());

					req.setAttribute("budget", budgetDto);
					req.setAttribute("action", "delete-budget");
					req.setAttribute("warningMessage", WARNING_DELETE_BUDGET_MESSAGE);
					req.getRequestDispatcher(getPath("budget", "budget-delete"))
							.forward(req, resp);
				} else {
					log.warn("Бюджет для удаления с ID '{}' не найдена для пользователя '{}'",
							 budgetId,
							 currentPersonId);
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
				 currentPersonDto.getFullName(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "add-budget" -> {
					var budgetAddDto = buildBudgetDto(req, true, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(budgetAddDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("budgetInput", budgetAddDto);
						req.getSession().setAttribute("budgetWarns", warns);

						var type = req.getParameter("type");

						if (type == null) {
							type = "EXPENSE";
						}

						resp.sendRedirect(req.getContextPath() + "/budget?action=add&type=" + type);
						return;
					}

					budgetService.create(budgetAddDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				case "updated-budget" -> {
					var budgetUpdateDto = buildBudgetDto(req, false, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(budgetUpdateDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("budgetInput", budgetUpdateDto);
						req.getSession().setAttribute("budgetWarns", warns);

						var budgetId = req.getParameter("budgetId");
						var type = req.getParameter("type");

						if (type == null) {
							type = "EXPENSE";
						}

						resp.sendRedirect(req.getContextPath() + "/budget?action=update-budget&budgetId=" + budgetId + "&type=" + type);
						return;
					}

					budgetService.update(budgetUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				case "deleted-budget" -> {
					var budgetToDeleteDto = buildBudgetDto(req, false, true, currentPersonDto);
					budgetService.delete(budgetToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/budget");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/budget");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
			req.setAttribute("errorMessage", e.getMessage());
			req.getRequestDispatcher(getPath("budget", "budget-menu"))
					.forward(req, resp);
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

		var limitStr = req.getParameter("limit");
		BigDecimal limit = null;
		if (limitStr != null && !limitStr.isBlank()) {
			limit = new BigDecimal(limitStr);
		}

		var periodStr = req.getParameter("period");
		YearMonth period = null;
		if (periodStr != null && !periodStr.isBlank()) {
			period = YearMonth.parse(periodStr);
		}

		if (isAdd) {
			return BudgetDto.builder()
					.budgetName(name)
					.categoryDto(categoryDto)
					.limit(limit)
					.period(period)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var transactionId = UUID.fromString(req.getParameter("budgetId"));
			return BudgetDto.builder()
					.budgetId(transactionId)
					.budgetName(name)
					.categoryDto(categoryDto)
					.limit(limit)
					.period(period)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}
