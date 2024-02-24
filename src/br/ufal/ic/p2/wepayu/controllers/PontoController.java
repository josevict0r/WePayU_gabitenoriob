package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.models.Ponto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.*;

public class PontoController {

    static ArrayList<Ponto> pontos = new ArrayList<Ponto>();


    public static String getHorasTrabalhadas(String emp, String dataInicial, String dataFinal, int NormalOuExtra) throws Exception {
        double horasNormais = 0;
        double horasExtras = 0;
        if(!empregados.get(emp).getTipo().equals("horista")) throw new NaoHorista();


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

        for(Ponto ponto : pontos) {

            if(ponto.getId().equals(emp)) {

                if(ponto.getId().equals(emp) && ponto.getData().equals(Final)) break;
                if (ponto.getData().isEqual(Inicial) || (ponto.getData().isAfter(Inicial) && ponto.getData().isBefore(Final))) {//DA ERRADO DE UM DIA PRO OUTRO
                    if(ponto.getHoras() > 8) {
                        horasNormais += 8;

                        horasExtras += ponto.getHoras()-8;

                    }
                    else horasNormais += ponto.getHoras();
                }

            }
        }
        String horasNormaisStr = (Double.toString(horasNormais)).replace(".", ",");
        if(horasNormais - (int) horasNormais == 0) {
            String[] horasNormaisStrsplit = horasNormaisStr.split(",");
            horasNormaisStr = horasNormaisStrsplit[0];
        }

        String horasExtrasStr = (Double.toString(horasExtras)).replace(".", ",");
        if(horasExtras - (int) horasExtras == 0) {
            String[] horasExtrasStrsplit = horasExtrasStr.split(",");
            horasExtrasStr = horasExtrasStrsplit[0];
        }

        if(NormalOuExtra == 1) return horasNormaisStr;
        else return horasExtrasStr;
    }


    public static void lancaCartao(String emp, String data, String horas) throws HoraPositiva, NaoHorista, EmpregadoNaoExisteException, IdentificacaoNula, DataInvalida {
        double horasNum = Double.parseDouble(horas.replace(",", "."));
        if(emp.isEmpty()) {
            throw new IdentificacaoNula();
        }
        if(empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        }
        if(!empregados.get(emp).getTipo().equals("horista")) {
            throw new NaoHorista();
        }
        String[] diaMesAnoStr = data.split("/");
        ArrayList<Integer> diaMesAno = new ArrayList<Integer>();
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[0]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[1]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[2]));
        data = (Integer.toString((int) diaMesAno.get(0)).concat("/" + Integer.toString((int)diaMesAno.get(1)))).concat("/" + Integer.toString((int)diaMesAno.get(2)));
        if(diaMesAno.get(1) >= 13) throw new DataInvalida();
        if(horasNum <= 0) throw new HoraPositiva();


        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataobj;
        dataobj = LocalDate.parse(data, formatoData);

        Ponto ponto = new Ponto(dataobj, horasNum, emp);
        pontos.add(ponto);
        pontosDosEmpregados.put(emp, pontos);
        pontosDosEmpregadosPersistencia.put(emp,pontos);
    }
}
