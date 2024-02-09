package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class isDateWithinRange {

    public static boolean isDateWithinRange(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }
}
