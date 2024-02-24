    package br.ufal.ic.p2.wepayu.controllers;

    import br.ufal.ic.p2.wepayu.exceptions.*;

    import br.ufal.ic.p2.wepayu.models.Banco;
    import br.ufal.ic.p2.wepayu.models.Sindicato;
    import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
    import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

    import java.util.*;


    import static br.ufal.ic.p2.wepayu.controllers.ServicoController.sindicatos;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;
    import static br.ufal.ic.p2.wepayu.utils.isNumeric.isNumeric;

    public class EmpregadoController {



        //ok
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

        //ok
        public static String getEmpregadoPorNome(String nome, int indice) throws NaoExisteNome {
           String idAchado;


            for (String key: empregados.keySet()) {

                if(empregados.get(key).getNome().equals(nome)) {
                    indice--;
                    if(indice == 0) {
                        idAchado = empregados.get(key).getId();
                        return idAchado;
                    }

                }
            }
            throw new NaoExisteNome();

        }

        //ok
        public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdentificacaoNula, AtributoNaoExiste {
            String resultado = null;
            Empregado buscado = empregados.get(emp);

            if(emp.isEmpty()) {
                throw new IdentificacaoNula();
            }
            if(empregados.get(emp) == null) {
                throw new EmpregadoNaoExisteException();
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
                throw new AtributoNaoExiste();
            }
            return resultado;
        }
        //ok
        public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException, SalarioNumerico, SalarioPositivo, SalarioNulo, TipoInvalido, TipoNaoAplicavel, EnderecoNulo, NomeNulo {

            Empregado novo = new Empregado(nome, endereco, tipo, salario);
            String id = UUID.randomUUID().toString();

            if(nome.isEmpty()) {
                throw new NomeNulo();
            }
            if(novo.getEndereco().isBlank()) {
                throw new EnderecoNulo();
            }
            if(tipo.equals("comissionado")) {
                throw new TipoNaoAplicavel();
            }
            if(!(novo.getTipo().equals("horista")) && !(novo.getTipo().equals("assalariado"))) {

                throw new TipoInvalido();
            }
            if(novo.getSalario().isBlank()) {
                throw new SalarioNulo();
            }
            if(novo.getSalario().contains("-")) {
                throw new SalarioPositivo();
            }
            if(!isNumeric(novo.getSalario().replace(",", "."))) {
                throw new SalarioNumerico();
            }

            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            //System.out.println("therezaaa" + id);
            return id;
        }
        //ok
        public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, ComissaoPositiva, ComissaoNumerica, ComissaoNula, SalarioNumerico, SalarioPositivo, SalarioNulo, TipoInvalido, TipoNaoAplicavel, EnderecoNulo, NomeNulo {

            Empregado novo = new Comissionado(nome, endereco, tipo, salario, comissao);
            String id = UUID.randomUUID().toString();
            //char menos = novo.salario.charAt(0);

            if(nome.isEmpty()) {
                throw new NomeNulo();
            }
            if(novo.getEndereco().isBlank()) {
                throw new EnderecoNulo();
            }
            //System.out.print(novo.tipo);
            Comissionado aux = (Comissionado)novo;
            if(tipo.equals("horista") || tipo.equals("assalariado")) {
                throw new TipoNaoAplicavel();
            }
            if(!(novo.getTipo().equals("horista")) && !(novo.getTipo().equals("assalariado")) && !(novo.getTipo().equals("comissionado"))) {

                throw new TipoInvalido();
            }
            if(novo.getSalario().isBlank()) {
                throw new SalarioNulo();
            }
            if(novo.getSalario().contains("-")) {
                throw new SalarioPositivo();
            }
            if(!isNumeric(novo.getSalario().replace(",", "."))) {
                throw new SalarioNumerico();
            }
            if(novo.getTipo().equals("comissionado")) {
                if(comissao.isBlank()) {
                    throw new ComissaoNula();
                }
                if(!isNumeric(comissao.replace(",", "."))) {
                    throw new ComissaoNumerica();
                }
                if(comissao.contains("-")) {
                    throw new ComissaoPositiva();
                }
            }

            empregados.put(id, novo);
            novo.setId(id);
            empregadosPersistencia.add(novo);
            return id;
        }

        //altera empregados

        public static void alteraEmpregado(String emp, String atributo,boolean valor, String idSindicato, String taxaSindical) throws IdentificacaoRepetida {
                if(sindicatos.containsKey(idSindicato)) throw new IdentificacaoRepetida();

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


        public static void alteraAtributoEmpregado(String emp, String atributo, String valor1) throws NaoComissionado {
            if(atributo.equals("nome")) {
                empregados.get(emp).setNome(valor1);
                //System.out.println(empregados.get(emp));
            }
            if(atributo.equals("endereco")) {
                empregados.get(emp).setEndereco(valor1);
            }
            if(atributo.equals("tipo")) {
                empregados.get(emp).setTipo(valor1);
            }
            if(atributo.equals("salario")) {

                empregados.get(emp).setSalario(valor1);
            }
            if(atributo.equals("comissao")) {
                if(!empregados.get(emp).getTipo().equals("comissionado")) throw new NaoComissionado();
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



