package br.ufal.ic.p2.wepayu.exceptions;

public class SalarioNulo extends Exception{
    public SalarioNulo(){
        super("Salario nao pode ser nulo.");
    }
}
