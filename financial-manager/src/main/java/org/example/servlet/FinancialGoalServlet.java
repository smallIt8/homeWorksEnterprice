package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.FinancialGoalDto;
import org.example.mapper.FinancialGoalMapper;
import org.example.model.FinancialGoal;
import org.example.service.FinancialGoalService;
import org.example.util.ValidationDtoUtil;
import org.springframework.stereotype.Component;

import java.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_FINANCIAL_GOAL_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@RequiredArgsConstructor
@Component
@WebServlet("/financial-goal")
public class FinancialGoalServlet extends HttpServlet {

	private final FinancialGoalService financialGoalService;
	private final FinancialGoalMapper financialGoalMapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "financial-goal-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				var financialGoalInput = req.getSession().getAttribute("financialGoalInput");
				var financialGoalWarns = req.getSession().getAttribute("financialGoalWarns");

				if (financialGoalInput != null) {
					req.setAttribute("financialGoal", financialGoalInput);
					req.getSession().removeAttribute("financialGoalInput");
				}

				if (financialGoalWarns != null) {
					req.setAttribute("warn", financialGoalWarns);
					req.getSession().removeAttribute("financialGoalWarns");
				}

				req.getRequestDispatcher(getPath("financialGoal", "financial-goal-add"))
						.forward(req, resp);
			}
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
				var financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				var financialGoalInput = req.getSession().getAttribute("financialGoalInput");
				var financialGoalWarns = req.getSession().getAttribute("financialGoalWarns");

				if (financialGoalInput != null) {
					req.setAttribute("financialGoal", financialGoalInput);
					req.getSession().removeAttribute("financialGoalInput");
				} else {
					Optional<FinancialGoal> financialGoalOpt = financialGoalService.findById(financialGoalId, currentPersonId);
					if (financialGoalOpt.isPresent()) {
						req.setAttribute("financialGoal", financialGoalMapper.mapModelToDto(financialGoalOpt.get()));
					} else {
						log.warn("Долгосрочная финансовая цель для обновления с ID '{}' не найдена для пользователя '{}'",
								 financialGoalId, currentPersonId);
						resp.sendRedirect(req.getContextPath() + "/financial-goal");
						return;
					}
				}

				if (financialGoalWarns != null) {
					req.setAttribute("warn", financialGoalWarns);
					req.getSession().removeAttribute("financialGoalWarns");
				}

				req.setAttribute("action", "update-financial-goal");
				req.getRequestDispatcher(getPath("financialGoal", "financial-goal-update"))
						.forward(req, resp);
			}
			case "delete-financial-goal" -> {
				var financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<FinancialGoal> financialGoalOpt = financialGoalService.findById(financialGoalId, currentPersonId);

				if (financialGoalOpt.isPresent()) {
					FinancialGoalDto financialGoalDto = financialGoalMapper.mapModelToDto(financialGoalOpt.get());

					req.setAttribute("financialGoal", financialGoalDto);
					req.setAttribute("action", "delete-financial-goal");
					req.setAttribute("warningMessage", WARNING_DELETE_FINANCIAL_GOAL_MESSAGE);
					req.getRequestDispatcher(getPath("financialGoal", "financial-goal-delete"))
							.forward(req, resp);
				} else {
					log.warn("Долгосрочная финансовая цель для удаления с ID '{}' не найдена для пользователя '{}'",
							 financialGoalId,
							 currentPersonId);
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
				 currentPersonDto.getFullName(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "add-financial-goal" -> {
					var financialGoalAddDto = buildFinancialGoalDto(req, true, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(financialGoalAddDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("financialGoalInput", financialGoalAddDto);
						req.getSession().setAttribute("financialGoalWarns", warns);
						resp.sendRedirect(req.getContextPath() + "/financial-goal?action=add");
						return;
					}

					financialGoalService.create(financialGoalAddDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				case "updated-financial-goal" -> {
					var financialGoalUpdateDto = buildFinancialGoalDto(req, false, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(financialGoalUpdateDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("financialGoalInput", financialGoalUpdateDto);
						req.getSession().setAttribute("financialGoalWarns", warns);

						var financialGoalId = req.getParameter("financialGoalId");
						resp.sendRedirect(req.getContextPath() + "/financial-goal?action=update-financial-goal&financialGoalId=" + financialGoalId);
						return;
					}

					financialGoalService.update(financialGoalUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				case "deleted-financial-goal" -> {
					var financialGoalToDeleteDto = buildFinancialGoalDto(req, false, true, currentPersonDto);
					financialGoalService.delete(financialGoalToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/financial-goal");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/financial-goal");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
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

		var targetAmountStr = req.getParameter("targetAmount");
		BigDecimal targetAmount = null;
		if (targetAmountStr != null && !targetAmountStr.isBlank()) {
			targetAmount = new BigDecimal(targetAmountStr);
		}

		var endDateStr = req.getParameter("endDate");
		LocalDate endDate = null;
		if (endDateStr != null && !endDateStr.isBlank()) {
			endDate = LocalDate.parse(endDateStr);
		}

		if (isAdd) {
			return FinancialGoalDto.builder()
					.financialGoalName(name)
					.targetAmount(targetAmount)
					.endDate(endDate)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var financialGoalId = UUID.fromString(req.getParameter("financialGoalId"));
			return FinancialGoalDto.builder()
					.financialGoalId(financialGoalId)
					.financialGoalName(name)
					.targetAmount(targetAmount)
					.endDate(endDate)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}
