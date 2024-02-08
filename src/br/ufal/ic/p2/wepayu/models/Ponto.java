package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDateTime;

public class Ponto {
    private String data;
    private String horas;

    public Ponto(){}

    // Construtor
    public Ponto(String entrada, String saida) {
        this.data = entrada;
        this.horas = horas;
    }

    // Getters e Setters
    public String getData() {
        return data;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.data = String.valueOf(entrada);
    }

    public String getHoras() {
        return horas;
    }

    public void setSaida(String horas) {
        this.horas = horas;
    }
}

