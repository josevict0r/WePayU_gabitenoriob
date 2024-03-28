package br.ufal.ic.p2.wepayu.models;

public class Agenda {
	private String regime;
    private int semanaDoMes;
    private int dia;
    
    
    public Agenda(){}
    
    public Agenda(String regime, int semanaDoMes, int diaDaSemana){
    	this.setRegime(regime);
    	this.setSemanaDoMes(semanaDoMes);
    	this.setDiaDaSemana(diaDaSemana);
    }

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public int getDiaDaSemana() {
		return dia;
	}

	public void setDiaDaSemana(int diaDaSemana) {
		this.dia = diaDaSemana;
	}

	public int getSemanaDoMes() {
		return semanaDoMes;
	}

	public void setSemanaDoMes(int diaDoMes) {
		this.semanaDoMes = diaDoMes;
	}
	
}
