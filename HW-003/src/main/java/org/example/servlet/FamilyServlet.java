package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.FamilyDto;
import org.example.mapper.FamilyMapper;
import org.example.model.Family;
import org.example.service.FamilyService;
import org.example.util.MenuDependency;
import org.example.util.ValidationDtoUtil;

import java.util.*;

import java.io.IOException;

import static org.example.util.ServletSessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.WARNING_DELETE_FAMILY_MESSAGE;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.actionGet;

@Slf4j
@RequiredArgsConstructor
@WebServlet("/family")
public class FamilyServlet extends HttpServlet {

	private final FamilyService familyService = MenuDependency.familyService();
	private final FamilyMapper familyMapper = MenuDependency.familyMapper();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "family-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> {
				var familyInput = req.getSession().getAttribute("familyInput");
				var familyWarns = req.getSession().getAttribute("familyWarns");

				if (familyInput != null) {
					req.setAttribute("family", familyInput);
					req.getSession().removeAttribute("familyInput");
				}
				if (familyWarns != null) {
					req.setAttribute("warn", familyWarns);
					req.getSession().removeAttribute("familyWarns");
				}

				req.getRequestDispatcher(getPath("family", "family-add"))
						.forward(req, resp);
			}
			case "list", "update", "delete" -> {
				var families = familyService.findAllOwnerFamily(currentPersonDto);
				req.setAttribute("families", families);

				switch (action) {
					case "list" -> req.getRequestDispatcher(getPath("family", "family-list"))
							.forward(req, resp);
					case "update" -> req.getRequestDispatcher(getPath("family", "family-update"))
							.forward(req, resp);
					case "delete" -> req.getRequestDispatcher(getPath("family", "family-delete"))
							.forward(req, resp);
				}
			}
			case "update-family" -> {
				var familyId = UUID.fromString(req.getParameter("familyId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				var familyInput = req.getSession().getAttribute("familyInput");
				var familyWarns = req.getSession().getAttribute("familyWarns");

				if (familyInput != null) {
					req.setAttribute("family", familyInput);
					req.getSession().removeAttribute("familyInput");
				} else {
					Optional<Family> familyOpt = familyService.findById(familyId, currentPersonId);
					if (familyOpt.isPresent()) {
						req.setAttribute("family", familyMapper.mapModelToDto(familyOpt.get()));
					} else {
						log.warn("Семейная группа для обновления с ID '{}' не найдена для пользователя '{}'",
								 familyId,
								 currentPersonId);
						resp.sendRedirect(req.getContextPath() + "/family");
						return;
					}
				}

				if (familyWarns != null) {
					req.setAttribute("warn", familyWarns);
					req.getSession().removeAttribute("familyWarns");
				}

				req.setAttribute("action", "update-family");
				req.getRequestDispatcher(getPath("family", "family-update"))
						.forward(req, resp);
			}
			case "delete-family" -> {
				var familyId = UUID.fromString(req.getParameter("familyId"));
				var currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Family> familyOpt = familyService.findById(familyId, currentPersonId);

				if (familyOpt.isPresent()) {
					FamilyDto familyDto = familyMapper.mapModelToDto(familyOpt.get());

					req.setAttribute("family", familyDto);
					req.setAttribute("action", "delete-family");
					req.setAttribute("warningMessage", WARNING_DELETE_FAMILY_MESSAGE);
					req.getRequestDispatcher(getPath("family", "family-delete"))
							.forward(req, resp);
				} else {
					log.warn("Семейная группа для удаления с ID '{}' не найдена для пользователя '{}'",
							 familyId,
							 currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
			}
			case "back", "family-menu" -> req.getRequestDispatcher(getPath("family", "family-menu"))
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
				case "add-family" -> {
					var familyAddDto = buildFamilyDto(req, true, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(familyAddDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("familyInput", familyAddDto);
						req.getSession().setAttribute("familyWarns", warns);
						resp.sendRedirect(req.getContextPath() + "/family?action=add");
						return;
					}

					familyService.create(familyAddDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				case "updated-family" -> {
					var familyUpdateDto = buildFamilyDto(req, false, false, currentPersonDto);
					var warns = ValidationDtoUtil.validateAnnotation(familyUpdateDto);

					if (!warns.isEmpty()) {
						req.getSession().setAttribute("familyInput", familyUpdateDto);
						req.getSession().setAttribute("familyWarns", warns);

						var familyId = req.getParameter("familyId");
						resp.sendRedirect(req.getContextPath() + "/family?action=update-family&familyId=" + familyId);
						return;
					}
					familyService.update(familyUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				case "deleted-family" -> {
					var familyToDeleteDto = buildFamilyDto(req, false, true, currentPersonDto);
					familyService.delete(familyToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/family");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'",
					  action,
					  currentPersonDto.getUserName(),
					  e);
			req.getRequestDispatcher(getPath("family", "family-menu"))
					.forward(req, resp);
			req.setAttribute("errorMessage", e.getMessage());
		}
	}

	private FamilyDto buildFamilyDto(HttpServletRequest req, boolean isAdd, boolean isDelete, PersonDto currentPersonDto) {
		if (isDelete) {
			var familyId = UUID.fromString(req.getParameter("familyId"));
			return FamilyDto.builder()
					.familyId(familyId)
					.build();
		}

		var name = req.getParameter("name");

		if (isAdd) {
			return FamilyDto.builder()
					.familyName(name)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var familyId = UUID.fromString(req.getParameter("familyId"));
			return FamilyDto.builder()
					.familyId(familyId)
					.familyName(name)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}
