package com.engage.service;

import java.text.ParseException;
import java.util.List;

import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;

public interface ExpenseService {
	public void add(ExpenseRequestObject erb) throws ParseException, ExpenseException;
	public List<ExpenseResponseObject> getExpenses();
}
