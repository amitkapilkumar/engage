package com.engage.mapper;

import static com.engage.util.Constants.NULL_EMPTY_DATE;
import static com.engage.util.Constants.NULL_EMPTY_REASON;
import static com.engage.util.Constants.NULL_EMPTY_VALUE;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.engage.builder.ExpenseRequestObjectBuilder;
import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.factory.ExpenseFactory;
import com.engage.factory.ExpenseResponseObjectFactory;
import com.engage.model.Expense;
import com.engage.util.ExpenseUtil;

public class ExpenseMapperImplTest {
	
	@InjectMocks
	private ExpenseMapperImpl expenseMapper; 
	
	@Rule
    public ExpectedException  thrown = ExpectedException .none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testMapExpenseRequestObject() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(12.89)
				.withReason("My expense descrption").build();
		Expense expense = ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/25/2017"), 12.89, "My expense descrption");
		
		Expense actualExpense = expenseMapper.map(ero);
		
		Assert.assertEquals(expense.getDate(), actualExpense.getDate());
		Assert.assertEquals(expense.getReason(), actualExpense.getReason());
		Assert.assertEquals(expense.getValue(), actualExpense.getValue());
	}
	
	@Test
	public void testMapExpenseRequestObjectWhenDateIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate(null)
				.withValue(12.89)
				.withReason("My expense descrption").build();
		
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_DATE);
		expenseMapper.map(ero);
	}
	
	@Test
	public void testMapExpenseRequestObjectWhenValueIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(null)
				.withReason("My expense descrption").build();
		
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_VALUE);
		expenseMapper.map(ero);
	}
	
	@Test
	public void testMapExpenseRequestObjectWhenReasonIsNull() throws ParseException, ExpenseException {
		ExpenseRequestObject ero = new ExpenseRequestObjectBuilder()
				.withDate("07/25/2017")
				.withValue(12.89)
				.withReason(null).build();
	
		thrown.expect(ExpenseException.class);
		thrown.expectMessage(NULL_EMPTY_REASON);
		expenseMapper.map(ero);
	}

	@Test
	public void testMapToResponseObjects() throws ParseException {
		List<Expense> expenses = new ArrayList<Expense>() {{
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/23/2017"), 100d, "My expense on 23rd"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/25/2017"), 120d, "My expense on 25th"));
			add(ExpenseFactory.getExpense(ExpenseUtil.getDateInLong("07/27/2017"), 80d, "My expense on 27th"));
		}};
		
		List<ExpenseResponseObject> list = new ArrayList<ExpenseResponseObject>() {{
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/23/2017", 100, 16.67, "My expense on 23rd"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/25/2017", 120, 20, "My expense on 25th"));
			add(ExpenseResponseObjectFactory.getExpenseResponseObject("07/27/2017", 80, 13.33, "My expense on 27th"));
		}};
		
		List<ExpenseResponseObject> actualList = expenseMapper.mapToResponseObjects(expenses);
		
		Assert.assertEquals(list.size(), actualList.size());
		for(int i=0; i < list.size(); i++) {
			Assert.assertEquals(list.get(i).getDate(), actualList.get(i).getDate());
			Assert.assertEquals(list.get(i).getReason(), actualList.get(i).getReason());
			Assert.assertEquals(list.get(i).getValue(), actualList.get(i).getValue(), 0.001);
			Assert.assertEquals(list.get(i).getVat(), actualList.get(i).getVat(), 0.001);
		}
	}

}
