package br.ufal.ic.p2.wepayu.models.empregados;


import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class Horista extends Empregado {

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    private String horas;
    public Horista(){};

    public Horista(String nome, String endereco,String tipo, String salario) throws EmpregadoNaoExisteException, EmpregadoNaoExisteException {
        super(nome, endereco, "horista", salario);
    }




}