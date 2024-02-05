package br.ufal.ic.p2.wepayu.models.empregados;


import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

public class Horista extends Empregado {


    public Horista(String nome, String endereco,String tipo, String salario) throws EmpregadoNaoExisteException, EmpregadoNaoExisteException {
        super(nome, endereco, "horista", salario);
    }




}