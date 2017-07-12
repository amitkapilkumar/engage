package com.engage.mapper;

import java.text.ParseException;
import java.util.List;

import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.model.Expense;

public interface ExpenseMapper {

	public Expense map(ExpenseRequestObject erb) throws ParseException, ExpenseException;

	public List<ExpenseResponseObject> mapToResponseObjects(List<Expense> expenses);
}
