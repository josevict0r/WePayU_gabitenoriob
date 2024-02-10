package br.ufal.ic.p2.wepayu.models;

public class Sindicato {
    private String idSindicato;
    private double taxaSindical;

    public Sindicato() {}

    public Sindicato(String idSindicato, double taxaSindical) {
        this.idSindicato = idSindicato;
        this.taxaSindical = taxaSindical;
    }

    public double getTaxaSindical() {
        return taxaSindical;
    }

    public void setTaxaSindical(double taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    public String getIdSindicato() {
        return idSindicato;
    }

    public void setIdSindicato(String idSindicato) {
        this.idSindicato = idSindicato;
    }
}