package com.cloud.platform;

import org.springframework.core.convert.converter.Converter;

public class TextFilterConverter implements Converter<String, String> {

	public String convert(String source) {
		
		if(!StringUtil.isNullOrEmpty(source)) {
			source = source.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
		
		return source;
	}
}
