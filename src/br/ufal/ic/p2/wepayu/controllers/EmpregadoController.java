package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.exceptions.*;

import br.ufal.ic.p2.wepayu.models.Banco;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.Sindicato;

import java.text.DecimalFormat;
import java.util.*;


import static br.ufal.ic.p2.wepayu.controllers.ServicoController.sindicatos;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;
import static br.ufal.ic.p2.wepayu.utils.isNumeric.isNumeric;



public class EmpregadoController {


    public static void removerEmpregado(String emp) throws IdentificacaoNula, EmpregadoNaoExisteException {
        if (emp.isBlank()) {
            throw new IdentificacaoNula();
        }


        if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        } else {
            empregados.remove(emp);
        }

    }

    //rever
    public static String getEmpregadoPorNome(String nome, int indice) throws NaoExisteNome {
        String idAchado;


        for (String key : empregados.keySet()) {

            if (empregados.get(key).getNome().equals(nome)) {
                indice--;
                if (indice == 0) {
                    idAchado = empregados.get(key).getId();
                    return idAchado;
                }

            }
        }
        throw new NaoExisteNome();

    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, SalarioNumerico, SalarioPositivo, SalarioNulo, TipoInvalido, TipoNaoAplicavel, EnderecoNulo, NomeNulo {

        Empregado novo = new Empregado(nome, endereco, tipo, salario,null);
        String id = UUID.randomUUID().toString();

        if (nome.isEmpty()) {
            throw new NomeNulo();
        }
        if (novo.getEndereco().isBlank()) {
            throw new EnderecoNulo();
        }
        if (tipo.equals("comissionado")) {
            throw new TipoNaoAplicavel();
        }
        if (!(novo.getTipo().equals("horista")) && !(novo.getTipo().equals("assalariado"))) {

            throw new TipoInvalido();
        }
        if (novo.getSalario().isBlank()) {
            throw new SalarioNulo();
        }
        if (novo.getSalario().contains("-")) {
            throw new SalarioPositivo();
        }
        if (!isNumeric(novo.getSalario().replace(",", "."))) {
            throw new SalarioNumerico();
        }

        empregados.put(id, novo);
        novo.setId(id);
        empregadosPersistencia.add(novo);
        return id;
    }

    
    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, ComissaoPositiva, ComissaoNumerica, ComissaoNula, SalarioNumerico, SalarioPositivo, SalarioNulo, TipoInvalido, TipoNaoAplicavel, EnderecoNulo, NomeNulo {

        Empregado novo = new Empregado(nome, endereco, tipo, salario, comissao);
        String id = UUID.randomUUID().toString();
        

        if (nome.isEmpty()) {
            throw new NomeNulo();
        }
        if (novo.getEndereco().isBlank()) {
            throw new EnderecoNulo();
        }
        if (tipo.equals("horista") || tipo.equals("assalariado")) {
            throw new TipoNaoAplicavel();
        }
        if (!(novo.getTipo().equals("horista")) && !(novo.getTipo().equals("assalariado")) && !(novo.getTipo().equals("comissionado"))) {

            throw new TipoInvalido();
        }
        if (novo.getSalario().isBlank()) {
            throw new SalarioNulo();
        }
        if (novo.getSalario().contains("-")) {
            throw new SalarioPositivo();
        }
        if (!isNumeric(novo.getSalario().replace(",", "."))) {
            throw new SalarioNumerico();
        }
        if (novo.getTipo().equals("comissionado")) {
            if (comissao.isBlank()) {
                throw new ComissaoNula();
            }
            if (!isNumeric(comissao.replace(",", "."))) {
                throw new ComissaoNumerica();
            }
            if (comissao.contains("-")) {
                throw new ComissaoPositiva();
            }
        }

        empregados.put(id, novo);
        novo.setId(id);
        empregadosPersistencia.add(novo);
        return id;
    }


    public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdentificacaoNula, AtributoNaoExiste, NaoBanco, NaoSindicalizado, NaoComissionado {
        String resultado = null;
        Empregado buscado = empregados.get(emp);
        //System.out.println(buscado.getNome() + " " + buscado.getTipo());

        if (emp.isEmpty()) {
            throw new IdentificacaoNula();
        }
        else if (buscado == null) {
            throw new EmpregadoNaoExisteException();
        }
        if (atributo.equals("nome")) {
            resultado = buscado.getNome();
        } else if (atributo.equals("endereco")) {
            resultado = buscado.getEndereco();
        } else if (atributo.equals("tipo")) {
            resultado = buscado.getTipo();
        } else if (atributo.equals("salario")) {
            if (!(buscado.getSalario().contains(",")) && !(buscado.getSalario().contains("."))) {
                resultado = buscado.getSalario().concat(",00");
            } else resultado = buscado.getSalario();
        } else if (atributo.equals("sindicalizado")) {
            
            boolean cut = buscado.isSindicalizado();
            resultado = Boolean.toString(cut);
            
        } 
        
        
        else if (atributo.equals("comissao")) {
            if (buscado.getTipo() == "comissionado" ) {
                
                resultado = buscado.getComissao();
                    
                }
         else {
                throw new NaoComissionado();
            }

        } 
        
        
        else if (atributo.equals("metodoPagamento")) {
            resultado = buscado.getMetodoPagamento();
        } else if (atributo.equals("banco")) {
            if (buscado.getMetodoPagamento() == "banco") {
                resultado = String.valueOf(buscado.getBanco().getNome());
            } else {
                throw new NaoBanco();
            }
        } else if (atributo.equals("taxaSindical")) {
            if (buscado.getSindicato() != null) {
                double taxaSindical = buscado.getSindicato().getTaxaSindical();

                DecimalFormat df = new DecimalFormat("#0.00");
                String resultadoFormatado = df.format(taxaSindical);

                resultado = resultadoFormatado.replace(".", ",");
            } else {
                throw new NaoSindicalizado();
            }
        } else if (atributo.equals("idSindicato")) {
            if (buscado.getSindicato() != null) {
                resultado = buscado.getSindicato().getIdSindicato();
            } else {
                throw new NaoSindicalizado();
            }
        } else if (atributo.equals("agencia")) {
            if (buscado.getMetodoPagamento() == "banco") {
                resultado = String.valueOf(buscado.getBanco().getAgencia());
            } else {
                throw new NaoBanco();
            }
        } else if (atributo.equals("contaCorrente")) {
            if (buscado.getMetodoPagamento() == "banco") {
                resultado = String.valueOf(buscado.getBanco().getContaCorrente());
            } else {
                throw new NaoBanco();
            }
        } else {
            throw new AtributoNaoExiste();
        }
        return resultado;
    }

    //altera empregados = faço um empregado a ser atualizado, atualizo ele e ponho ELE na lista de empregados empregados e empregadosPersistencia
    public static void alteraEmpregadoSindicalizado(String emp, String atributo, boolean valor, String idSindicato, String taxaSindical) throws IdentificacaoRepetida, IdSindicatoNula, TaxaNumerica, TaxaNegativa, TaxaNula, ValorTrueFalse {
        System.out.println("ENTRA NO ALTERA SINDICALIZADO");
        Empregado atualizar = empregados.get(emp);
        atualizar.setSindicalizado(valor);
        if (idSindicato.isEmpty()) {
            throw new IdSindicatoNula();
        }
        if (taxaSindical.isEmpty()) {
            throw new TaxaNula();
        }
        if (taxaSindical.contains("-")) {
            throw new TaxaNegativa();
        }
        if (!isNumeric(taxaSindical)) {
            throw new TaxaNumerica();
        }
        if (valor != true && valor != false) {
            throw new ValorTrueFalse();
        }
        if (sindicatos.containsKey(idSindicato)) throw new IdentificacaoRepetida();

        double taxaSindicalD = Double.parseDouble(taxaSindical);

        Sindicato sindicato = new Sindicato(idSindicato, taxaSindicalD);
        sindicatos.put(sindicato.getIdSindicato(), sindicato);
        atualizar.setSindicato(sindicato);

        empregados.put(emp, atualizar);
        empregadosPersistencia.add(atualizar);
    }

    //ALTERA GERAL - 3 variaveis
    public static void alteraAtributoEmpregado(String emp, String atributo, String valor1) throws NaoComissionado, EmpregadoNaoExisteException, AtributoNaoExiste, NomeNulo, SalarioPositivo, SalarioNumerico, SalarioNulo, TipoInvalido, EnderecoNulo, ComissaoPositiva, ComissaoNumerica, ComissaoNula, MetodoInvalido, IdentificacaoNula, ValorTrueFalse {
         System.out.println("ENTRA NO ALTERA GERAL VALOR 1");
        Empregado atualizar = empregados.get(emp);
        if (emp.isEmpty()) {
            throw new IdentificacaoNula();
        }
        if (atualizar == null) {
            throw new EmpregadoNaoExisteException();
        }

        if (atributo.equals("nome")) {
            if (valor1.isEmpty()) {
                throw new NomeNulo();
            } else {
                atualizar.setNome(valor1);
            }


        } else if (atributo.equals("endereco")) {
            if (valor1.isEmpty()) {
                throw new EnderecoNulo();
            }
            atualizar.setEndereco(valor1);
        } else if (atributo.equals("tipo")) {
            if (!valor1.contains("horista") && !valor1.contains("assalariado") && !valor1.contains("comissionado")) {
                throw new TipoInvalido();
            }
            //ver se tem q mudar algo em outro lugar, pq se mudou o tipo deve fazer o cast
            atualizar.setTipo(valor1);
        } else if (atributo.equals("salario")) {

            if (valor1.isEmpty()) {
                throw new SalarioNulo();
            }
            if (!isNumeric(valor1)) {
                throw new SalarioNumerico();
            }
            if (valor1.contains("-")) {
                throw new SalarioPositivo();
            }

            atualizar.setSalario(valor1);
        } else if (atributo.equals("comissao")) {
            if (atualizar.getTipo() != "comissionado") {
                throw new NaoComissionado();
            }
            if (valor1.isEmpty()) {
                throw new ComissaoNula();
            }
            if (!isNumeric(valor1)) {
                throw new ComissaoNumerica();
            }
            if (valor1.contains("-")) {
                throw new ComissaoPositiva();
            } else {

                atualizar.setComissao(valor1); 
                
            }

        } else if (atributo.equals("metodoPagamento")) {
            if (!valor1.contains("emMaos") && !valor1.contains("correios") && !valor1.contains("banco")) {
                throw new MetodoInvalido();
            }
            else{
                atualizar.setMetodoPagamento(valor1);
            }
        } else if (atributo.equals("sindicalizado")) {
                if (valor1.equals("true")) {
                    atualizar.setSindicalizado(true);
                } else if(valor1.equals("false")){
                    atualizar.setSindicato(null);
                    atualizar.setSindicalizado(false);
                }else{
                    throw new ValorTrueFalse();
                }
            }
         else {
            throw new AtributoNaoExiste();
        }

        empregados.put(emp, atualizar);
        empregadosPersistencia.add(atualizar);
    }

    //4 variaveis, AQUI ELE MUDA O TIPO DE EMPREGADO E JA POE UM NOVO VALOR E SALARIO OU DE COMISSAO OU ALGO ASSIM
    public static void alteraEmpregado (String emp, String atributo, String valor, String comissao) throws IdentificacaoNula, EmpregadoNaoExisteException {
         System.out.println("ENTRA NO ALTERA QUE RECEBE A COMISSAO");
        
        Empregado atualizar = empregados.get(emp);
        if (emp.isEmpty()) {
            throw new IdentificacaoNula();
          
        }
        if (atualizar == null) {
            throw new EmpregadoNaoExisteException();
          
        }

        if (atributo.equals("tipo")) {
            if (valor.equals("comissionado")) {
                atualizar.setTipo("comissionado");
            }
            else if(valor.equals("horista")){
                atualizar.setTipo("horista");
                atualizar.setSalario(comissao);
            }
        }

        
        empregados.put(emp, atualizar);
        empregadosPersistencia.add(atualizar);
        
    }


    //6 variaveis
    public static void adicionaMetodoPagamento(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws ContaNula, AgenciaNula, BancoNulo, EmpregadoNaoExisteException, IdentificacaoNula {
         System.out.println("ENTRA NO ALTERA DE METODO DE PAGAMENTO");
        Banco bancoobj = new Banco(banco, agencia, contaCorrente);
        if (banco.isEmpty()) {
            throw new BancoNulo();
        }
        if (agencia.isEmpty()) {
            throw new AgenciaNula();
        }
        if (contaCorrente.isEmpty()) {
            throw new ContaNula();
        }
        Empregado atualizar = empregados.get(emp);
        if (emp.isEmpty()) {
            throw new IdentificacaoNula();
        }
        if (atualizar == null) {
            throw new EmpregadoNaoExisteException();
        }
        atualizar.setMetodoPagamento(valor1);
        ;

        if (valor1.equals("banco")) {
            atualizar.setBanco(bancoobj);
            System.out.println("Esse é o banco novo add:" + bancoobj.getAgencia() + bancoobj.getNome() + bancoobj.getContaCorrente());
        }

        empregadosPersistencia.add(atualizar);
        empregados.put(emp, atualizar);
    }
}



