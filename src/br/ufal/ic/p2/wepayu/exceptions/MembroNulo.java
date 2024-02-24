package br.ufal.ic.p2.wepayu.exceptions;

public class MembroNulo extends Exception{
    public MembroNulo(){
        super("Identificacao do membro nao pode ser nula.");
    }
}
