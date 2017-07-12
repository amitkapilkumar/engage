package com.engage.factory;

import com.engage.model.Expense;

public class ExpenseFactory {
	public static Expense getExpense() {
		return new Expense();
	}
	
	public static Expense getExpense(Long date, Double value, String reason) {
		Expense expense = getExpense();
		expense.setDate(date);
		expense.setValue(value);
		expense.setReason(reason);
		return expense;
	}
}
