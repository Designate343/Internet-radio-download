package com.internet_radio.pagescraping.bbc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BbcPageParser {

    private static final String BBC_UK_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(BBC_UK_DATE_TIME_PATTERN);

    protected static LocalDateTime rawBBCDateStringToLocalDateTime(String ukDate) {
        try {
            return LocalDateTime.parse(ukDate, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            Logger.getLogger(BbcPageParser.class.getName()).log(Level.WARNING, "cannot parse date " + ukDate, ex);
            return null;
        }
    }

}
