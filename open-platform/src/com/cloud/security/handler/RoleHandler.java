package com.cloud.security.handler;

import com.cloud.entity.EntityField;
import com.cloud.entity.EntityHandler;
import com.cloud.entity.EntityUtil;
import com.cloud.security.config.MyInvocationSecurityMetadataSource;
import com.cloud.security.service.RoleService;

public class RoleHandler extends EntityHandler {
	
	@Override
	public void initBase() {
		
		modelName = "角色";
		
		super.initBase();
	}

	@Override
	public void initListFields() {
		
		fields.add(new EntityField("name", "名称", 400));
		fields.add(new EntityField("intro", "描述", 400));
		
		super.initListFields();
	}
	
	@Override
	public void initFormFields() throws Exception {
		
		fields.add(new EntityField("name", "名称", EntityUtil.HTML_TEXT, true));
		fields.add(new EntityField("intro", "描述", EntityUtil.HTML_TEXT, false));
		fields.add(new EntityField("resourceIds", "关联资源", EntityUtil.HTML_TEXT, false));
		
		super.initFormFields();
	}

	@Override
	public void initFormPage() {
		
		viewPage = "account/roleAdd";
		
		super.initFormPage();
	}

	@Override
	public void afterSave(Object modelObj) {
		// reset spring-security resource map
		MyInvocationSecurityMetadataSource.resetResourceMap();
		
		// reset user resouce map
		RoleService.userResStrMap = null;
		
		super.afterSave(modelObj);
	}

	@Override
	public void afterRemove() {
		// reset spring-security resource map
		MyInvocationSecurityMetadataSource.resetResourceMap();
		
		// reset user resouce map
		RoleService.userResStrMap = null;
		
		super.afterRemove();
	}
}
