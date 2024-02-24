package br.ufal.ic.p2.wepayu.exceptions;

public class DataInicialPosterior extends Exception{
    public DataInicialPosterior(){
        super("Data inicial nao pode ser posterior aa data final.");
    }
}
