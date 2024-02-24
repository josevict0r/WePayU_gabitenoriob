package br.ufal.ic.p2.wepayu.exceptions;

public class NaoExisteNome extends Exception{
    public NaoExisteNome(){
        super("Nao ha empregado com esse nome.");
    }
}
