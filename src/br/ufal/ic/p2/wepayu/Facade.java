package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controllers.*;


import java.io.FileNotFoundException;

public class Facade {


    public Facade() throws FileNotFoundException{
        //SistemaController.encerrarSistema();
        SistemaController.iniciarSistema();

    }
    public void zerarSistema() throws FileNotFoundException {
        SistemaController.zerarSistema();
    }

    public void encerrarSistema() throws FileNotFoundException {
        SistemaController.encerrarSistema();
    }

    public  void alteraEmpregado(String emp,String atributo, String idSindicato, String taxaSindical) throws EmpregadoAtributosExceptions {
        EmpregadoController.alteraEmpregado(emp,"sindicalizado", idSindicato,taxaSindical);
    }
    public  void alteraEmpregado(String emp,String atributo, String valor1) throws EmpregadoAtributosExceptions {
        EmpregadoController.alteraEmpregado(emp,"sindicalizado",valor1);
    }

    public  void alteraEmpregado(String emp,String atributo, Boolean valor1) throws EmpregadoAtributosExceptions {
        EmpregadoController.alteraEmpregado(emp,"sindicalizado",false);
    }

    public  void alteraEmpregado(String emp,String atributo, String valor1, String banco,String agencia, String contaCorrente) throws EmpregadoAtributosExceptions {
        EmpregadoController.alteraEmpregado(emp,"metodoPagamento", "banco",banco,agencia,contaCorrente);
    }

    public void lancaTaxaServico(String emp, String data,String valor) throws Exception {
        ServicoController.lancaTaxaServico(emp,data,valor);
    }

    public  String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        return ServicoController.getTaxasServico(emp,dataInicial,dataFinal);

    }


    public void lancaVenda(String emp, String data, String valor) throws Exception {
        VendasController.lancaVenda(emp,data,valor);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
         return  VendasController.getVendasRealizadas(emp,dataInicial,dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
       return PontoController.getHorasExtrasTrabalhadas(emp,dataInicial,dataFinal);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
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

