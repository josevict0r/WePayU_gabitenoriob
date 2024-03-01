package br.ufal.ic.p2.wepayu.utils.utilsFolha;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ehUltimoDiaMes {
     public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    public static boolean ehUltimoDiaMes(String data){
        LocalDate dataParse = LocalDate.parse(data, formatter);
        LocalDate ultimo = dataParse.with(TemporalAdjusters.lastDayOfMonth());

        return ultimo.equals(dataParse);
    }
}
