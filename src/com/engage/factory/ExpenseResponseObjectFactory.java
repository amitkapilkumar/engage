package com.engage.factory;

import com.engage.dto.ExpenseResponseObject;

public class ExpenseResponseObjectFactory {
	
	public static ExpenseResponseObject getExpenseResponseObject() {
		return new ExpenseResponseObject();
	}
	
	public static ExpenseResponseObject getExpenseResponseObject(String date, double value, double vat, String reason) {
		ExpenseResponseObject ero = new ExpenseResponseObject();
		ero.setDate(date);
		ero.setValue(value);
		ero.setVat(vat);
		ero.setReason(reason);
		return ero;
	}
}
