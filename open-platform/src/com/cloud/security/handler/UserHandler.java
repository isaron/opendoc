package com.cloud.security.handler;

import java.util.Map;

import com.cloud.entity.EntityField;
import com.cloud.entity.EntityHandler;
import com.cloud.entity.EntityUtil;
import com.cloud.platform.Constants;
import com.cloud.platform.MD5Util;
import com.cloud.platform.StringUtil;

public class UserHandler extends EntityHandler {

	@Override
	public void beforeSave(Map<String, Object> paramMap) throws Exception {

		String originPassword = (String) paramMap.get("password");
		
		if(StringUtil.isNullOrEmpty(originPassword)) {
			paramMap.remove("password");
		} else {
			paramMap.put("password", MD5Util.MD5(originPassword));
		}
		
		super.beforeSave(paramMap);
	}

	@Override
	public void initListPage() {
		
		viewPage = "account/structure";
		
		super.initListPage();
	}

	@Override
	public void initFormFields() throws Exception {
		
		EntityField username = new EntityField("username", "用户名", EntityUtil.HTML_TEXT, true);
		username.setValidate_no_repeat(Constants.VALID_YES);
		
		fields.add(username);
		fields.add(new EntityField("password", "密码", EntityUtil.HTML_PASSWORD, true));
		fields.add(new EntityField("departmentId", "所属部门", "com.cloud.security.model.Department", false, true));
		fields.add(new EntityField("positionId", "职位", "com.cloud.security.model.Position", false, true));
		fields.add(new EntityField("email", "Email", EntityUtil.HTML_TEXT, false));
		fields.add(new EntityField("address", "地址", EntityUtil.HTML_TEXT, false));
		fields.add(new EntityField("roleIds", "角色", EntityUtil.HTML_CHECKBOX, "com.cloud.security.model.Role", false));
		
		super.initFormFields();
	}
}
