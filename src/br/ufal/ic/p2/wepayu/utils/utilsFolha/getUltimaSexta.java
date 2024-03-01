package br.ufal.ic.p2.wepayu.utils.utilsFolha;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class getUltimaSexta {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static String getUltimaSexta(String data) {
        LocalDate dataParse = LocalDate.parse(data,formatter);
        LocalDate dataInicial = dataParse.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

        return dataInicial.format(formatter);
    }
}
