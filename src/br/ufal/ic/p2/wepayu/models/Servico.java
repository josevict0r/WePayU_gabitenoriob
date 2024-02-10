package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class Servico {
    public String idSindicato;
    public LocalDate data;
    public double valor;

    public Servico(String id, LocalDate data, double valor) {
        this.idSindicato = id;
        this.data = data;
        this.valor = valor;
    }

    public String getIdSindicato() {
        return idSindicato;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }
}