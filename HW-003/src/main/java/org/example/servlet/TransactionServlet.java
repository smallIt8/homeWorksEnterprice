package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.TransactionDto;
import org.example.dto.CategoryDto;
import org.example.model.Transaction;
import org.example.model.TransactionType;
import org.example.service.TransactionService;
import org.example.util.MenuDependency;

import java.util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.example.mapper.TransactionMapper.modelToDto;
import static org.example.util.SessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.*;

@Slf4j
@WebServlet("/transact")
public class TransactionServlet extends HttpServlet {

	private final TransactionService transactionService = MenuDependency.TRANSACTION_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "transaction-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				String type = req.getParameter("type");
				req.setAttribute("includeFrom", "transact");

				if (type == null) {
					type = "EXPENSE";
				}

				req.setAttribute("type", type);
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
				UUID transactionId = UUID.fromString(req.getParameter("transactionId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Transaction> transactionOpt = transactionService.findById(transactionId, currentPersonId);

				if (transactionOpt.isPresent()) {
					TransactionDto transactionDto = modelToDto(transactionOpt.get());

					String type = req.getParameter("type");
					if (type != null) {
						transactionDto.setType(TransactionType.valueOf(type));
					}

					req.getSession().setAttribute("currentTransaction", transactionDto);

					TransactionType typeForCategories = transactionDto.getType();

					req.setAttribute("transaction", transactionDto);
					req.setAttribute("includeFrom", "transact");
					req.setAttribute("type", typeForCategories.name());
					req.getRequestDispatcher("/category?action=list&type=" + typeForCategories)
							.include(req, resp);
					req.setAttribute("action", "update-transact");
					req.getRequestDispatcher(getPath("transaction", "transaction-update"))
							.forward(req, resp);
				} else {
					log.warn("Транзакция для обновления с ID '{}' не найдена для пользователя '{}'", transactionId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
			}
			case "delete-transact" -> {
				UUID transactionId = UUID.fromString(req.getParameter("transactionId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Transaction> transactionOpt = transactionService.findById(transactionId, currentPersonId);

				if (transactionOpt.isPresent()) {
					TransactionDto transactionDto = modelToDto(transactionOpt.get());

					req.setAttribute("transaction", transactionDto);
					req.setAttribute("action", "delete-transact");
					req.setAttribute("warningMessage", WARNING_DELETE_TRANSACTION_MESSAGE);
					req.getRequestDispatcher(getPath("transaction", "transaction-delete"))
							.forward(req, resp);
				} else {
					log.warn("Транзакция для удаления с ID '{}' не найдена для пользователя '{}'", transactionId, currentPersonId);
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
				 currentPersonDto.toNameString(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "add-transact" -> {
					TransactionDto transactionAddDto = buildTransactionDto(req, true, false, currentPersonDto);
					transactionService.create(transactionAddDto);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				case "updated-transact" -> {
					TransactionDto transactionUpdateDto = buildTransactionDto(req, false, false, currentPersonDto);
					transactionService.update(transactionUpdateDto, currentPersonDto);
//					req.getSession().removeAttribute("currentTransaction");
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				case "deleted-transact" -> {
					TransactionDto transactionToDeleteDto = buildTransactionDto(req, false, true, currentPersonDto);
					transactionService.delete(transactionToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/transact");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/transact");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'", action, currentPersonDto.getUserName(), e);
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
		var amount = new BigDecimal(req.getParameter("amount"));
		var transactionDate = LocalDate.parse(req.getParameter("transactionDate"));

		if (isAdd) {
			return TransactionDto.builder()
					.name(name)
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
					.name(name)
					.type(type)
					.categoryDto(categoryDto)
					.amount(amount)
					.transactionDate(transactionDate)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}