package br.ufal.ic.p2.wepayu.exceptions;

public class ComissaoPositiva extends Exception{
    public  ComissaoPositiva(){
        super("Comissao deve ser nao-negativa.");
    }
}
