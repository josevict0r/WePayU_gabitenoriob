package br.ufal.ic.p2.wepayu.exceptions;

public class ComissaoNula extends Exception{
    public ComissaoNula(){
        super("Comissao nao pode ser nula.");
    }
}
