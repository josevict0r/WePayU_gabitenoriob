package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class convertStringToDate {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
    public LocalDate convertStringToDate(String dataString){
     return LocalDate.parse(dataString, dateFormatter);
    }
}
