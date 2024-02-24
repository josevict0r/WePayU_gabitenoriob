package br.ufal.ic.p2.wepayu.exceptions;

public class IdentificacaoNula extends Exception {
    public IdentificacaoNula(){
        super("Identificacao do empregado nao pode ser nula.");
    }
}
