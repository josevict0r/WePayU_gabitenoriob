package br.ufal.ic.p2.wepayu.exceptions;

public class ValorPositivo extends  Exception{
    public ValorPositivo(){
        super("Valor deve ser positivo.");
    }
}
