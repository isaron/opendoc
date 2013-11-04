package com.cloud.entity;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("entity")
public class EntityDispatcher {

	private static Logger logger = Logger.getLogger(EntityDispatcher.class);
	
	/**
	 * entity auto operate dispatcher
	 * 
	 * @param operate
	 * @param model
	 * @return
	 */
	@RequestMapping("/dispatch.do")
	public ModelAndView dispatch(HttpServletRequest request,
			@RequestParam("operate") String operate,
			@RequestParam("model") String model) {
		
		ModelAndView mv = new ModelAndView();
		
		try {
			// get handler
			EntityHandler handler = getHandler(model);
			
			// get params
			Map<String, Object> paramMap = getParamMap(request);
			
			// deal before dispatch
			dealDispatch(handler, operate, model, paramMap);
			
			// init page info for front page
			JSONObject pageInfo = handler.combineResult();
			mv.addObject("pageInfo", pageInfo);
			
			// init return page
			mv.setViewName(handler.viewPage);
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：dispatch", e);
		}
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/synDispatch.do")
	public String synDispatch(HttpServletRequest request,
			@RequestParam("operate") String operate,
			@RequestParam("model") String model) {
		
		String pageInfo = "";
		
		try {
			// get handler
			EntityHandler handler = getHandler(model);
			
			// get params
			Map<String, Object> paramMap = getParamMap(request);
			
			// deal before dispatch
			dealDispatch(handler, operate, model, paramMap);
			
			// init page info for front page
			pageInfo = handler.combineResult().toString();
			
		} catch(Exception e) {
			logger.error("***** 异常信息 ***** 方法：synDispatch", e);
		}
		
		return pageInfo;
	}
	
	/**
	 * deal with handler before dispatch
	 * 
	 * @param operate
	 * @param model
	 * @return
	 * @throws Exception
	 */
	private void dealDispatch(EntityHandler handler, String operate,
			String model, Map paramMap) throws Exception {
		
		// set operate and model
		handler.operate = operate;
		handler.model = model;
		
		// init entity base info
		handler.initBase();
		
		// init page fields
		handler.initFields();
		
		// init view page
		handler.initViewPage();
		
		// edit entity
		if(EntityUtil.OPERATE_EDIT.equals(operate)) {
			handler.initEditEntity((String) paramMap.get(EntityUtil.ENTITY_ID));
		}
		
		// search list datas
		if(EntityUtil.OPERATE_LIST.equals(operate)) {
			handler.searchListDatas();
		}
		
		// save model
		if(EntityUtil.OPERATE_SAVE.equals(operate)) {
			handler.saveModel(paramMap);
		}
		
		// remove model
		if(EntityUtil.OPERATE_REMOVE.equals(operate)) {
			handler.removeEntity((String) paramMap.get(EntityUtil.ENTITY_ID));
		}
	}
	
	/**
	 * get entity operate handler
	 * 
	 * @param model
	 * @return
	 */
	private EntityHandler getHandler(String model) throws Exception {
		
		String handlerStr = model.replace("model", "handler") + "Handler";
		Class handlerClz = Class.forName(handlerStr);
		EntityHandler handler = (EntityHandler) handlerClz.newInstance();
		
		return handler;
	}
	
	/**
	 * get param map by request params
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> getParamMap(HttpServletRequest request) {
		Map params = new HashMap();
		Enumeration enums = request.getParameterNames();
		
		while (enums.hasMoreElements()) {
			String paramName = (String) enums.nextElement();
			String paramValue = request.getParameter(paramName);

			params.put(paramName, paramValue);
		}

		return params;
	}
}
