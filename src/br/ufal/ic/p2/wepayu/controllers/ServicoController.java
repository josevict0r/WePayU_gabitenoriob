package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.models.Servico;
import br.ufal.ic.p2.wepayu.models.Sindicato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;

public class ServicoController {

    static ArrayList<Servico> taxas = new ArrayList<Servico>();
    static LinkedHashMap<String, Sindicato> sindicatos = new LinkedHashMap<String, Sindicato>();
    public static void lancaTaxaServico(String emp, String data, String valor) throws Exception {
        if(emp.isEmpty()) throw new MembroNulo();
        if(sindicatos.get(emp) == null) throw new MembroNaoExiste();


        String[] diaMesAnoStr = data.split("/");
        ArrayList<Integer> diaMesAno = new ArrayList<Integer>();
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[0]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[1]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[2]));             //Convertendo a data para int pra poder fazer os testes de erro
        data = (Integer.toString((int) diaMesAno.get(0)).concat("/" + Integer.toString((int)diaMesAno.get(1)))).concat("/" + Integer.toString((int)diaMesAno.get(2)));

        if(diaMesAno.get(1) >= 13) throw new DataInvalida();



        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataobj;
        dataobj = LocalDate.parse(data, formatoData);

        String valorPonto = valor.replace(",", ".");
        double valorD = Double.parseDouble(valorPonto);
        if(valorD<= 0) throw new ValorPositivo();
        Servico taxaServico = new Servico(emp, dataobj, valorD);

        taxas.add(taxaServico);
    }

    public static String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        double taxasServico = 0;
        if(!empregados.get(emp).isSindicalizado()) throw new NaoSindicalizado();

        String[] diaMesAnoStrI = dataInicial.split("/");
        ArrayList<Integer> diaMesAnoI = new ArrayList<Integer>();
        diaMesAnoI.add(Integer.parseInt(diaMesAnoStrI[0]));
        diaMesAnoI.add(Integer.parseInt(diaMesAnoStrI[1]));
        diaMesAnoI.add(Integer.parseInt(diaMesAnoStrI[2]));

        String[] diaMesAnoStrF = dataFinal.split("/");
        ArrayList<Integer> diaMesAnoF = new ArrayList<Integer>();
        diaMesAnoF.add(Integer.parseInt(diaMesAnoStrF[0]));
        diaMesAnoF.add(Integer.parseInt(diaMesAnoStrF[1]));
        diaMesAnoF.add(Integer.parseInt(diaMesAnoStrF[2]));


        if(diaMesAnoI.get(0) > 31) throw new DataInicialInvalida();
        if(diaMesAnoF.get(1) == 2) {
            if(diaMesAnoF.get(0) > 29) throw new DataFinalInvalida();
        }
        if(diaMesAnoI.get(1) >= diaMesAnoF.get(1)){
            if(diaMesAnoI.get(0) > diaMesAnoF.get(0)) throw new DataInicialPosterior();
        }

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial, Final;

        Inicial = LocalDate.parse(dataInicial, formatoData);
        Final = LocalDate.parse(dataFinal, formatoData);

        for(Servico taxa : taxas) {


            if(taxa.getIdSindicato().equals(empregados.get(emp).getSindicato().getIdSindicato())) {

                if(empregados.get(emp).getSindicato().getIdSindicato().equals(taxa.getIdSindicato()) && taxa.getData().equals(Final)) break;
                if (taxa.getData().isEqual(Inicial) || (taxa.getData().isAfter(Inicial) && taxa.getData().isBefore(Final))) {//DA ERRADO DE UM DIA PRO OUTRO
                    taxasServico += taxa.getValor();
                }
            }


        }
        String taxasServicoStr = (Double.toString(taxasServico)).replace(".", ",");//0,00
        String[] taxasServicoStrsplit = taxasServicoStr.split(",");
        if(taxasServico - (int) taxasServico == 0) taxasServicoStr = taxasServicoStrsplit[0].concat(",00");
        if(taxasServicoStrsplit[1].length() == 2) taxasServicoStr = taxasServicoStrsplit[0].concat("," + taxasServicoStrsplit[1]);
        else taxasServicoStr = taxasServicoStrsplit[0].concat("," + taxasServicoStrsplit[1] + "0");


        return taxasServicoStr;
    }
}
