package com.cloud.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.StringUtil;

@Controller
@RequestMapping("validate")
public class EntityValidate {
	
	@Autowired
	private IDao dao;

	/**
	 * validate field value no-repeat
	 * 
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/norepeat.do")
	public String validateNorepeat(@RequestParam("model") String model,
			@RequestParam("field") String field,
			@RequestParam("value") String value,
			@RequestParam("entityId") String entityId) {
		
		String hql = "from " + model + " where " + field + " = ?";
		
		if(!StringUtil.isNullOrEmpty(entityId)) {
			hql += " and id != '" + entityId + "'";
		}
		
		// specially deal with user, check valid user repeat
		if("com.cloud.security.model.User".equals(model)) {
			hql += " and isValid = 'Y'";
		}
		
		List list = dao.getAllByHql(hql, value);
		
		return list.isEmpty() ? Constants.VALID_YES : Constants.VALID_NO;
	}
}
