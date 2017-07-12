package com.engage.service;

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
import com.engage.factory.ExpenseFactory;
import com.engage.factory.ExpenseResponseObjectFactory;
import com.engage.mapper.ExpenseMapper;
import com.engage.model.Expense;
import com.engage.repository.ExpenseRepository;
import com.engage.util.ExpenseUtil;

public class ExpenseServiceImplTest {
	
	@InjectMocks
	private ExpenseServiceImpl expenseService;
	
	@Mock
	private ExpenseMapper mockExpenseMapper;
	@Mock
	private ExpenseRepository mockExpenseRepository;
	
	@Rule
    public ExpectedException  thrown = ExpectedException .none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAdd() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(12.89)
				.withReason("My expense descrption").build();
		Expense expense = ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/25/2017"), 12.89, "My expense descrption");
		
		when(mockExpenseMapper.map(ero)).thenReturn(expense);
		expenseService.add(ero);

		verify(mockExpenseMapper).map(ero);
		verify(mockExpenseRepository).add(expense);
	}
	
	@Test
	public void testAddWhenDateIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate(null)
				.withValue(12.89)
				.withReason("My expense descrption").build();
		
		doThrow(new ExpenseException(NULL_EMPTY_DATE)).when(mockExpenseMapper).map(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_DATE);
		expenseService.add(ero);

		verify(mockExpenseMapper).map(ero);
	}
	
	@Test
	public void testAddWhenValueIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(null)
				.withReason("My expense descrption").build();
		
		doThrow(new ExpenseException(NULL_EMPTY_VALUE)).when(mockExpenseMapper).map(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_VALUE);
		expenseService.add(ero);

		verify(mockExpenseMapper).map(ero);
	}
	
	@Test
	public void testAddWhenReasonIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(12.89)
				.withReason(null).build();
		
		doThrow(new ExpenseException(NULL_EMPTY_REASON)).when(mockExpenseMapper).map(ero);
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_REASON);
		expenseService.add(ero);

		verify(mockExpenseMapper).map(ero);
	}
	
	@Test
	public void testGetExpenses() throws ParseException {
		List<Expense> expenses = new ArrayList<Expense>() {{
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/23/2017"), 100d, "My expense on 23rd"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/25/2017"), 120d, "My expense on 25th"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/27/2017"), 80d, "My expense on 27th"));
		}};
		
		when(mockExpenseRepository.findAll()).thenReturn(expenses);
		
		List<ExpenseResponseObject> list = new ArrayList<ExpenseResponseObject>() {{
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/23/2017", 100, 16.67, "My expense on 23rd"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/25/2017", 120, 20, "My expense on 25th"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/27/2017", 80, 13.33, "My expense on 27th"));
		}};
		
		when(mockExpenseMapper.mapToResponseObjects(expenses)).thenReturn(list);
		
		List<ExpenseResponseObject> actualList = expenseService.getExpenses();
		
		verify(mockExpenseRepository).findAll();
		verify(mockExpenseMapper).mapToResponseObjects(expenses);
		
		Assert.assertEquals(list.size(), actualList.size());
		for(int i=0; i < list.size(); i++) {
			Assert.assertEquals(list.get(i).getDate(), actualList.get(i).getDate());
			Assert.assertEquals(list.get(i).getReason(), actualList.get(i).getReason());
			Assert.assertEquals(list.get(i).getValue(), actualList.get(i).getValue(), 0.001);
			Assert.assertEquals(list.get(i).getVat(), actualList.get(i).getVat(), 0.001);
		}
	}

}
