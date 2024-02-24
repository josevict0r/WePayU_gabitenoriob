package br.ufal.ic.p2.wepayu.models.empregados;


import br.ufal.ic.p2.wepayu.exceptions.EmpregadoNaoExisteException;

public class Horista extends Empregado {



    public Horista(String nome, String endereco,String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoNaoExisteException {
        super(nome, endereco, "horista", salario);
    }




}