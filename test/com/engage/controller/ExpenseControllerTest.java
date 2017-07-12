package com.engage.controller;

import static com.engage.util.Constants.NULL_EMPTY_DATE;
import static com.engage.util.Constants.NULL_EMPTY_REASON;
import static com.engage.util.Constants.NULL_EMPTY_VALUE;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.engage.builder.ExpenseRequestObjectBuilder;
import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.factory.ExpenseResponseObjectFactory;
import com.engage.service.ExpenseService;

public class ExpenseControllerTest {
	
	@InjectMocks
	private ExpenseController expenseController;
	
	@Mock
	private ExpenseService mockExpenseService;
	
	@Rule
    public ExpectedException  thrown = ExpectedException .none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddExpense() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
									.withDate("07/25/2017")
									.withValue(12.89)
									.withReason("My expense descrptiion").build();
		expenseController.addExpense(ero);
		
		verify(mockExpenseService).add(ero);
	}
	
	@Test(expected = ParseException.class)
	public void testAddExpenseThrowsParseException() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
									.withDate("07-25-2017")
									.withValue(12.89)
									.withReason("My expense descrptiion").build();
		
		doThrow(new ParseException("parse exception", 1)).when(mockExpenseService).add(ero);
		expenseController.addExpense(ero);
		verify(mockExpenseService).add(ero);
	}
	
	@Test
	public void testAddExpenseThrowsExpenseExceptionWhenDateIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
									.withDate(null)
									.withValue(12.89)
									.withReason("My expense descrptiion").build();
		
		doThrow(new ExpenseException(NULL_EMPTY_DATE)).when(mockExpenseService).add(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_DATE);
		expenseController.addExpense(ero);
		verify(mockExpenseService).add(ero);
		
	}
	
	@Test
	public void testAddExpenseThrowsExpenseExceptionWhenValueIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
									.withDate("07-25-2017")
									.withValue(null)
									.withReason("My expense descrptiion").build();
		
		doThrow(new ExpenseException(NULL_EMPTY_VALUE)).when(mockExpenseService).add(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_VALUE);
		expenseController.addExpense(ero);
		verify(mockExpenseService).add(ero);
	}
	
	@Test
	public void testAddExpenseThrowsExpenseExceptionWhenReasonIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
									.withDate("07-25-2017")
									.withValue(12.89)
									.withReason(null).build();
		
		doThrow(new ExpenseException(NULL_EMPTY_REASON)).when(mockExpenseService).add(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_REASON);
		expenseController.addExpense(ero);
		verify(mockExpenseService).add(ero);
	}

	@Test
	public void testGetExpenses() {
		List<ExpenseResponseObject> list = new ArrayList<ExpenseResponseObject>() {{
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/23/2017", 100, 16.67, "My expense on 23rd"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/25/2017", 120, 20, "My expense on 25th"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/27/2017", 80, 13.33, "My expense on 27th"));
		}};
		
		when(mockExpenseService.getExpenses()).thenReturn(list);
		List<ExpenseResponseObject> actualList = expenseController.getExpenses();
		
		verify(mockExpenseService).getExpenses();
		Assert.assertEquals(list.size(), actualList.size());
		for(int i=0; i < list.size(); i++) {
			Assert.assertEquals(list.get(i).getDate(), actualList.get(i).getDate());
			Assert.assertEquals(list.get(i).getReason(), actualList.get(i).getReason());
			Assert.assertEquals(list.get(i).getValue(), actualList.get(i).getValue(), 0.001);
			Assert.assertEquals(list.get(i).getVat(), actualList.get(i).getVat(), 0.001);
		}		
	}
}
