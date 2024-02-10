package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ponto {
    private String id;
    private LocalDate data;
    private Double horas;


    // Construtor
    public Ponto(LocalDate data, Double horas, String id) {
        this.data = data;
        this.horas = horas;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Double getHoras() {
        return horas;
    }
}

// Getters e Setters


