package com.engage.builder;

import com.engage.dto.ExpenseRequestObject;

public class ExpenseRequestObjectBuilder {
	private String date;
	private Double value;
	private String reason;
	
	public ExpenseRequestObjectBuilder withDate(String date) {
		this.date = date;
		return this;
	}
	
	public ExpenseRequestObjectBuilder withValue(Double value) {
		this.value = value;
		return this;
	}
	
	public ExpenseRequestObjectBuilder withReason(String reason) {
		this.reason = reason;
		return this;
	}
	
	public ExpenseRequestObject build() {
		ExpenseRequestObject ero = new ExpenseRequestObject();
		ero.setDate(date);
		ero.setReason(reason);
		ero.setValue(value);
		return ero;
	}
}
