package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.models.Ponto;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.pontosDosEmpregados;
import static br.ufal.ic.p2.wepayu.utils.isDateWithinRange.isDateWithinRange;
import static br.ufal.ic.p2.wepayu.utils.isValidDate.isValidDate;

public class PontoController {

    static ArrayList<Ponto> pontos = new ArrayList<Ponto>();


    public static String getHorasTrabalhadas(String emp, String dataInicial, String dataFinal, int NormalOuExtra) throws Exception {
        double horasNormais = 0;
        double horasExtras = 0;
        if(!empregados.get(emp).getTipo().equals("horista")) throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");


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


        if(diaMesAnoI.get(0) > 31) throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        if(diaMesAnoF.get(1) == 2) {
            if(diaMesAnoF.get(0) > 29) throw new EmpregadoAtributosExceptions("Data final invalida.");
        }
        if(diaMesAnoI.get(1) >= diaMesAnoF.get(1)){
            if(diaMesAnoI.get(0) > diaMesAnoF.get(0)) throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");;
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
                        //((cartoes.get(i).getData().isAfter(Inicial) && cartoes.get(i).getData().isBefore(Final))
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

        //System.out.println("---->normais = " + horasNormais);
        //System.out.println("---->extras = " + horasExtras);
        if(NormalOuExtra == 1) return horasNormaisStr;
        else return horasExtrasStr;
    }


    public static void lancaCartao(String emp, String data, String horas) throws EmpregadoAtributosExceptions {
        double horasNum = Double.parseDouble(horas.replace(",", "."));
        if(emp.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        }
        if(empregados.get(emp) == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if(!empregados.get(emp).getTipo().equals("horista")) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        String[] diaMesAnoStr = data.split("/");
        ArrayList<Integer> diaMesAno = new ArrayList<Integer>();
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[0]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[1]));
        diaMesAno.add(Integer.parseInt(diaMesAnoStr[2]));             //Convertendo a data para int pra poder fazer os testes de erro
        data = (Integer.toString((int) diaMesAno.get(0)).concat("/" + Integer.toString((int)diaMesAno.get(1)))).concat("/" + Integer.toString((int)diaMesAno.get(2)));
        //System.out.println("---->" + diaMesAno.get(1));
        //System.out.println(data);
        if(diaMesAno.get(1) >= 13) throw new EmpregadoAtributosExceptions("Data invalida.");
        if(horasNum <= 0) throw new EmpregadoAtributosExceptions("Horas devem ser positivas.");


        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataobj;
        dataobj = LocalDate.parse(data, formatoData);

        Ponto ponto = new Ponto(dataobj, horasNum, emp);
        pontos.add(ponto);
    }
}
