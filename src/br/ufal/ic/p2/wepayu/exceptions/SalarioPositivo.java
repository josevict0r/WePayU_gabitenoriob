package br.ufal.ic.p2.wepayu.exceptions;

public class SalarioPositivo extends  Exception{
    public SalarioPositivo(){
        super("Salario deve ser nao-negativo.");
    }
}
