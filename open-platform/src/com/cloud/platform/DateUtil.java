package com.cloud.platform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * get date before or after date
	 * 
	 * @param date
	 * @param span
	 * @return
	 */
	public static Date getAddDate(Date date, int span) {
		if(date == null) {
			return new Date();
		}
		
		if(span == 0) {
			return date;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		// add date to calendar
		cal.add(Calendar.DATE, span);
		
		return cal.getTime();
	}
	
	/**
	 * get day span between two days
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static int getDays(Date start, Date end) throws ParseException {
		if(start == null || end == null) {
			return 0;
		}

		// reset date time to empty
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		start = sdf.parse(sdf.format(start));
		end = sdf.parse(sdf.format(end));
		
		// count day span by time long
		long span = (start.getTime() - end.getTime()) / (24 * 60 * 60 * 1000);

		return (int) span;
	}
	
	/**
	 * get date string
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		if(date == null) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(date);
	}

	/**
	 * get time string
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeStr(Date date) {
		if(date == null) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		return sdf.format(date);
	}
	
	/**
	 * get time string without year
	 * 
	 * @param date
	 * @return
	 */
	public static String getSimpleTimeStr(Date date) {
		if(date == null) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		
		return sdf.format(date);
	}
	
	/**
	 * convert date string to date
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		if(StringUtil.isNullOrEmpty(dateStr)) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.parse(dateStr);
	}
	
	/**
	 * set a date's hour, minite, second to empty
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(Date date) throws ParseException {
		return parseDate(getDateStr(date));
	}
	
	/**
	 * convert time string to date
	 * 
	 * @param dateTimeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateTimeStr) throws ParseException {
		if(StringUtil.isNullOrEmpty(dateTimeStr)) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.parse(dateTimeStr);
	}
	
	/**
	 * get current time string
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return getTimeStr(new Date());
	}
}
