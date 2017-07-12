package com.engage.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.engage.factory.ExpenseFactory;
import com.engage.model.Expense;
import com.engage.repository.ExpenseRepositoryImpl;
import com.engage.util.ExpenseUtil;

public class ExpenseRepositoryImplTest {
	
	@InjectMocks
	private ExpenseRepositoryImpl expenseRepository;
	
	@Mock
	private EntityManager mockEntityManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAdd() throws ParseException {
		Expense expense = ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/23/2017"), 100d, "My expense on 23rd");
		expenseRepository.add(expense);
		
		verify(mockEntityManager).persist(expense);
	}

	@Test
	public void testFindAll() throws ParseException {
		List<Expense> expenses = new ArrayList<Expense>() {{
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/23/2017"), 100d, "My expense on 23rd"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/25/2017"), 120d, "My expense on 25th"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/27/2017"), 80d, "My expense on 27th"));
		}};
		
		CriteriaBuilder cb = mock(CriteriaBuilder.class);
		CriteriaQuery<Expense> cq = mock(CriteriaQuery.class);
		Root<Expense> root = mock(Root.class);
		TypedQuery<Expense> tq = mock(TypedQuery.class);
		when(mockEntityManager.getCriteriaBuilder()).thenReturn(cb);
		when(cb.createQuery(Expense.class)).thenReturn(cq);
		when(cq.from(Expense.class)).thenReturn(root);
		when(mockEntityManager.createQuery(cq)).thenReturn(tq);
		when(tq.getResultList()).thenReturn(expenses);
		
		List<Expense> actualList = expenseRepository.findAll();
		
		verify(mockEntityManager).getCriteriaBuilder();
		verify(cb).createQuery(Expense.class);
		verify(cq).from(Expense.class);
		verify(cq).select(root);
		verify(mockEntityManager).createQuery(cq);
		verify(tq).getResultList();
		
		Assert.assertEquals(expenses.size(), actualList.size());
		for(int i=0; i < expenses.size(); i++) {
			Assert.assertEquals(expenses.get(i).getDate(), actualList.get(i).getDate());
			Assert.assertEquals(expenses.get(i).getValue(), actualList.get(i).getValue());
			Assert.assertEquals(expenses.get(i).getReason(), actualList.get(i).getReason());
		}
	}

}
