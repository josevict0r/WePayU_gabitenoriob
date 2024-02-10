package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class Venda {
    public String id;
    public LocalDate data;
    public double valor;

    public Venda(String id, LocalDate data, double valor) {
        this.id = id;
        this.data = data;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }
}