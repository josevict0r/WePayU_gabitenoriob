package br.ufal.ic.p2.wepayu.exceptions;

public class IdSindicatoNula extends  Exception{
    public IdSindicatoNula(){
        super("Identificacao do sindicato nao pode ser nula.");
    }

}
