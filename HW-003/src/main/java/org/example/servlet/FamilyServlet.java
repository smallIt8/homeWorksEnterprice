package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PersonDto;
import org.example.dto.FamilyDto;
import org.example.model.Family;
import org.example.service.FamilyService;
import org.example.util.MenuDependency;

import java.util.*;

import java.io.IOException;

import static org.example.mapper.FamilyMapper.modelToDto;
import static org.example.util.SessionUtil.presenceCurrentPersonDto;
import static org.example.util.constant.InfoMessageConstant.*;
import static org.example.helper.jsp.JspHelper.getPath;
import static org.example.helper.servlet.ServletHelper.*;

@Slf4j
@WebServlet("/family")
public class FamilyServlet extends HttpServlet {

	private final FamilyService familyService = MenuDependency.FAMILY_SERVICE;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var action = actionGet(req, resp, "family-menu");
		var currentPersonDto = presenceCurrentPersonDto(req, resp);

		switch (action) {
			case "add" -> req.getRequestDispatcher(getPath("family", "family-add"))
					.forward(req, resp);
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
				UUID familyId = UUID.fromString(req.getParameter("familyId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Family> familyOpt = familyService.findById(familyId, currentPersonId);

				if (familyOpt.isPresent()) {
					FamilyDto familyDto = modelToDto(familyOpt.get());

					req.setAttribute("family", familyDto);
					req.setAttribute("action", "update-family");
					req.getRequestDispatcher(getPath("family", "family-update"))
							.forward(req, resp);
				} else {
					log.warn("Семейная группа для обновления с ID '{}' не найдена для пользователя '{}'", familyId, currentPersonId);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
			}
			case "delete-family" -> {
				UUID familyId = UUID.fromString(req.getParameter("familyId"));
				UUID currentPersonId = UUID.fromString(String.valueOf(currentPersonDto.getPersonId()));

				Optional<Family> familyOpt = familyService.findById(familyId, currentPersonId);

				if (familyOpt.isPresent()) {
					FamilyDto familyDto = modelToDto(familyOpt.get());

					req.setAttribute("family", familyDto);
					req.setAttribute("action", "delete-family");
					req.setAttribute("warningMessage", WARNING_DELETE_FAMILY_MESSAGE);
					req.getRequestDispatcher(getPath("family", "family-delete"))
							.forward(req, resp);
				} else {
					log.warn("Семейная группа для удаления с ID '{}' не найдена для пользователя '{}'", familyId, currentPersonId);
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
				 currentPersonDto.toNameString(),
				 req.getServletPath(),
				 action
		);

		try {
			switch (action) {
				case "add-family" -> {
					FamilyDto familyAddDto = buildFamilyDto(req, true, false, currentPersonDto);
					familyService.create(familyAddDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				case "updated-family" -> {
					FamilyDto familyUpdateDto = buildFamilyDto(req, false, false, currentPersonDto);
					familyService.update(familyUpdateDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				case "deleted-family" -> {
					FamilyDto familyToDeleteDto = buildFamilyDto(req, false, true, currentPersonDto);
					familyService.delete(familyToDeleteDto, currentPersonDto);
					resp.sendRedirect(req.getContextPath() + "/family");
				}
				default -> resp.sendRedirect(req.getContextPath() + "/family");
			}
		} catch (Exception e) {
			log.error("Ошибка при обработке действия '{}' для пользователя '{}'", action, currentPersonDto.getUserName(), e);
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
					.name(name)
					.creatorDto(currentPersonDto)
					.build();
		} else {
			var familyId = UUID.fromString(req.getParameter("familyId"));
			return FamilyDto.builder()
					.familyId(familyId)
					.name(name)
					.creatorDto(currentPersonDto)
					.build();
		}
	}
}