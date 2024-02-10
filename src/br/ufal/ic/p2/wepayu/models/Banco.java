package br.ufal.ic.p2.wepayu.models;

public class Banco {
    private String nome;
    private String agencia;
    private String contaCorrente;

    public Banco() {}

    public Banco(String nome, String agencia, String contaCorrente) {
        this.setNome(nome);
        this.setAgencia(agencia);
        this.setContaCorrente(contaCorrente);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

}