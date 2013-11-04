package com.cloud.platform;

import java.util.regex.Pattern;

public class RegexUtil {

	public static final String REGEX_MAIL = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+";
	
	/**
	 * if text if email format
	 * 
	 * @param text
	 * @return
	 */
	public static boolean emailFormat(String text) {
		return match(REGEX_MAIL, text);
	}
	
	/**
	 * if is match
	 * 
	 * @param regex
	 * @param text
	 * @return
	 */
	public static boolean match(String regex, String text) {
		return Pattern.matches(regex, text);
	}
}
