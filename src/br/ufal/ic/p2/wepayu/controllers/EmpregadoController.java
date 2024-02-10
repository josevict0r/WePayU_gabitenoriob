    package br.ufal.ic.p2.wepayu.controllers;

    import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
    import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

    import br.ufal.ic.p2.wepayu.models.Banco;
    import br.ufal.ic.p2.wepayu.models.Sindicato;
    import br.ufal.ic.p2.wepayu.models.empregados.Assalariado;
    import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
    import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
    import br.ufal.ic.p2.wepayu.models.empregados.Horista;
    import java.util.*;


    import static br.ufal.ic.p2.wepayu.controllers.ServicoController.sindicatos;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;
    import static br.ufal.ic.p2.wepayu.utils.isNumeric.isNumeric;

    public class EmpregadoController {



        //ok
        public static void removerEmpregado(String emp) throws EmpregadoAtributosExceptions {
            if (emp.isBlank()) {
                throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
            }


            if (!empregados.containsKey(emp)) {
                throw new EmpregadoAtributosExceptions("Empregado nao existe.");
            } else {
                empregados.remove(emp);
            }

        }

        //ok
        public static String getEmpregadoPorNome(String nome, int indice) throws EmpregadoAtributosExceptions {
            String idAchado = null;


            for (String key: empregados.keySet()) {

                if(empregados.get(key).getNome().equals(nome)) {
                    indice--;
                    if(indice == 0) {
                        idAchado = empregados.get(key).getId();
                        return idAchado;
                    }

                }
            }
            throw new EmpregadoAtributosExceptions("Nao ha empregado com esse nome.");

        }

        //ok
        public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {
            String resultado = null;
            Empregado buscado = empregados.get(emp);

            if(emp.isEmpty()) {
                throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
            }
            if(empregados.get(emp) == null) {
                throw new EmpregadoAtributosExceptions("Empregado nao existe.");
            }

            if(atributo.equals("nome")) {
                resultado = buscado.getNome();
            }
            else if(atributo.equals("endereco")) {
                resultado = buscado.getEndereco();
            }
            else if(atributo.equals("tipo")) {
                resultado = buscado.getTipo();
            }
            else if(atributo.equals("salario")) {
                if(!(buscado.getSalario().contains(",")) && !(buscado.getSalario().contains("."))) {
                    resultado = buscado.getSalario().concat(",00");
                }
                else resultado = buscado.getSalario();
            }
            else if(atributo.equals("sindicalizado")) {
                boolean cut = buscado.isSindicalizado();// CONVERTER DE BOOL PRA STRING
                resultado = Boolean.toString(cut);
            }
            else if (atributo.equals("comissao")) {
                Comissionado casted = (Comissionado) buscado;
                resultado = casted.getComissao();
            }
            else {
                throw new EmpregadoAtributosExceptions("Atributo nao existe.");
            }
            return resultado;
        }
        //ok
        public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {

            Empregado novo = new Empregado(nome, endereco, tipo, salario);
            String id = UUID.randomUUID().toString();

            if(nome.isEmpty()) {
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if(novo.getEndereco().isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            //System.out.print(novo.tipo);
            if(novo.getTipo().equals("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
            }
            if(!(novo.getTipo().equals("horista")) && !(novo.getTipo().equals("assalariado"))) {

                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }
            if(novo.getSalario().isBlank()) {
                throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            }
            if(novo.getSalario().contains("-")) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if(!isNumeric(novo.getSalario().replace(",", "."))) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }

            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            //System.out.println("therezaaa" + id);
            return id;
        }
        //ok
        public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {

            Empregado novo = new Comissionado(nome, endereco, tipo, salario, comissao);
            String id = UUID.randomUUID().toString();
            //char menos = novo.salario.charAt(0);

            if(nome.isEmpty()) {
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if(novo.getEndereco().isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            //System.out.print(novo.tipo);
            Comissionado aux = (Comissionado)novo;
            if(!(novo.getTipo().equals("comissionado"))) {
                if(!(aux.getComissao().isBlank())) {
                    throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
                }
                else {
                    throw new EmpregadoAtributosExceptions("Tipo invalido.");
                }
            }
            if(novo.getSalario().isBlank()) {
                throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            }
            if(novo.getSalario().contains("-")) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if(!isNumeric(novo.getSalario().replace(",", "."))) {
                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if(novo.getTipo().equals("comissionado")) {
                if(comissao.isBlank()) {
                    throw new EmpregadoAtributosExceptions("Comissao nao pode ser nula.");
                }
                if(!isNumeric(comissao.replace(",", "."))) {
                    throw new EmpregadoAtributosExceptions("Comissao deve ser numerica.");
                }
                if(comissao.contains("-")) {
                    throw new EmpregadoAtributosExceptions("Comissao deve ser nao-negativa.");
                }
            }

            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            return id;
        }

        //altera empregados

        public static void alteraEmpregado(String emp, String atributo,boolean valor, String idSindicato, String taxaSindical) throws EmpregadoAtributosExceptions {
                if(sindicatos.containsKey(idSindicato)) throw new EmpregadoAtributosExceptions("Ha outro empregado com esta identificacao de sindicato");

                String taxaSindicalPonto = taxaSindical.replace(",", ".");
                double taxaSindicalD = Double.parseDouble(taxaSindicalPonto);

                Sindicato sindicato = new Sindicato(idSindicato, taxaSindicalD);
                sindicatos.put(sindicato.getIdSindicato(), sindicato);

                empregados.get(emp).setSindicalizado(valor);
                empregados.get(emp).setSindicato(sindicato);
        }

        public static void alteraEmpregado(String emp, String sindicalizado, String valor1) {
        //dessindicaliza
            empregados.get(emp).setSindicalizado(Boolean.parseBoolean(valor1));
            empregados.get(emp).setSindicato(null);
        }


        public static void alteraAtributoEmpregado(String emp, String atributo, String valor1) throws EmpregadoAtributosExceptions {
            if(atributo.equals("nome")) {
                empregados.get(emp).setNome(valor1);
                //System.out.println(empregados.get(emp));
            }
            if(atributo.equals("endereco")) {
                empregados.get(emp).setEndereco(valor1);
            }
            if(atributo.equals("tipo")) {
                empregados.get(emp).setTipo(valor1);//criar outro com a classe do tipo novo e excluir o que ja tem
            }
            if(atributo.equals("salario")) {
                //String salarioPonto = valor1.replace(",", ".");
                //double salarioD = Double.parseDouble(salarioPonto);
                empregados.get(emp).setSalario(valor1);
            }
            if(atributo.equals("comissao")) {
                if(!empregados.get(emp).getTipo().equals("comissionado")) throw new EmpregadoAtributosExceptions("Empregado nao eh comissionado.");
                empregados.get(emp).setTipo(valor1);

            }
        }

        public static void alteraEmpregado(String emp, String atributo, Boolean valor1) {
            empregados.get(emp).setSindicalizado(valor1);
            empregados.get(emp).setSindicato(null);
        }

        public static void adicionaMetodoPagamento(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) {
            Banco bancoobj = new Banco(banco, agencia, contaCorrente);
            empregados.get(emp).setMetodoPagamento(valor1);;

            if(valor1.equals("banco")) {
                empregados.get(emp).setBanco(bancoobj);
            }
        }
    }



