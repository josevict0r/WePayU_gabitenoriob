package br.ufal.ic.p2.wepayu.exceptions;

public class ContaNula extends Exception{
    public ContaNula(){
        super("Conta corrente nao pode ser nulo.");
    }
}
