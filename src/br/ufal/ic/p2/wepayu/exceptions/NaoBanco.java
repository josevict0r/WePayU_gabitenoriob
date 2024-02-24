package br.ufal.ic.p2.wepayu.exceptions;

public class NaoBanco extends Exception{
    public NaoBanco(){
        super("Empregado nao recebe em banco.");
    }
}
