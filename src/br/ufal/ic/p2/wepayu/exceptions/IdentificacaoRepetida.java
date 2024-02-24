package br.ufal.ic.p2.wepayu.exceptions;

public class IdentificacaoRepetida extends Exception{
    public IdentificacaoRepetida(){
        super("Ha outro empregado com esta identificacao de sindicato");
    }
}
