package org.example.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;
import java.time.YearMonth;

@Converter
public class DateConverter implements AttributeConverter<YearMonth, Date> {

	@Override
	public Date convertToDatabaseColumn(YearMonth yearMonth) {
		return Date.valueOf(yearMonth.atEndOfMonth());
	}

	@Override
	public YearMonth convertToEntityAttribute(Date dbData) {
		return YearMonth.from(dbData.toLocalDate());
	}
}