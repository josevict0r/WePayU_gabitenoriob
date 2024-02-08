package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class isDateWithinRange {

    public static boolean isDateWithinRange(String dateToCheck, String startDate, String endDate) {
        LocalDate date = LocalDate.parse(dateToCheck, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("d/M/yyyy"));
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
