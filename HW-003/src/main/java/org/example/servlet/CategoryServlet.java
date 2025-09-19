package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.CategoryDto;
import org.example.mapper.CategoryMapper;
import org.example.model.Category;
import org.example.model.CategoryType;
import org.example.service.CategoryService;
import org.example.util.DependencyInjection;
import org.example.util.ValidationDtoUtil;

import java.util.*;

import java.io.IOException;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.EMPTY_LIST_CATEGORY_BY_PERSON_MESSAGE;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_CATEGORY_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@RequiredArgsConstructor
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

	private final CategoryService categoryService = DependencyInjection.categoryService();
	private final CategoryMapper categoryMapper = DependencyInjection.categoryMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "category-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				var backTo = req.getParameter("backTo");
				if (backTo != null) {
					req.setAttribute("backTo", backTo);
				}

				var categoryInput = req.getSession().getAttribute("categoryInput");
				var categoryWarns = req.getSession().getAttribute("categoryWarns");

				if (categoryInput != null) {
					req.setAttribute("category", categoryInput);
					req.getSession().removeAttribute("categoryInput");
				}
				if (categoryWarns != null) {
					req.setAttribute("warn", categoryWarns);
					req.getSession().removeAttribute("categoryWarns");
				}

				req.getRequestDispatcher(getPath("category", "category-add"))
						.forward(req, resp);
			}
			case "list", "update", "delete" -> {
				var categories = categoryService.findAll(currentPersonDto);
				var type = (String) req.getAttribute("type");

				if (type != null) {
					categories = categories.stream()
							.filter(c -> c.getType().name().equals(type))
							.toList();
				}

				if (categories.isEmpty()) {
					req.setAttribute("warningMessage", EMPTY_LIST_CATEGORY_BY_PERSON_MESSAGE);
				} else {
					req.setAttribute("categories", categories);
				}
				var includeFrom = (String) req.getAttribute("includeFrom");
				if ("transact".equals(includeFrom) || "budget".equals(includeFrom)) {
					return;
				}

				switch (action) {
					case "list" -> req.getRequestDispatcher(getPath("category", "category-list"))
							.forward(req, resp);
					case "update" -> req.getRequestDispatcher(getPath("category", "category-update"))
							.forward(req, resp);
					case "delete" -> req.getRequestDispatcher(getPath("category", "category-delete"))
							.forward(req, resp);
				}
			}
			case "update-category" -> {
				var categoryId = UUID.fromString(req.getParameter("categoryId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				var categoryInput = req.getSession().getAttribute("categoryInput");
				var categoryWarns = req.getSession().getAttribute("categoryWarns");

				if (categoryInput != null) {
					req.setAttribute("category", categoryInput);
					req.getSession().removeAttribute("categoryInput");
				} else {
					Optional<Category> categoryOpt = categoryService.findById(categoryId, currentPersonId);
					if (categoryOpt.isPresent()) {
						req.setAttribute("category", categoryMapper.mapModelToDto(categoryOpt.get()));
					} else {
						log.warn("Категория для обновления с ID '{}' не найдена для пользователя '{}'",
								 categoryId,
								 currentPersonId);
						resp.sendRedirect(req.getContextPath() + "/category");
						return;
					}
				}

				if (categoryWarns != null) {
					req.setAttribute("warn", categoryWarns);
					req.getSession().removeAttribute("categoryWarns");
				}

				req.setAttribute("action", "update-category");
				req.getRequestDispatcher(getPath("category", "category-update"))
						.forward(req, resp);
			}
			case "delete-category" -> {
				var categoryId = UUID.fromString(req.getParameter("categoryId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Category> categoryOpt = categoryService.findById(categoryId, currentPersonId);

				if (categoryOpt.isPresent()) {
					CategoryDto categoryDto = categoryMapper.mapModelToDto(categoryOpt.get());

					req.setAttribute("category", categoryDto);
					req.setAttribute("action", "delete-category");
					req.setAttribute("warningMessage", WARNING_DELETE_CATEGORY_MESSAGE);
					req.getRequestDispatcher(getPath("category", "category-delete"))
							.forward(req, resp);
				} else {
					log.warn("Категория для удаления с ID '{}' не найдена для пользователя '{}'",
							 categoryId,
							 currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/category");
				}
			}
			case "back", "category-menu" -> req.getRequestDispatcher(getPath("category", "category-menu"))
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
				case "add-category" -> {
					var backTo = req.getParameter("backTo");
					var categoryAddDto = buildCategoryDto(req, true, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(categoryAddDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("categoryInput", categoryAddDto);
						req.getSession().setAttribute("categoryWarns", warns);

						var type = req.getParameter("type");

						if (type == null) {
							type = "EXPENSE";
						}

						resp.sendRedirect(req.getContextPath() + "/category?action=add&type=" + type +
												  (backTo != null ? "&backTo=" + backTo : ""));
						return;
					}

					categoryService.create(categoryAddDto);

					if ("transact".equals(backTo)) {
						resp.sendRedirect(req.getContextPath() + "/transact?action=add");
					} else if ("budget".equals(backTo)) {
						resp.sendRedirect(req.getContextPath() + "/budget?action=add");
					} else {
						resp.sendRedirect(req.getContextPath() + "/category");
					}
				}
				case "updated-category" -> {
					var categoryUpdateDto = buildCategoryDto(req, false, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(categoryUpdateDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("categoryInput", categoryUpdateDto);
						req.getSession().setAttribute("categoryWarns", warns);

						var categoryId = req.getParameter("categoryId");
						resp.sendRedirect(req.getContextPath() + "/category?action=update-category&categoryId=" + categoryId);
						return;
					}

					categoryService.update(categoryUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/category");
				}
				case "deleted-category" -> {
					var categoryToDeleteDto = buildCategoryDto(req, false, true, currentPersonDto);
					categoryService.delete(categoryToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/category");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/category");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
			req.getRequestDispatcher(getPath("category", "category-menu"))
					.forward(req, resp);
			req.setAttribute("errorMessage", e.getMessage());
		}
	}

	private CategoryDto buildCategoryDto(HttpServletRequest req, boolean isAdd, boolean isDelete, PersonDto currentPersonDto) {
		if (isDelete) {
			var categoryId = UUID.fromString(req.getParameter("categoryId"));
			return CategoryDto.builder()
					.categoryId(categoryId)
					.build();
		}

		var name = req.getParameter("name");

		if (isAdd) {
			var type = CategoryType.valueOf(req.getParameter("type"));
			return CategoryDto.builder()
					.categoryName(name)
					.type(type)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var categoryId = UUID.fromString(req.getParameter("categoryId"));
			return CategoryDto.builder()
					.categoryId(categoryId)
					.categoryName(name)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}
