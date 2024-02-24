package br.ufal.ic.p2.wepayu.exceptions;

public class TaxaNula extends Exception{
    public TaxaNula(){
        super("Taxa sindical nao pode ser nula.");
    }
}
