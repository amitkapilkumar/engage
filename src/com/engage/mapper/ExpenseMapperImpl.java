package com.engage.mapper;

import static com.engage.util.Constants.*;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.engage.dto.ExpenseRequestObject;
import com.engage.dto.ExpenseResponseObject;
import com.engage.exception.ExpenseException;
import com.engage.factory.ExpenseFactory;
import com.engage.factory.ExpenseResponseObjectFactory;
import com.engage.model.Expense;
import com.engage.util.ExpenseUtil;

@Component
public class ExpenseMapperImpl implements ExpenseMapper {

	@Override
	public Expense map(ExpenseRequestObject erb) throws ParseException, ExpenseException {
		verifyRequestObject(erb);
		return ExpenseFactory.getExpense(ExpenseUtil.getDateInLong(erb.getDate()), erb.getValue(), erb.getReason());
	}
	
	private void verifyRequestObject(ExpenseRequestObject erb) throws ExpenseException {
		if(erb.getDate() == null || erb.getDate().isEmpty()) {
			throw new ExpenseException(NULL_EMPTY_DATE);
		}
		
		if(erb.getValue() == null) {
			throw new ExpenseException(NULL_EMPTY_VALUE);
		}
		
		if(erb.getReason() == null || erb.getReason().isEmpty()) {
			throw new ExpenseException(NULL_EMPTY_REASON);
		}
	}
	
	@Override
	public List<ExpenseResponseObject> mapToResponseObjects(List<Expense> expenses) {
		List<ExpenseResponseObject> list = new ArrayList<>();
		for(Expense expense : expenses) {
			list.add(ExpenseResponseObjectFactory.getExpenseResponseObject(getDateInString(expense.getDate()), 
					expense.getValue(), getVat(expense.getValue()), expense.getReason()));
		}
		return list;
	}

	private double getVat(double value) {
		return Double.parseDouble(new DecimalFormat(".##").format(ExpenseUtil.get20PercentVat(value)));
	}
	
	private String getDateInString(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(date);
		return sdf.format(calender.getTime());
	}
}
