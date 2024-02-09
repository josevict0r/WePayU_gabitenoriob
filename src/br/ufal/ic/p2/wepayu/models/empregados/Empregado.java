package br.ufal.ic.p2.wepayu.models.empregados;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

import java.util.Objects;
import java.util.UUID;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;

    boolean sindicalizado;


    private String id;

/*Os seguintes atributos de um empregado podem ser alterados: nome, endereço, tipo (hourly,salaried,commisioned),
 método de pagamento, se pertence ao sindicato ou não, identificação no sindicato, taxa sindica*/
    private  String idSindicato;
    private String taxaSindical;

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    private String metodoPagamento;

    private String banco;
     private String agencia;

    public Empregado() {}

    public Empregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        if(salario != null){
            this.salario = salario;
        }
        else{
            this.salario = "0,00";
        }

        this.sindicalizado = false;
        this.id = String.valueOf(UUID.randomUUID());

    }


    public boolean getSindicalizado() {
        return sindicalizado;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSalario() {
        return salario;
    }


    public String getId() {
        return String.valueOf(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSalario(String salario) {
        this.salario = Objects.requireNonNullElse(salario, "0,00");
    }

    public void setSindicalizado(boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public String getIdSindicato() {
        return idSindicato;
    }

    public void setIdSindicato(String idSindicato) {
        this.idSindicato = idSindicato;
    }

    public String getTaxaSindical() {
        return taxaSindical;
    }

    public void setTaxaSindical(String taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    public boolean isSindicalizado() {
        return sindicalizado;
    }



}
