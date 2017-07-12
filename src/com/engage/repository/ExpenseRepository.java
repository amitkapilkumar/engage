package com.engage.repository;

import java.util.List;

import com.engage.model.Expense;

public interface ExpenseRepository {
	public void add(Expense expense);
	public List<Expense> findAll();
}
