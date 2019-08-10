package com.internet_radio.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtilities {
	
	private static final String UK_DATE_PATTERN = "dd/MM/yyyy";
	private static final String BBC_UK_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(UK_DATE_PATTERN);
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(BBC_UK_DATE_TIME_PATTERN);

	public static String localDateToSimpleUKDate(LocalDateTime date) {
		return date.format(DATE_FORMATTER);
	}

	public static LocalDateTime rawBBCDateStringToLocalDateTime(String ukDate) {
		try {
			return LocalDateTime.parse(ukDate, DATE_TIME_FORMATTER);
		} catch (DateTimeParseException ex) {
			Logger.getLogger(DateUtilities.class.getName()).log(Level.WARNING, "cannot parse date " + ukDate, ex);
			return LocalDateTime.MIN;
		}

	}

}
