package com.cloud.security.handler;

import com.cloud.entity.EntityField;
import com.cloud.entity.EntityHandler;

public class DepartmentHandler extends EntityHandler {

	@Override
	public void initListFields() {

		fields.add(new EntityField("name", "名称", 400));
		
		super.initListFields();
	}

}
