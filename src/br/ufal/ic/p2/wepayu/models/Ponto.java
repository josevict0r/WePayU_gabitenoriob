package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ponto {
    private String data;
    private String horas;

    public Ponto(){}

    // Construtor
    public Ponto(String entrada, String horas) {
        this.data = entrada;
        if(horas != null){
            this.horas = horas;
        }
        else{
            this.horas = "0";
        }
    }

    // Getters e Setters
    public String getData() {
        return data;
    }

    public void setEntrada(String entrada) {
        this.data = entrada;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = Objects.requireNonNullElse(horas, "0");
    }
}

