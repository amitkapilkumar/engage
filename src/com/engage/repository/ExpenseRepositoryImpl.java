package com.engage.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.engage.model.Expense;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public void add(Expense expense) {
		entityManager.persist(expense);
	}

	@Override
	public List<Expense> findAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Expense> cq = builder.createQuery(Expense.class);
	    Root<Expense> root = cq.from(Expense.class);
	    cq.select(root);
	    TypedQuery<Expense> tq = entityManager.createQuery(cq);
	    List<Expense> list = tq.getResultList();
	    return list;
	}	
}