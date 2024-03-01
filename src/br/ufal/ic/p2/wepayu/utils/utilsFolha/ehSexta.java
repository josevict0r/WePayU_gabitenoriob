package br.ufal.ic.p2.wepayu.utils.utilsFolha;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ehSexta {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static boolean ehSexta(String data){
        LocalDate dataParse = LocalDate.parse(data, formatter);

        return dataParse.getDayOfWeek() == DayOfWeek.FRIDAY;
    }
}
