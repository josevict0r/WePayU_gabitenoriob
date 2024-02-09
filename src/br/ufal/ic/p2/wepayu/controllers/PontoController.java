package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
import br.ufal.ic.p2.wepayu.models.Ponto;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.*;
import static br.ufal.ic.p2.wepayu.utils.isDateWithinRange.isDateWithinRange;
import static br.ufal.ic.p2.wepayu.utils.isValidDate.isValidDate;


public class PontoController {


    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {

        //erros
        Empregado empregado = empregados.get(emp);
        List<Ponto> pontos = pontosDosEmpregados.get(emp);


        if(empregado.getTipo() != "horista"){
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        if (!pontosDosEmpregados.containsValue(dataInicial)) {
            return "0";
        }
        if (!pontosDosEmpregados.containsKey(emp)) {
            return "0";
        }
        else if (!pontosDosEmpregados.containsValue(dataFinal)) {
            return "0";
        }
        else{//existe ponto

            // Converte as datas para objetos LocalDate
            LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));
            if(dataInicio.isAfter(dataFim)){
                throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
            }
            if (!isValidDate(String.valueOf(dataInicio)) ) {
                throw new EmpregadoAtributosExceptions("Data inicial invalida.");
            }
            if( !isValidDate(String.valueOf(dataFim))){
                throw new EmpregadoAtributosExceptions("Data final invalida.");
            }
            if (pontos.stream().noneMatch(ponto -> isDateWithinRange(ponto.getData(), dataInicial, dataFinal))) {
                return "0";
            }


            // Inicializa o total de horas normais e extras
            double totalHorasNormais = 0;
            double totalHorasExtras = 0;



            for (Ponto ponto : pontos) {
                LocalDate dataPonto = LocalDate.parse(ponto.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));


                if (!dataPonto.isBefore(dataInicio) && !dataPonto.isAfter(dataFim)) {
                    double horas = Double.parseDouble(ponto.getHoras().replace(',', '.'));
                    // Verifica se o total de horas normais excede 8 horas por dia
                    if (totalHorasNormais + horas <= 8) {
                        totalHorasNormais += horas;
                    } else {
                        totalHorasExtras += horas - 8;
                        totalHorasNormais = 8;
                    }
                }
            }


            return String.valueOf(totalHorasExtras);
        }


    }

    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        //erros
        Empregado empregado = empregados.get(emp);
        List<Ponto> pontos = pontosDosEmpregados.get(emp);
        if(empregado.getTipo() != "horista"){
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        if (!pontosDosEmpregados.containsValue(dataInicial)) {
            return "0";
        }
        if (!pontosDosEmpregados.containsKey(emp)) {
            return "0";
        }
        else if (!pontosDosEmpregados.containsValue(dataFinal)) {
            return "0";
        }

// Verifica se pelo menos um dos pontos está dentro do intervalo de datas fornecido
        if (pontos.stream().noneMatch(ponto -> isDateWithinRange(ponto.getData(), dataInicial, dataFinal))) {
            return "0";
        }
        else{//existe ponto

            // Converte as datas para objetos LocalDate
            LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));
            if(dataInicio.isAfter(dataFim)){
                throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
            }
            try {
                dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
                dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));
            } catch (DateTimeParseException e) {
                if (e.getErrorIndex() == 0) {
                    throw new EmpregadoAtributosExceptions("Data inicial invalida.");
                } else {
                    throw new EmpregadoAtributosExceptions("Data final invalida.");
                }
            }
            if (!isValidDate(String.valueOf(dataInicio)) ) {
                throw new EmpregadoAtributosExceptions("Data inicial invalida.");
            }
            if( !isValidDate(String.valueOf(dataFim))){
                throw new EmpregadoAtributosExceptions("Data final invalida.");
            }


            // Inicializa o total de horas normais e extras
            double totalHorasNormais = 0;
            double totalHorasExtras = 0;


            for (Ponto ponto : pontos) {
                LocalDate dataPonto = LocalDate.parse(ponto.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));


                if (!dataPonto.isBefore(dataInicio) && !dataPonto.isAfter(dataFim)) {
                    double horas = Double.parseDouble(ponto.getHoras().replace(',', '.'));
                    // Verifica se o total de horas normais excede 8 horas por dia
                    if (totalHorasNormais + horas <= 8) {
                        totalHorasNormais += horas;
                    } else {
                        totalHorasExtras += horas - 8;
                        totalHorasNormais = 8;
                    }
                }
            }


            return String.valueOf(totalHorasNormais);
        }

        }


    public static void lancaCartao(String emp, String data, String horas) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);
        //erros
        if (emp.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        } else if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        } else if (Double.parseDouble(horas.replace(',', '.')) <= 0) { // Substitui a vírgula por ponto para parsing de double
            throw new EmpregadoAtributosExceptions("Horas devem ser positivas.");
        } else if (empregado.getTipo() != "horista") {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate dataLancamento = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }
        String[] partesData = data.split("/");
        int mes = Integer.parseInt(partesData[1]);
        if (mes < 1 || mes > 12) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }

        //logica, add um ponto c essa data e hora p usario do id emp
        else{
            if (!pontosDosEmpregados.containsKey(emp)) {
                pontosDosEmpregados.put(emp, new ArrayList<>());
            }

            Ponto ponto = new Ponto(data, horas);
            pontosDosEmpregados.get(emp).add(ponto);

        }


    }


}
