package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDateTime;

public class Servico {

    private String data;
    private String valor;
    public Servico(String data, String valor) {
        this.data = data;
        this.valor = valor;
    }

    public Servico(){}


        // Getters e Setters
        public String getData() {
            return data;
        }

        public void setData(LocalDateTime data) {
            this.data = String.valueOf(data);
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

