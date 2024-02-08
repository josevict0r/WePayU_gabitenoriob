package br.ufal.ic.p2.wepayu.utils;


import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class isValidDate {
    public static boolean isValidDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        if(month == 2 && day > 29){
            return false;
        }
        else{
            return year >= 1500 && year <= 2024 && month >= 1 && month <= 12 && day >= 1 && day <= Month.of(month).length(Year.isLeap(year));
        }

    }
}