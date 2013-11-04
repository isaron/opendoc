package com.cloud.entity;

import com.cloud.platform.Constants;

public class EntityField {

	private String name;
	private String label;
	private String htmlType;
	private String relateModel;
	private String relateMulti;
	private String isRequire;
	private int width;
	private Object value;
	private Object relateValue;
	
	private String validate_no_repeat;
	private String validate_max_length;

	public EntityField(String name, String label, int width) {
		this.name = name;
		this.label = label;
		this.width = width;
	}
	
	public EntityField(String name, String label, String htmlType, boolean isRequire) {
		this.name = name;
		this.label = label;
		this.htmlType = htmlType;
		this.isRequire = isRequire ? Constants.VALID_YES : Constants.VALID_NO;
	}
	
	public EntityField(String name, String label, String relateModel, boolean isMul, boolean isRequire) {
		this.name = name;
		this.label = label;
		this.relateModel = relateModel;
		this.relateMulti = isMul ? Constants.VALID_YES : Constants.VALID_NO;
		this.isRequire = isRequire ? Constants.VALID_YES : Constants.VALID_NO;
	}
	
	public EntityField(String name, String label, String htmlType, String relateModel, boolean isRequire) {
		this(name, label, htmlType, isRequire);
		this.relateModel = relateModel;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getHtmlType() {
		return htmlType;
	}
	
	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}
	
	public String getRelateModel() {
		return relateModel;
	}
	
	public void setRelateModel(String relateModel) {
		this.relateModel = relateModel;
	}
	
	public String getRelateMulti() {
		return relateMulti;
	}

	public void setRelateMulti(String relateMulti) {
		this.relateMulti = relateMulti;
	}
	
	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getRelateValue() {
		return relateValue;
	}

	public void setRelateValue(Object relateValue) {
		this.relateValue = relateValue;
	}
	
	public String getValidate_no_repeat() {
		return validate_no_repeat;
	}

	public void setValidate_no_repeat(String validate_no_repeat) {
		this.validate_no_repeat = validate_no_repeat;
	}

	public String getValidate_max_length() {
		return validate_max_length;
	}

	public void setValidate_max_length(String validate_max_length) {
		this.validate_max_length = validate_max_length;
	}
}
