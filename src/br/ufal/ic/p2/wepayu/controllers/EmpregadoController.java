package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.empregados.Assalariado;
import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
import br.ufal.ic.p2.wepayu.models.empregados.Horista;

import java.util.Arrays;
import java.util.HashMap;

public class EmpregadoController {
    public static HashMap<String, Empregado> empregados = new HashMap<>();

    public  static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException{
        Empregado empregado = empregados.get(emp);


        if(emp.isEmpty()){
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        }
        if((empregados.get(emp) == null)){
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if (!(Arrays.asList("nome", "endereco", "tipo", "salario", "sindicalizado", "comissao").contains(atributo))) {
            throw new EmpregadoAtributosExceptions("Atributo nao existe.");
        }
        if(atributo.equals("sindicalizado")){
           if(!(empregado.getSindicalizado())){
               return "false";
           }
        }
        return switch (atributo) {
            case "nome" -> empregado.getNome();
            case "endereco" -> empregado.getEndereco();
            case "tipo" -> empregado.getTipo();
            case "salario" -> empregado.getSalario();
            case "comissao" -> {
                if (empregado instanceof Comissionado) {
                    yield ((Comissionado) empregado).getComissao();
                } else {
                    throw new EmpregadoAtributosExceptions("Empregado não é do tipo Comissionado.");
                }
            }
            case "sindicalizado" -> String.valueOf(empregado.getSindicalizado());
            default -> throw new EmpregadoAtributosExceptions("Atributo nao existe.");
        };


    }
    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {

        //CRIAR EMPREGADOS DO TIPO ASSALARIADO E HORISTA
        salario = salario.replace(',', '.');


        if (tipo.equals("assalariado")){
            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
            if (tipo.equalsIgnoreCase("comissionado")){
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel");
            }
            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }



            Empregado empregado = new Assalariado(nome,endereco,tipo,salario);
            String id = empregado.addEmpregado();
            empregados.put(empregado.addEmpregado(), empregado);
            return  id;

        } else if (tipo.equals("horista")) {

            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }

            }
        else {
            if (tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
            } else {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
        }


            Empregado empregado= new Horista(nome,endereco,tipo,salario);
           String id = empregado.addEmpregado();
            //O ADDEMPREGADO SO TA SERVINDO P PEGAR ID
            empregados.put(id, empregado);
            return id;


        }



    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {
        salario = salario.replace(',', '.');

        if (tipo.equals("comissionado")){
            if (nome.isBlank()){
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }

            if (salario.isEmpty()) throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            if (!isNumeric(salario)) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if (comissao.isEmpty()) throw new EmpregadoAtributosExceptions("Comissao nao pode ser nula.");
            if (!isNumeric(comissao)) {
                throw new EmpregadoAtributosExceptions("Comissao deve ser numerica.");
            }
            if (Double.parseDouble(comissao) < 0) {
                throw new EmpregadoAtributosExceptions("Comissao deve ser nao-negativa.");
            }



            Empregado empregado = new Comissionado(nome,endereco,tipo,salario,comissao);
            //O ADDEMPREGADO SO TA SERVINDO P PEGAR ID
            String id = empregado.addEmpregado();
            empregados.put(id, empregado);
            return id;

        }
        else{

            if (tipo.equals("horista") || tipo.equals("assalariado")){
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
            }
            else  {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }


        }


    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
