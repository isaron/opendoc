package com.cloud.security.handler;

import com.cloud.entity.EntityField;
import com.cloud.entity.EntityHandler;
import com.cloud.entity.EntityUtil;

public class PositionHandler extends EntityHandler {
	
	@Override
	public void initBase() {

		modelName = "职位";
		
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
		fields.add(new EntityField("intro", "描述", EntityUtil.HTML_TEXTAREA, true));
		
		super.initFormFields();
	}
}
