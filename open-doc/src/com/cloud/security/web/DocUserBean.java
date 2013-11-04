package com.cloud.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloud.doc.service.DocStoreService;
import com.cloud.security.model.Department;

@Controller
@RequestMapping("user")
public class DocUserBean extends UserBean {

	@Autowired
	private DocStoreService docStoreService;

	@Override
	@RequestMapping("/saveDepart.do")
	public String saveDepart(Department depart) {

		String view = super.saveDepart(depart);
		
		docStoreService.saveDepartDir(depart);
		
		return view;
	}

	@Override
	public String removeDepart(String departId) {
		
		docStoreService.remove(departId, true);
		
		return super.removeDepart(departId);
	}
}
