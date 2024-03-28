package br.ufal.ic.p2.wepayu.models;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private double salario;
    private boolean sindicalizado;
    private String id;
    private Sindicato sindicato;
    protected String metodoPagamento;
    protected String agendaPagamento;
    protected Banco banco;

    public Empregado() {
    }

    public Empregado(String nome, String endereco, String tipo, double salario) 
        {
            this.nome = nome;
            this.endereco = endereco;
            this.tipo = tipo;
            this.salario = salario;
            this.sindicalizado = false;
            this.id = null;
            this.sindicato = null;
            this.metodoPagamento = "emMaos";
            this.banco = null;
            this.agendaPagamento = getDefaultAgendaPagamento(); 

        
    
        


    }

    private String getDefaultAgendaPagamento() {
        
        if ("horista".equals(tipo)) {
            return "semanal 5";
        } else if ("assalariado".equals(tipo)) {
            return "mensal $";
        } else {
            return "semanal 2 5";
        }
    
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public boolean isSindicalizado() {
        return sindicalizado;
    }

    public void setSindicalizado(boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sindicato getSindicato() {
        return sindicato;
    }

    public void setSindicato(Sindicato sindicato) {
        this.sindicato = sindicato;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
   
    public String getAgendaPagamento() {
    
        return agendaPagamento;
    }

    public void setAgendaPagamento(String agendaPagamento) {
        this.agendaPagamento = agendaPagamento;
    }


}
