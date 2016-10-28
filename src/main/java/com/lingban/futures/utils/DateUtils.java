package com.lingban.futures.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

	public static LocalDateTime Date2LocalDateTime(Date date) {

		LocalDateTime t = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return t;

	}

	public static String Date2StrHourMinute(Date date) {
		
		if(date == null){
			return "";
		}
		
		LocalDateTime t = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		String hourAndMinute = t.getHour() + ":" + t.getMinute();
		
		return hourAndMinute;

	}
	
	public static String Date2LocalDateStr(Date date) {
		LocalDateTime t = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return t.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	}
}
