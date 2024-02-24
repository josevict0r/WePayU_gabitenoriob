package br.ufal.ic.p2.wepayu.exceptions;

public class NaoComissionado extends Exception{
    public NaoComissionado(){
        super("Empregado nao eh comissionado.");
    }
}
