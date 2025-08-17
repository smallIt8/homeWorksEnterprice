package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.example.dto.PersonDto;

import java.io.IOException;

@UtilityClass
public class SessionUtil {

	public static PersonDto getCurrentPersonDto(HttpServletRequest req) {

		Object personSession = req.getSession().getAttribute("currentPersonDto");
		if (personSession instanceof PersonDto personDto) {
			return personDto;
		}
		return null;
	}

	public static PersonDto presenceCurrentPersonDto(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersonDto personDto = getCurrentPersonDto(req);
		if (personDto == null) {
			resp.sendRedirect(req.getContextPath() + "/start");
		}
		return personDto;
	}
}