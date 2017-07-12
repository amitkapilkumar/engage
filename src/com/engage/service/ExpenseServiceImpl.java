package com.engage.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.mapper.ExpenseMapper;
import com.engage.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private ExpenseMapper expenseMapper;

	@Override
	public void add(ExpenseRequestObject erb) throws ParseException, ExpenseException {
		expenseRepository.add(expenseMapper.map(erb));
	}

	@Override
	public List<ExpenseResponseObject> getExpenses() {
		return expenseMapper.mapToResponseObjects(expenseRepository.findAll());
	}
}
