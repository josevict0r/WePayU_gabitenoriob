package br.ufal.ic.p2.wepayu.models.empregados;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

import java.util.UUID;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;

    boolean sindicalizado;
    private UUID id;

    public Empregado() {}

    public Empregado(String nome, String endereco, String tipo, String salario) throws EmpregadoNaoExisteException {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = false;
        this.id = UUID.randomUUID();

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
        salario = salario.replace(',', '.');
        return salario;
    }


    public String getId() {
        return String.valueOf(id);
    }

    public void setId(UUID id) {
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
        this.salario = salario;
    }

    public void setSindicalizado(boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }



}
