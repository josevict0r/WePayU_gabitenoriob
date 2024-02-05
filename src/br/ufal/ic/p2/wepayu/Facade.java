package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controllers.EmpregadoController;
import br.ufal.ic.p2.wepayu.controllers.SistemaController;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

public class Facade {

    SistemaController sistema = new SistemaController();
    EmpregadoController empregados = new EmpregadoController();


    public void zerarSistema(){
        SistemaController.zerarSistema();
    }

    public void encerrarSistema(){
        SistemaController.encerrarSistema();
    }



    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
                return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
        return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario,comissao);
    }

}

