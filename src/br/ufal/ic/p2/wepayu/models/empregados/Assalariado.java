package br.ufal.ic.p2.wepayu.models.empregados;


import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class Assalariado extends Empregado {

    public Assalariado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException {
        super(nome, endereco, tipo, salario);
    }

}


