package br.ufal.ic.p2.wepayu.models;


public class Horista extends Empregado{
    public Horista(){
        super();
    }
    public Horista(String nome, String endereco, String tipo, String salario) throws Exception {
        super(nome, endereco, tipo, salario);
    }
}
