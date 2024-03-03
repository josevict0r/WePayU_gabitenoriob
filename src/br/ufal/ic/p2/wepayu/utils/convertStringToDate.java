package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class convertStringToDate {
    public static LocalDate convertStringToDate(String dataString, int metodo){
     DateTimeFormatter dateFormatter0 = DateTimeFormatter.ofPattern("d/M/yyyy").withResolverStyle(ResolverStyle.STRICT);
     DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);
        if(metodo == 0){

     return LocalDate.parse(dataString, dateFormatter0);
        }
        else{
            return LocalDate.parse(dataString,dateFormatter1);
        }
    }
}
