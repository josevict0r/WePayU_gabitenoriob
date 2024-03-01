package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.controllers.*;
import java.io.FileNotFoundException;

public class Facade {

    public Facade() throws FileNotFoundException {
       // SistemaController.encerrarSistema();
        SistemaController.iniciarSistema();

    }
    public void zerarSistema() throws FileNotFoundException {
        SistemaController.zerarSistema();
    }

    public void encerrarSistema() throws FileNotFoundException {
        SistemaController.encerrarSistema();
    }

    public  void alteraEmpregado(String emp,String atributo, String valor) throws NaoComissionado, SalarioNulo, AtributoNaoExiste, TipoInvalido, EnderecoNulo, MetodoInvalido, ComissaoNumerica, EmpregadoNaoExisteException, NomeNulo, SalarioNumerico, ComissaoPositiva, SalarioPositivo, ComissaoNula, IdentificacaoNula, ValorTrueFalse, AgendaNaoDisponivel {
        EmpregadoController.alteraAtributoEmpregado(emp, atributo, valor);
    }
    
    public  void alteraEmpregado(String emp,String atributo, String valor1, String comissao) throws ValorTrueFalse, IdentificacaoNula, NaoComissionado, EmpregadoNaoExisteException { 
     EmpregadoController.alteraEmpregado(emp,atributo,valor1, comissao);
   }
    
    public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws IdentificacaoRepetida, IdSindicatoNula, TaxaNula, TaxaNegativa, TaxaNumerica, ValorTrueFalse {
        EmpregadoController.alteraEmpregadoSindicalizado(emp, "sindicalizado", true, idSindicato, taxaSindical);
    }
    
    public void alteraEmpregado(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws BancoNulo, AgenciaNula, ContaNula, EmpregadoNaoExisteException, IdentificacaoNula {
        EmpregadoController.adicionaMetodoPagamento(emp, "metodoPagamento", "banco", banco, agencia, contaCorrente);
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
       return PontoController.getHorasTrabalhadas(emp,dataInicial,dataFinal,0);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        return PontoController.getHorasTrabalhadas(emp,dataInicial,dataFinal,1);
    }

    public void lancaCartao(String emp, String data, String horas) throws DataInvalida, HoraPositiva, NaoHorista, EmpregadoNaoExisteException, IdentificacaoNula {
        PontoController.lancaCartao(emp,data,horas);
    }


    public String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, AtributoNaoExiste, IdentificacaoNula, NaoSindicalizado, NaoBanco, NaoComissionado {
        String resultado = EmpregadoController.getAtributoEmpregado(emp, atributo);
        return resultado;
    }

    public String getEmpregadoPorNome(String nome, int indice ) throws  EmpregadoNaoExisteException, NaoExisteNome {
        return  EmpregadoController.getEmpregadoPorNome(nome, indice);
    }

    public void removerEmpregado(String emp) throws  EmpregadoNaoExisteException, IdentificacaoNula {
        EmpregadoController.removerEmpregado(emp);
    }


    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, SalarioNulo, TipoInvalido, EnderecoNulo, SalarioNumerico, NomeNulo, SalarioPositivo, TipoNaoAplicavel {
        return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException,  SalarioNulo, TipoInvalido, EnderecoNulo, ComissaoNumerica, SalarioNumerico, NomeNulo, ComissaoPositiva, ComissaoNula, SalarioPositivo, TipoNaoAplicavel {
        return EmpregadoController.criarEmpregado(nome,endereco,tipo,salario,comissao);
    }

    public void undo() throws Exception {
        SistemaController.popUndo();
    }

    public void redo() throws Exception {
        SistemaController.popRedo();
    }

    public void rodaFolha(String data, String saida){
        FolhaController.rodaFolha(data,saida);
    }
    public String totalFolha(String data){
       return FolhaController.totalFolha(data);
    }

    public void criarAgenda(String agenda){
        FolhaController.criarAgenda(agenda);
    }



}

