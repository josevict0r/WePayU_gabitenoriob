package br.ufal.ic.p2.wepayu.utils;

public class convertDoubleToString {
    public static String convertDoubleToString(Double value, int decimalPlaces){

     return String.format(("%." + decimalPlaces + "f"), value).replace(".", ",");
    }
    
}
