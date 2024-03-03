package br.ufal.ic.p2.wepayu.models;


public class Comissionado extends Empregado{
    private String comissao;

    public Comissionado(){
    }
    public Comissionado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception {
        super(nome, endereco, tipo, salario);
        this.comissao = comissao;
        
    }

    public String getComissao() {
        return comissao;
    }
    public void setComissao(String comissao) {
        this.comissao = comissao;
    }
}
