package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.models.Venda;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import static br.ufal.ic.p2.wepayu.controllers.SistemaController.*;

public class VendasController {

    static ArrayList<Venda> vendas = new ArrayList<Venda>();

    public static void lancaVenda(String emp, String data, String valor) throws Exception {
        if(emp.isEmpty()) {
            throw new IdentificacaoNula();
        }
        if(empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        }
        if(!empregados.get(emp).getTipo().equals("comissionado")) {
            throw new NaoComissionado();
        }
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
        //System.out.println("valor q deveria colocar na venda -->" + valor);
        double valorD = Double.parseDouble(valorPonto);
        if(valorD<= 0) throw new ValorPositivo();
        //System.out.println("valor q coloca na venda -->" + valorD);
        Venda venda = new Venda(emp, dataobj, valorD);
        vendas.add(venda);

    }




    public static String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado empregado = empregados.get(emp);
        if (!"comissionado".equals(empregado.getTipo())) {
            throw new NaoComissionado();
        }

        double vendasRequisitadas = 0;
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

        for(Venda venda : vendas) {
            if(venda.getId().equals(emp)) {
                if(venda.getId().equals(emp) && venda.getData().equals(Final)) break;
                if (venda.getData().isEqual(Inicial) || (venda.getData().isAfter(Inicial) && venda.getData().isBefore(Final))) {//DA ERRADO DE UM DIA PRO OUTRO
                    vendasRequisitadas += venda.getValor();

                }
            }


        }
        String vendasRequisitadasStr = (Double.toString(vendasRequisitadas)).replace(".", ",");//0,00
        String[] vendasRequisitadasStrsplit = vendasRequisitadasStr.split(",");
        if(vendasRequisitadas - (int) vendasRequisitadas == 0) vendasRequisitadasStr = vendasRequisitadasStrsplit[0].concat(",00");
        if(vendasRequisitadasStrsplit[1].length() == 2) vendasRequisitadasStr = vendasRequisitadasStrsplit[0].concat("," + vendasRequisitadasStrsplit[1]);
        else vendasRequisitadasStr = vendasRequisitadasStrsplit[0].concat("," + vendasRequisitadasStrsplit[1] + "0");

        return vendasRequisitadasStr;
    }



}

