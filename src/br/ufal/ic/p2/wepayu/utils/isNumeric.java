package br.ufal.ic.p2.wepayu.utils;

public class isNumeric {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
