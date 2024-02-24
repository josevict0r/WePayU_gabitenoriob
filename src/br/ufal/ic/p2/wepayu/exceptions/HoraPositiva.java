package br.ufal.ic.p2.wepayu.exceptions;

public class HoraPositiva extends Exception {
    public HoraPositiva(){
        super("Horas devem ser positivas.");
    }
}
