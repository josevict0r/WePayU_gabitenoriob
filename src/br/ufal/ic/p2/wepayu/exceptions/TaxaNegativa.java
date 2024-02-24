package br.ufal.ic.p2.wepayu.exceptions;

public class TaxaNegativa extends Exception{
    public TaxaNegativa(){
        super("Taxa sindical deve ser nao-negativa.");
    }
}
