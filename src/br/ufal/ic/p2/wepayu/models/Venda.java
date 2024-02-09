package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDateTime;

public class Venda {
    private String dataInicial;
    private String valor;

    public Venda() {}

    // Construtor
    public Venda(String dataInicial, String valor) {
        this.dataInicial = dataInicial;
        this.valor = valor;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}

// Getters e Setters

