    package br.ufal.ic.p2.wepayu.controllers;

    import br.ufal.ic.p2.wepayu.exceptions.*;

    import br.ufal.ic.p2.wepayu.models.Banco;
    import br.ufal.ic.p2.wepayu.models.Sindicato;
    import br.ufal.ic.p2.wepayu.models.empregados.Comissionado;
    import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

    import java.text.DecimalFormat;
    import java.util.*;


    import static br.ufal.ic.p2.wepayu.controllers.ServicoController.sindicatos;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
    import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregadosPersistencia;
    import static br.ufal.ic.p2.wepayu.utils.isNumeric.isNumeric;
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
        public static String getAtributoEmpregado(String emp, String atributo) throws EmpregadoNaoExisteException, IdentificacaoNula, AtributoNaoExiste, NaoBanco, NaoSindicalizado, NaoComissionado {
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
                if (buscado instanceof Comissionado) {
                    Comissionado comissionado = (Comissionado) buscado;
                    resultado = comissionado.getComissao();
                }
                else{
                    throw new NaoComissionado();
                }

            }
            else if(atributo.equals("metodoPagamento")){
                resultado = buscado.getMetodoPagamento();
            }
            else if(atributo.equals("banco")){
                if(buscado.getMetodoPagamento() == "banco"){
                    resultado = String.valueOf(buscado.getBanco().getNome());
                }
                else{
                    throw new NaoBanco();
                }
            }
            else if(atributo.equals("taxaSindical")){
                if(buscado.getSindicato() != null){
                    double taxaSindical = buscado.getSindicato().getTaxaSindical();

                    // Formatando a taxa com duas casas decimais
                    DecimalFormat df = new DecimalFormat("#0.00");
                    String resultadoFormatado = df.format(taxaSindical);

                    resultado = resultadoFormatado.replace(".", ",");
                }
                else{
                    throw new NaoSindicalizado();
                }
            }
            else if(atributo.equals("idSindicato")){
                if(buscado.getSindicato() != null){
                    resultado = buscado.getSindicato().getIdSindicato();
                }
                else{
                    throw new NaoSindicalizado();
                }
            }
            else if(atributo.equals("agencia")){
                if(buscado.getMetodoPagamento() == "banco"){
                    resultado = String.valueOf(buscado.getBanco().getAgencia());
                }
                else{
                    throw new NaoBanco();
                }
            }
            else if(atributo.equals("contaCorrente")){
                if(buscado.getMetodoPagamento() == "banco"){
                    resultado = String.valueOf(buscado.getBanco().getContaCorrente());
                }
                else{
                    throw new NaoBanco();
                }
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

        //altera empregados = faço um empregado a ser atualizado, atualizo ele e ponho ELE na lista de empregados empregados e empregadosPersistencia
        //RECEBE SINDICALIZADO E TRUE AUTOMATICO
        public static void alteraEmpregadoSindicalizado(String emp, String atributo,boolean valor, String idSindicato, String taxaSindical) throws IdentificacaoRepetida, IdSindicatoNula, TaxaNumerica, TaxaNegativa, TaxaNula, ValorTrueFalse {
                Empregado atualizar = empregados.get(emp);
                if(idSindicato.isEmpty()){
                    throw new IdSindicatoNula();
                }
                if(taxaSindical.isEmpty()){
                    throw new TaxaNula();
                }
                if(taxaSindical.contains("-")){
                    throw new TaxaNegativa();
                }
                if(!isNumeric(taxaSindical)){
                    throw new TaxaNumerica();
                }
                if(valor != true && valor!= false){
                    throw new ValorTrueFalse();
                }
                if(sindicatos.containsKey(idSindicato)) throw new IdentificacaoRepetida();

                double taxaSindicalD = Double.parseDouble(taxaSindical);

                Sindicato sindicato = new Sindicato(idSindicato, taxaSindicalD);
                sindicatos.put(sindicato.getIdSindicato(), sindicato);

                atualizar.setSindicalizado(valor);
                atualizar.setSindicato(sindicato);

                empregados.put(emp,atualizar);
                empregadosPersistencia.add(atualizar);
        }


        //ALTERA GERAL
        public static void alteraAtributoEmpregado(String emp, String atributo, String valor1) throws NaoComissionado, EmpregadoNaoExisteException, AtributoNaoExiste, NomeNulo, SalarioPositivo, SalarioNumerico, SalarioNulo, TipoInvalido, EnderecoNulo, ComissaoPositiva, ComissaoNumerica, ComissaoNula, MetodoInvalido, IdentificacaoNula, ValorTrueFalse {
            Empregado atualizar = empregados.get(emp);
            if(emp.isEmpty()){
                throw new IdentificacaoNula();
            }
            if(atualizar == null){
                throw new EmpregadoNaoExisteException();
            }

            if(atributo.equals("nome")) {
                if(valor1.isEmpty()){
                    throw new NomeNulo();
                }
                else{
                    atualizar.setNome(valor1);
                }


            }
            if(atributo.equals("endereco")) {
                if(valor1.isEmpty()){
                    throw new EnderecoNulo();
                }
                atualizar.setEndereco(valor1);
            }
            if(atributo.equals("tipo")) {
                if(!valor1.contains("horista") && !valor1.contains("assalariado") && !valor1.contains("comissionado")){
                    throw new TipoInvalido();
                }
                //ver se tem q mudar algo em outro lugar, pq se mudou o tipo deve fazer o cast
                atualizar.setTipo(valor1);
            }
            if(atributo.equals("salario")) {

                if (valor1.isEmpty()){
                    throw new SalarioNulo();
                }
                if (!isNumeric(valor1)){
                    throw new SalarioNumerico();
                }
                if(valor1.contains("-")){
                    throw new SalarioPositivo();
                }

                atualizar.setSalario(valor1);
            }
            if(atributo.equals("comissao")) {
                if(atualizar.getTipo() != "comissionado") {
                    throw new NaoComissionado();
                }
                if (valor1.isEmpty()){
                    throw new ComissaoNula();
                }
                if (!isNumeric(valor1)){
                    throw new ComissaoNumerica();
                }
                if(valor1.contains("-")){
                    throw new ComissaoPositiva();
                }


                else{
                    atualizar.setTipo(valor1);
                }

            }
            if(atributo.equals("metodoPagamento")){
                if(!valor1.contains("emMaos") && !valor1.contains("correios") && !valor1.contains("banco")){
                    throw new MetodoInvalido();
                }
            }
            if(atributo.equals("sindicalizado")){
                if(valor1 != "true" && valor1 != "false"){
                    throw new ValorTrueFalse();
                }
            }
            else{
                throw new AtributoNaoExiste();
            }

            empregados.put(emp,atualizar);
            empregadosPersistencia.add(atualizar);
        }
        //RECEBE SEMPRE SINDICALIZADO E FALSE
        public static void alteraEmpregado(String emp, String atributo, Boolean valor1) throws IdentificacaoNula, ValorTrueFalse, NaoComissionado, EmpregadoNaoExisteException {
            Empregado atualizar = empregados.get(emp);
            if(emp.isEmpty()){
                throw new IdentificacaoNula();
            }
            if(atualizar == null){
                throw new EmpregadoNaoExisteException();
            }
            if(valor1!= true && valor1!= false){
                throw  new ValorTrueFalse();
            }
            if(atributo=="comissao"){
                if(atualizar.getTipo() != "comissionado"){
                    throw new NaoComissionado();
                }
            }
            atualizar.setSindicalizado(valor1);
            atualizar.setSindicato(null);
            empregados.put(emp,atualizar);
            empregadosPersistencia.add(atualizar);
        }

        //É UM ALTERA Q JA RECEBE METODO E BANCO
        public static void adicionaMetodoPagamento(String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws ContaNula, AgenciaNula, BancoNulo, EmpregadoNaoExisteException, IdentificacaoNula {
            Banco bancoobj = new Banco(banco, agencia, contaCorrente);
            if(banco.isEmpty()){
                throw new BancoNulo();
            }
            if(agencia.isEmpty()){
                throw  new AgenciaNula();
            }
            if(contaCorrente.isEmpty()){
                throw new ContaNula();
            }
            Empregado atualizar = empregados.get(emp);
            if(emp.isEmpty()){
                throw new IdentificacaoNula();
            }
            if(atualizar == null){
                throw new EmpregadoNaoExisteException();
            }
            atualizar.setMetodoPagamento(valor1);;

            if(valor1.equals("banco")) {
                atualizar.setBanco(bancoobj);
                System.out.println("Esse é o banco novo add:" + bancoobj.getAgencia() + bancoobj.getNome() + bancoobj.getContaCorrente());
            }

            empregadosPersistencia.add(atualizar);
            empregados.put(emp,atualizar);
        }
    }



