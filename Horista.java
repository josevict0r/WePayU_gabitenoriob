package br.ufal.ic.p2.wepayu.models;


public class Horista extends Empregado{
    public Horista(){
        super();
    }
    public Horista(String nome, String endereco, String tipo, double salario) throws Exception {
        super(nome, endereco, tipo, salario);
    }
    public Double getSalarioBruto(String dataInicial, String data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalarioBruto'");
    }
}
