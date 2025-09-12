package org.example.util.jsp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspStartHelper {
	private final String PATTERN_PATH = "jsp/start/menu/%s.jsp";

	public String getPath(String jspName) {
		return String.format(PATTERN_PATH, jspName);
	}
}