package com.cloud.platform;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {

	/**
	 * decimal format, default 2 length decimal
	 * 
	 * @param number
	 * @return
	 */
	public static String decimalFormat(double number) {
		
		NumberFormat format = new DecimalFormat(".00");
		
		return format.format(number);
	}
}
