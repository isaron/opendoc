package com.cloud.doc.util;

import com.cloud.platform.StringUtil;

public class DocUtil {

	public static final String DEFAULT_DIR_NAME = "新建文件夹";
	
	public static final int DOC_STATUS_NORMAL = 0;
	public static final int DOC_STATUS_CHECKOUT = 1;
	
	/**
	 * get doc file version
	 * 
	 * @param isNew
	 * @return
	 */
	public static String getVersion(boolean isNew, String version) {
		
		if(isNew || StringUtil.isNullOrEmpty(version)) {
			return "A.1";
		}
		
		String[] vs = version.split("\\.");
		int count = Integer.parseInt(vs[1]);
		
		return vs[0] + "." + count;
	}
}
