package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controllers.EmpregadoController;
import br.ufal.ic.p2.wepayu.controllers.PontoController;
import br.ufal.ic.p2.wepayu.controllers.SistemaController;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.io.FileNotFoundException;

public class Facade {

   // SistemaController sistema = new SistemaController();
   // EmpregadoController empregados = new EmpregadoController();

    //construtor facade
    public Facade() throws FileNotFoundException{
        // QUANDO ATIVA ISSO DA MIL PROBLEMAS DE NAO ACHAR O ARQUIVO
        SistemaController.iniciarSistema();

    }



    public void zerarSistema() throws FileNotFoundException {
        SistemaController.zerarSistema();
    }

    public void encerrarSistema() throws FileNotFoundException {
        SistemaController.encerrarSistema();
    }


    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoAtributosExceptions {
       return PontoController.getHorasExtrasTrabalhadas(emp,dataInicial,dataFinal);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoAtributosExceptions {
        return PontoController.getHorasNormaisTrabalhadas(emp,dataInicial,dataFinal);
    }

    public void lancaCartao(String emp, String data, String horas) throws EmpregadoAtributosExceptions {
        PontoController.lancaCartao(emp,data,horas);
    }


    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {
        String resultado = EmpregadoController.getAtributoEmpregado(emp, atributo);


        return resultado;
    }

    public String getEmpregadoPorNome(String nome, int indice ) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {


         return  EmpregadoController.getEmpregadoPorNome(nome, indice);

    }

    public void removerEmpregado(String emp) throws EmpregadoAtributosExceptions {
        EmpregadoController.removerEmpregado(emp);
    }


    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
                return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
        return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario,comissao);
    }

}

