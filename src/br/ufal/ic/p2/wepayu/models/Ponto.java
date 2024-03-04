package br.ufal.ic.p2.wepayu.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class Ponto implements Serializable {


    private String id;
    private LocalDate data;
    private Double horas;
    

    public Ponto(){}
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
    public void setId(String id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }
}



