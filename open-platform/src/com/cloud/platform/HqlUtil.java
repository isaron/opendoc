package com.cloud.platform;

import java.util.Set;

public class HqlUtil {
	
	/**
	 * combine hql in string
	 * 
	 * @param strs
	 * @param isString
	 * @return
	 */
	public static String combineInHql(String field, Set<String> strSet, boolean isString, boolean isAnd) {
		
		if(strSet == null || strSet.size() == 0) {
			return isAnd ? " 1 = 1 " : " 1 != 1 ";
		}
		
		StringBuffer strs = new StringBuffer();
		
		for(String s : strSet) {
			strs.append(s + ",");
		}
		strs.deleteCharAt(strs.length() - 1);
		
		return combineInHql(field, strs.toString(), isString, isAnd);
	}

	/**
	 * combine hql in string
	 * 
	 * @param strs
	 * @return
	 */
	public static String combineInHql(String field, String strs, boolean isString, boolean isAnd) {
		
		if(StringUtil.isNullOrEmpty(strs)) {
			return isAnd ? " 1 = 1 " : " 1 != 1 ";
		}
		
		StringBuffer sb = new StringBuffer(" " + field + " in (");
		
		for(String s : strs.split(",")) {
			
			if(StringUtil.isNullOrEmpty(s)) {
				continue;
			}
			
			if(isString) {
				sb.append("'" + s + "'");
			} else {
				sb.append(s);
			}
			
			sb.append(",");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		
		return sb.toString();
	}
}
