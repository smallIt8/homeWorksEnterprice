package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.FinancialGoalDto;
import org.example.model.FinancialGoal;
import org.example.service.FinancialGoalService;
import org.example.util.MenuDependency;

import java.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.mapper.FinancialGoalMapper.modelToDto;
import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.*;

@Slf4j
@WebServlet("/financial-goal")
public class FinancialGoalServlet extends HttpServlet {

	private final FinancialGoalService financialGoalService = MenuDependency.financialGoalService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "financial-goal-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> req.getRequestDispatcher(getPath("financialGoal", "financial-goal-add"))
					.forward(req, resp);
			case "list", "update", "delete" -> {
				var financialGoals = financialGoalService.findAll(currentPersonDto);
				req.setAttribute("financialGoals", financialGoals);

				switch (action) {
					case "list" -> req.getRequestDispatcher(getPath("financialGoal", "financial-goal-list"))
							.forward(req, resp);
					case "update" -> req.getRequestDispatcher(getPath("financialGoal", "financial-goal-update"))
							.forward(req, resp);
					case "delete" -> req.getRequestDispatcher(getPath("financialGoal", "financial-goal-delete"))
							.forward(req, resp);
				}
			}
			case "update-financial-goal" -> {
				UUID financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<FinancialGoal> financialGoalOpt = financialGoalService.findById(financialGoalId, currentPersonId);

				if (financialGoalOpt.isPresent()) {
					FinancialGoalDto financialGoalDto = modelToDto(financialGoalOpt.get());

					req.setAttribute("financialGoal", financialGoalDto);
					req.setAttribute("action", "update-financial-goal");
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-update"))
							.forward(req, resp);
				} else {
					log.warn("Долгосрочная финансовая цель для обновления с ID '{}' не найдена для пользователя '{}'", financialGoalId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
			}
			case "delete-financial-goal" -> {
				UUID financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<FinancialGoal> financialGoalOpt = financialGoalService.findById(financialGoalId, currentPersonId);

				if (financialGoalOpt.isPresent()) {
					FinancialGoalDto financialGoalDto = modelToDto(financialGoalOpt.get());

					req.setAttribute("financialGoal", financialGoalDto);
					req.setAttribute("action", "delete-financial-goal");
					req.setAttribute("warningMessage", WARNING_DELETE_FINANCIAL_GOAL_MESSAGE);
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-delete"))
							.forward(req, resp);
				} else {
					log.warn("Долгосрочная финансовая цель для удаления с ID '{}' не найдена для пользователя '{}'", financialGoalId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
			}
			case "back", "financial-goal-menu" ->
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-menu"))
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
				case "add-financial-goal" -> {
					FinancialGoalDto financialGoalAddDto = buildFinancialGoalDto(req, true, false, currentPersonDto);
					financialGoalService.create(financialGoalAddDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				case "updated-financial-goal" -> {
					FinancialGoalDto financialGoalUpdateDto = buildFinancialGoalDto(req, false, false, currentPersonDto);
					financialGoalService.update(financialGoalUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				case "deleted-financial-goal" -> {
					FinancialGoalDto financialGoalToDeleteDto = buildFinancialGoalDto(req, false, true, currentPersonDto);
					financialGoalService.delete(financialGoalToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/financial-goal");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'", action, currentPersonDto.getUserName(), e);
			req.getRequestDispatcher(getPath("financialGoal", "financial-goal-menu"))
					.forward(req, resp);
			req.setAttribute("errorMessage", e.getMessage());
		}
	}

	private FinancialGoalDto buildFinancialGoalDto(HttpServletRequest req, boolean isAdd, boolean isDelete, PersonDto currentPersonDto) {
		if (isDelete) {
			var financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
			return FinancialGoalDto.builder()
					.financialGoalId(financialGoalId)
					.build();
		}

		var name = req.getParameter("name");
		var targetAmount = new BigDecimal(req.getParameter("targetAmount"));
		var endDate = LocalDate.parse(req.getParameter("endDate"));

		if (isAdd) {
			return FinancialGoalDto.builder()
					.name(name)
					.targetAmount(targetAmount)
					.endDate(endDate)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
			return FinancialGoalDto.builder()
					.financialGoalId(financialGoalId)
					.name(name)
					.targetAmount(targetAmount)
					.endDate(endDate)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}