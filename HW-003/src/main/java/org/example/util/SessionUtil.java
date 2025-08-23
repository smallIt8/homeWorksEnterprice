package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.example.dto.PersonDto;

import java.io.IOException;

import static org.example.util.constant.InfoMessageConstant.NOT_FOUND_TRANSACTION_MESSAGE;

@UtilityClass
public class SessionUtil {

	public static PersonDto presenceCurrentPersonDto(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PersonDto currentPersonDto = getCurrentPersonDto(req);
		if (currentPersonDto == null) {
			resp.sendRedirect(req.getContextPath() + "/start");
			throw new IllegalStateException(NOT_FOUND_TRANSACTION_MESSAGE);
		}
		return currentPersonDto;
	}

	public static PersonDto getCurrentPersonDto(HttpServletRequest req) {
		Object personSession = req.getSession().getAttribute("currentPersonDto");
		if (personSession instanceof PersonDto currentPersonDto) {
			return currentPersonDto;
		}
		return null;
	}
}