package com.engage.util;

import static com.engage.util.Constants.DATE_FORMAT;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExpenseUtil {
	public static double get20PercentVat(double value) {
		return value - ((value * 100) / 120);
	}
	
	public static Long getDateInLong(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.parse(date).getTime();
	}
}
