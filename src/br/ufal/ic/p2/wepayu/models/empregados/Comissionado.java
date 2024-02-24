package br.ufal.ic.p2.wepayu.models.empregados;

import br.ufal.ic.p2.wepayu.exceptions.EmpregadoNaoExisteException;

public class Comissionado extends Empregado {


    private String comissao;
    public Comissionado() {}
    public Comissionado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        super(nome, endereco, "comissionado", salario);
        this.comissao = comissao;
    }


    public String getComissao() {
        return comissao;
    }
    public void setComissao(String comissao) {
        this.comissao = comissao;
    }

}
