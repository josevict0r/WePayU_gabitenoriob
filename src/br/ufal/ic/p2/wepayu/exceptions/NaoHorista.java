package br.ufal.ic.p2.wepayu.exceptions;

public class NaoHorista extends Exception{
    public NaoHorista(){
        super("Empregado nao eh horista.");
    }
}
