package com.engage.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.service.ExpenseService;

@Controller
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@RequestMapping(value = "/expense", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@ResponseBody
	public void addExpense(@RequestBody ExpenseRequestObject erb) throws ParseException, ExpenseException {
		expenseService.add(erb);
	}
	
	@RequestMapping(value = "/expenses", produces = "application/json")
	@ResponseBody
	public List<ExpenseResponseObject> getExpenses() {
		return expenseService.getExpenses();
	}
}
