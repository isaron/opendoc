package com.cloud.platform;

import java.util.List;


public class StringUtil {

	/**
	 * check string if is null or empty
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isNullOrEmpty(String... strs) {
		boolean flag = false;
		
		for(String s : strs) {
			if(s == null || "".equals(s.trim())) {
				flag = true;
				break;
			}
		}
		
		return flag; 
	}
	
	/**
	 * combine a list data to a String connect by comma ","
	 * 
	 * @param datas
	 * @return
	 */
	public static String combineByComma(List datas) {
		
		if(datas == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(Object o : datas) {
			
			if(sb.length() != 0) {
				sb.append(",");
			}
			
			sb.append((String) o);
		}
		
		return sb.toString();
	}
	
	/**
	 * ========================  text filter  ========================
	 */
	
	public static String filterHtmlTag(String text) {
		
		return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
	}
}
