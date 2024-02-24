package br.ufal.ic.p2.wepayu.exceptions;

public class NaoSindicalizado extends Exception{
    public NaoSindicalizado(){
        super("Empregado nao eh sindicalizado.");
    }
}
