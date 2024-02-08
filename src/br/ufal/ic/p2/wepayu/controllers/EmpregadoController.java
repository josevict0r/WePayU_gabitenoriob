    package br.ufal.ic.p2.wepayu.controllers;

    import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
    import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
    import br.ufal.ic.p2.wepayu.models.empregados.Assalariado;
    import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
    import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
    import br.ufal.ic.p2.wepayu.models.empregados.Horista;

    import java.util.Arrays;

    import java.util.UUID;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;

    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;

    public class EmpregadoController {
        //public static HashMap<String, Empregado> empregados = new HashMap<>();

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





        public static String getEmpregadoPorNome(String nome, int indice) throws EmpregadoAtributosExceptions {
            if (nome.isBlank()) {
                throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
            }

            int count = 0;
            for (Empregado empregado : empregados.values()) {
                String nomeEmpregado = empregado.getNome();
                //System.out.println(nomeEmpregado);
                if (nomeEmpregado.equals(nome)) {
                    if (count == indice) {
                        return nomeEmpregado; // conferir se é p retornar NOME OU ID
                    }
                    count++;
                }
            }


            throw new EmpregadoAtributosExceptions("Nao ha empregado com esse nome.");
        }


        public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {
            if (emp == null || emp.isEmpty()) {
                throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
            }

            Empregado empregado = empregados.get(emp);
            System.out.println(empregado);

            if (empregado == null) {
                throw new EmpregadoAtributosExceptions("Empregado nao existe.");
            }

            if (!(Arrays.asList("nome", "endereco", "tipo", "salario", "sindicalizado", "comissao").contains(atributo))) {
                throw new EmpregadoAtributosExceptions("Atributo nao existe.");
            }

            if (atributo.equals("sindicalizado")) {
                if (!empregado.getSindicalizado()) {
                    return "false";
                }
            }

            return switch (atributo) {

                case "nome" -> empregado.getNome();
                case "endereco" -> empregado.getEndereco();
                case "tipo" -> empregado.getTipo();
                case "salario" -> {
                    String salario = empregado.getSalario().replace(".", ",");
                    if (!salario.contains(",")) {
                        salario = salario.concat(",00");
                    }
                    yield salario;
                }
                case "comissao" -> {
                    if (empregado instanceof Comissionado) {
                        yield ((Comissionado) empregado).getComissao().replace(".", ",");
                    } else {
                        throw new EmpregadoAtributosExceptions("Empregado nao e do tipo comissionado.");
                    }
                }
                case "sindicalizado" -> String.valueOf(empregado.getSindicalizado());
                default -> throw new EmpregadoAtributosExceptions("Atributo nao existe.");
            };
        }

        public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws EmpregadoAtributosExceptions, EmpregadoNaoExisteException {

            // Substituir vírgulas por pontos no salário, se houver
            if(salario != null){
                salario = salario.replace(',', '.');
            }


            if (nome.isBlank()) {
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (salario.isBlank()) {
                throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            }


            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if (!isNumeric(salario)) {

                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }

            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }

            if (tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
            }

            Empregado empregado = null;
            if (tipo.equalsIgnoreCase("horista")) {
                // Cria um novo empregado do tipo horista
                empregado = new Horista(nome, endereco, tipo, salario);
            } else if (tipo.equalsIgnoreCase("assalariado")) {
                // Cria um novo empregado do tipo assalariado
                empregado = new Assalariado(nome, endereco, tipo, salario);
            }

            // Gera uma identificação única para o empregado
            String id = empregado.getId();

            // Adiciona o empregado ao HashMap usando a identificação como chave
            empregados.put(id, empregado);
            empregado.setId(UUID.fromString(id));
            empregadosPersistencia.add(empregado);

            // Retorna a identificação do empregado criado
            return id;
        }

        public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws EmpregadoNaoExisteException, EmpregadoAtributosExceptions {

            // Substituir vírgulas por pontos no salário e na comissão, se houver
            if(salario!= null){
                salario = salario.replace(',', '.');
            }
            if(comissao != null){
                comissao = comissao.replace(',', '.');
            }


            if (nome.isBlank()) {
                throw new EmpregadoAtributosExceptions("Nome nao pode ser nulo.");
            }
            if (endereco.isBlank()) {
                throw new EmpregadoAtributosExceptions("Endereco nao pode ser nulo.");
            }
            if (salario.isBlank()) {
                throw new EmpregadoAtributosExceptions("Salario nao pode ser nulo.");
            }
            if (comissao.isBlank()) {
                throw new EmpregadoAtributosExceptions("Comissao nao pode ser nula.");
            }
            if (Double.parseDouble(salario) < 0) {
                throw new EmpregadoAtributosExceptions("Salario deve ser nao-negativo.");
            }
            if (Double.parseDouble(comissao) < 0) {
                throw new EmpregadoAtributosExceptions("Comissao deve ser nao-negativa.");
            }
            if (!isNumeric(salario)) {

                throw new EmpregadoAtributosExceptions("Salario deve ser numerico.");
            }
            if (!isNumeric(comissao)) {

                throw new EmpregadoAtributosExceptions("Comissao deve ser numerica.");
            }

            if (!tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo nao aplicavel.");
            }

            if (!tipo.equalsIgnoreCase("horista") && !tipo.equalsIgnoreCase("assalariado") && !tipo.equalsIgnoreCase("comissionado")) {
                throw new EmpregadoAtributosExceptions("Tipo invalido.");
            }

            // Cria um novo empregado do tipo comissionado
            Empregado empregado = new Comissionado(nome, endereco, tipo, salario, comissao);

            // Gera uma identificação única para o empregado
            String id = empregado.getId();

            // Adiciona o empregado ao HashMap usando a identificação como chave
            empregados.put(id, empregado);
            empregado.setId(UUID.fromString(id));
            empregadosPersistencia.add(empregado);

            // Retorna a identificação do empregado criado
            return id;
        }


        public static boolean isNumeric(String str) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }


    }



