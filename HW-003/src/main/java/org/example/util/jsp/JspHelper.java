package org.example.util.jsp;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
	private final String PATTERN_PATH = "WEB-INF/jsp/menu/%s/%s.jsp";

	public String getPath(String packageName, String jspName) {
		return String.format(PATTERN_PATH, packageName, jspName);
	}
}