package com.cloud.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import net.sf.json.JSONObject;

import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import com.cloud.platform.SpringUtil;
import com.cloud.platform.StringUtil;
import com.cloud.security.model.Department;
import com.cloud.security.model.Role;
import com.cloud.security.model.User;
import com.cloud.security.service.RoleService;

public class EntityHandler {

	public String model;
	public String modelName;
	public String operate;
	public String viewPage;
	public List<EntityField> fields = new ArrayList();
	public List datas;
	
	/**
	 * init entity base info
	 */
	public void initBase() {}
	
	/**
	 * init page field
	 */
	public void initFields() throws Exception {
		if(EntityUtil.OPERATE_LIST.equals(operate)) {
			initListFields();
		} else {
			initFormFields();
		}
	}
	
	public void initFormFields() throws Exception {
		
		if(fields == null || fields.isEmpty() || StringUtil.isNullOrEmpty(model)) {
			return;
		}
		
		// add max length validate for fields
		Class modelClz = Class.forName(model);
		Method getter = null;
		
		for(EntityField field : fields) {
			
			if (!EntityUtil.HTML_TEXT.equals(field.getHtmlType())
					&& !EntityUtil.HTML_TEXTAREA.equals(field.getHtmlType())) {
				continue;
			}
			
			try {
				getter = modelClz.getMethod("get"
						+ field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
			} catch(NoSuchMethodException e) {
				continue;
			}
			
			Column an = getter.getAnnotation(Column.class);
			
			if(an != null) {
				field.setValidate_max_length("input-maxlen" + an.length());
			}
		}
	}
	
	public void initListFields() {}
	
	/**
	 * init view page
	 */
	public void initViewPage() {
		
		if (EntityUtil.OPERATE_LIST.equals(operate)
				|| EntityUtil.OPERATE_SAVE.equals(operate)
				|| EntityUtil.OPERATE_REMOVE.equals(operate)) {
			initListPage();
		} 
		else {
			initFormPage();
		}
	}
	
	public void initListPage() {
		
		if(!StringUtil.isNullOrEmpty(viewPage)) {
			return;
		}
		
		if(EntityUtil.OPERATE_LIST.equals(operate)) {
			viewPage = "entity/list";
		}
		
		if(EntityUtil.OPERATE_SAVE.equals(operate) || EntityUtil.OPERATE_REMOVE.equals(operate)) {
			viewPage = "redirect:/entity/dispatch.do?operate=list&model=" + model;
		}
	}
	
	public void initFormPage() {
		
		if(!StringUtil.isNullOrEmpty(viewPage)) {
			return;
		}
		
		viewPage = "entity/form";
	}
	
	/**
	 * init entity info for edit
	 */
	public void initEditEntity(String entityId) throws Exception {
		
		if(StringUtil.isNullOrEmpty(entityId)) {
			return;
		}
		
		IDao dao = SpringUtil.getDao();
		
		// get edit entity
		Class modelClz = Class.forName(model);
		Object modelObj = dao.getObject(modelClz, entityId);
		
		// set entity value to field
		Method getter = null;
		
		for(EntityField field : fields) {
			try {
				getter = modelClz.getMethod("get"
						+ field.getName().substring(0, 1).toUpperCase()
						+ field.getName().substring(1));
				
			} catch(NoSuchMethodException e) {
				continue;
			}
			
			// set field value
			field.setValue(getter.invoke(modelObj));
			
			// set relate field text
			field.setRelateValue(getRelateText(field.getRelateModel(),
					field.getName(), field.getValue(), field.getHtmlType()));
		}
	}
	
	/**
	 * get relate field's text
	 * 
	 * @param relateModel
	 * @param fieldName
	 * @param value
	 * @param htmlType
	 * @return
	 * @throws Exception
	 */
	private Object getRelateText(String relateModel, String fieldName,
			Object value, String htmlType) throws Exception {
		
		Object relateValue = "";
		
		if (StringUtil.isNullOrEmpty(relateModel) || value == null
				|| EntityUtil.HTML_CHECKBOX.equals(htmlType)) {
			return relateValue;
		}

		IDao dao = SpringUtil.getDao();
		Class relateClz = Class.forName(relateModel);
		
		// department
		if((relateClz == Department.class) && Constants.getDepartTreeMap().containsKey(value)) {
			relateValue = Constants.getDepartTreeMap().get(value).getName();
		}
		// others
		else {
			Object relateObj = dao.getObject(relateClz, (String) value);
			
			if(relateObj == null) {
				relateValue = "";
			} else {
				Method nameMethod = relateClz.getMethod("getName");
				relateValue = nameMethod.invoke(relateObj);
			}
		}
		
		return relateValue;
		
	}
	
	/**
	 * search list datas
	 */
	public void searchListDatas() {
		// combine search hql
		StringBuffer hql = new StringBuffer();
		hql.append("select id,");
		
		for(EntityField f : fields) {
			hql.append(f.getName() + ",");
		}
		hql.deleteCharAt(hql.length() - 1);
		
		hql.append(" from " + model);
		
		// get list datas
		IDao dao = SpringUtil.getDao();
		datas = dao.getAllByHql(hql.toString());
	}
	
	/**
	 * save or update model
	 * 
	 * @param paramMap
	 * @throws ClassNotFoundException 
	 */
	public void saveModel(Map<String, Object> paramMap) throws Exception {
		
		// before save
		beforeSave(paramMap);
		
		Class modelClz = Class.forName(model);
		Object modelObj = null;
		IDao dao = SpringUtil.getDao();
		
		// check add new or update
		if(!paramMap.containsKey("id") || StringUtil.isNullOrEmpty((String) paramMap.get("id"))) {
			paramMap.put("id", Constants.getID());
			modelObj = modelClz.newInstance();
		} else {
			modelObj = dao.getObject(modelClz, (String) paramMap.get("id"));
		}

		Method getter = null;
		Method setter = null;
		
		for(String name : paramMap.keySet()) {
			
			try {
				getter = modelClz.getMethod("get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1));
			} catch(NoSuchMethodException e) {
				continue;
			}
			
			setter = modelClz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1),
					getter.getReturnType());
			
			setter.invoke(modelObj, paramMap.get(name));
		}
		
		// save model
		dao.saveObject(modelObj);
		
		// reset cache
		if(modelObj instanceof User) {
			Constants.userIdNameMap = null;
			Constants.userNameIdMap = null;
			Constants.departTreeMap = null;
		}
		
		if(modelObj instanceof Role) {
			RoleService.userResStrMap = null;
		}
		
		// after save
		afterSave(modelObj);
	}
	
	public void beforeSave(Map<String, Object> paramMap) throws Exception {}
	
	public void afterSave(Object modelObj) {}
	
	/**
	 * remove entity
	 * 
	 * @param entityId
	 */
	public void removeEntity(String entityId) throws Exception {
		
		if(StringUtil.isNullOrEmpty(entityId)) {
			return;
		}
		
		Class modelClz = Class.forName(model);
		
		IDao dao = SpringUtil.getDao();
		dao.removeById(modelClz, entityId);
		
		// after save
		afterRemove();
	}
	
	public void afterRemove() {}
	
	/**
	 * combine JSON format result to page
	 * 
	 * @return
	 */
	public JSONObject combineResult() {
		
		JSONObject result = new JSONObject();
		
		result.put("operate", operate);
		result.put("model", model);
		result.put("modelName", modelName);
		result.put("fields", fields);
		result.put("datas", datas);
		
		return result;
	}
}
