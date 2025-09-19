package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.dto.CategoryDto;
import org.example.mapper.TransactionMapper;
import org.example.model.Transaction;
import org.example.model.TransactionType;
import org.example.service.TransactionService;
import org.example.util.DependencyInjection;
import org.example.util.ValidationDtoUtil;

import java.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_TRANSACTION_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@RequiredArgsConstructor
@WebServlet("/transact")
public class TransactionServlet extends HttpServlet {

	private final TransactionService transactionService = DependencyInjection.transactionService();
	private final TransactionMapper transactionMapper = DependencyInjection.transactionMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "transaction-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				var type = req.getParameter("type");
				req.setAttribute("includeFrom", "transact");

				if (type == null) {
					type = "EXPENSE";
				}

				req.setAttribute("type", type);

				var transactionInput = req.getSession().getAttribute("transactionInput");
				var transactionWarns = req.getSession().getAttribute("transactionWarns");

				if (transactionInput != null) {
					req.setAttribute("transaction", transactionInput);
					req.getSession().removeAttribute("transactionInput");
				}
				if (transactionWarns != null) {
					req.setAttribute("warn", transactionWarns);
					req.getSession().removeAttribute("transactionWarns");
				}

				req.getRequestDispatcher("/category?action=list&type=" + type)
						.include(req, resp);
				req.setAttribute("action", "add");
				req.getRequestDispatcher(getPath("transaction", "transaction-add"))
						.forward(req, resp);
			}
			case "list", "update", "delete" -> {
				var transactions = transactionService.findAll(currentPersonDto);
				req.setAttribute("transactions", transactions);

				switch (action) {
					case "list" -> req.getRequestDispatcher(getPath("transaction", "transaction-list"))
							.forward(req, resp);
					case "update" -> req.getRequestDispatcher(getPath("transaction", "transaction-update"))
							.forward(req, resp);
					case "delete" -> req.getRequestDispatcher(getPath("transaction", "transaction-delete"))
							.forward(req, resp);
				}
			}
			case "update-transact" -> {
				var transactionId = UUID.fromString(req.getParameter("transactionId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				var transactionInput = req.getSession().getAttribute("transactionInput");
				var transactionWarns = req.getSession().getAttribute("transactionWarns");

				TransactionDto transactionDto;

				if (transactionInput != null) {
					transactionDto = (TransactionDto) transactionInput;
					req.getSession().removeAttribute("transactionInput");
				} else {
					Optional<Transaction> transactionOpt = transactionService.findById(transactionId, currentPersonId);
					if (transactionOpt.isPresent()) {
						transactionDto = transactionMapper.mapModelToDto(transactionOpt.get());

						var type = req.getParameter("type");
						if (type != null) transactionDto.setType(TransactionType.valueOf(type));

						req.getSession().setAttribute("currentTransaction", transactionDto);
					} else {
						log.warn("Транзакция для обновления с ID '{}' не найдена для пользователя '{}'",
								 transactionId, currentPersonId);
						resp.sendRedirect(req.getContextPath() + "/transact");
						return;
					}
				}

				if (transactionWarns != null) {
					req.setAttribute("warn", transactionWarns);
					req.getSession().removeAttribute("transactionWarns");
				}

					TransactionType typeForCategories = transactionDto.getType();

					req.setAttribute("transaction", transactionDto);
					req.setAttribute("includeFrom", "transact");
					req.setAttribute("type", typeForCategories.name());
					req.getRequestDispatcher("/category?action=list&type=" + typeForCategories)
							.include(req, resp);
					req.setAttribute("action", "update-transact");
					req.getRequestDispatcher(getPath("transaction", "transaction-update"))
							.forward(req, resp);
			}
			case "delete-transact" -> {
				var transactionId = UUID.fromString(req.getParameter("transactionId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Transaction> transactionOpt = transactionService.findById(transactionId, currentPersonId);

				if (transactionOpt.isPresent()) {
					TransactionDto transactionDto = transactionMapper.mapModelToDto(transactionOpt.get());

					req.setAttribute("transaction", transactionDto);
					req.setAttribute("action", "delete-transact");
					req.setAttribute("warningMessage", WARNING_DELETE_TRANSACTION_MESSAGE);
					req.getRequestDispatcher(getPath("transaction", "transaction-delete"))
							.forward(req, resp);
				} else {
					log.warn("Транзакция для удаления с ID '{}' не найдена для пользователя '{}'",
							 transactionId,
							 currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
			}
			case "back", "transaction-menu" -> req.getRequestDispatcher(getPath("transaction", "transaction-menu"))
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
				case "add-transact" -> {
					var transactionAddDto = buildTransactionDto(req, true, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(transactionAddDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("transactionInput", transactionAddDto);
						req.getSession().setAttribute("transactionWarns", warns);

						var type = req.getParameter("type");

						if (type == null) {
							type = transactionAddDto.getType() != null ? transactionAddDto.getType().name() : "EXPENSE";
//							type = "EXPENSE";
						}

						resp.sendRedirect(req.getContextPath() + "/transact?action=add&type=" + type);
						return;
					}
					transactionService.create(transactionAddDto);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				case "updated-transact" -> {
					var transactionUpdateDto = buildTransactionDto(req, false, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(transactionUpdateDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("transactionInput", transactionUpdateDto);
						req.getSession().setAttribute("transactionWarns", warns);

						var transactionId = req.getParameter("transactionId");
						var type = req.getParameter("type");

						if (type == null) {
							type = transactionUpdateDto.getType() != null ? transactionUpdateDto.getType().name() : "EXPENSE";
						}

						resp.sendRedirect(req.getContextPath() + "/transact?action=update-transact&transactionId="
												  + transactionId + "&type=" + type);
						return;
					}

					transactionService.update(transactionUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				case "deleted-transact" -> {
					var transactionToDeleteDto = buildTransactionDto(req, false, true, currentPersonDto);
					transactionService.delete(transactionToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/transact");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
			req.getRequestDispatcher(getPath("transaction", "transaction-menu"))
					.forward(req, resp);
			req.setAttribute("errorMessage", e.getMessage());
		}
	}

	private TransactionDto buildTransactionDto(HttpServletRequest req, boolean isAdd, boolean isDelete, PersonDto currentPersonDto) {
		if (isDelete) {
			var transactionId = UUID.fromString(req.getParameter("transactionId"));
			return TransactionDto.builder()
					.transactionId(transactionId)
					.build();
		}

		var name = req.getParameter("name");
		var type = TransactionType.valueOf(req.getParameter("type"));
		var categoryDto = CategoryDto.builder()
				.categoryId(UUID.fromString(req.getParameter("categoryId")))
				.build();
		var amountStr = req.getParameter("amount");
		BigDecimal amount = null;
		if (amountStr != null && !amountStr.isBlank()) {
			amount = new BigDecimal(amountStr);
		}

		var transactionDateStr = req.getParameter("transactionDate");
		LocalDate transactionDate = null;
		if (transactionDateStr != null && !transactionDateStr.isBlank()) {
			transactionDate = LocalDate.parse(transactionDateStr);
		}

		if (isAdd) {
			return TransactionDto.builder()
					.transactionName(name)
					.type(type)
					.categoryDto(categoryDto)
					.amount(amount)
					.transactionDate(transactionDate)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var transactionId = UUID.fromString(req.getParameter("transactionId"));
			return TransactionDto.builder()
					.transactionId(transactionId)
					.transactionName(name)
					.type(type)
					.categoryDto(categoryDto)
					.amount(amount)
					.transactionDate(transactionDate)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}
