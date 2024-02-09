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

    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado empregado = empregados.get(emp);
        List<Ponto> pontos = pontosDosEmpregados.get(emp);

        // Verificações de segurança
        if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if (!"horista".equals(empregado.getTipo())) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        if (pontos == null) {
            return "0";
        }

        double totalHorasExtras = 0;

        // Converte as datas para objetos LocalDate
        LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        if(!isValidDate(dataInicial)){
            throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        }
        if(!isValidDate(dataFinal)){
            throw new EmpregadoAtributosExceptions("Data final invalida.");
        }

        if(dataInicio.isAfter(dataFim)){
            throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
        }

        for (Ponto ponto : pontos) {
            LocalDate dataPonto = LocalDate.parse(ponto.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));

            if (isDateWithinRange(dataPonto, LocalDate.from(dataInicio.atStartOfDay()), LocalDate.from(dataFim.atStartOfDay()))) {
                double horas = Double.parseDouble(ponto.getHoras().replace('.', ','));
                if (horas > 8) {
                    totalHorasExtras += horas - 8;
                }
            }
        }
        if(totalHorasExtras == 0.0){
            return "0";
        }
        else{
            return String.valueOf(totalHorasExtras);
        }

    }

    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado empregado = empregados.get(emp);
        List<Ponto> pontos = pontosDosEmpregados.get(emp);

        // Verificações de segurança
        if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if (!"horista".equals(empregado.getTipo())) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        if (pontos == null) {
            return "0";
        }
        // Converte as datas para objetos LocalDate
        LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        if(!isValidDate(dataInicial)){
            throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        }
        if(!isValidDate(dataFinal)){
            throw new EmpregadoAtributosExceptions("Data final invalida.");
        }

        if(dataInicio.isAfter(dataFim)){
            throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
        }

        double totalHorasNormais = 0;

        for (Ponto ponto : pontos) {
            LocalDate dataPonto = LocalDate.parse(ponto.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));

            if (isDateWithinRange(dataPonto, LocalDate.from(dataInicio.atStartOfDay()), LocalDate.from(dataFim.atStartOfDay()))) {
                double horas = Double.parseDouble(ponto.getHoras().replace(".",","));
                totalHorasNormais += Math.min(horas, 8); // Limita a 8 horas por dia
            }
        }

        // Ajuste para considerar apenas as horas do dia 1 se a data final for igual à data inicial
        if (dataFinal.equals(dataInicial)) {
            totalHorasNormais = Math.min(totalHorasNormais, 8);
        }

        // Retorno do valor inteiro, sem pontos ou vírgulas
        return String.valueOf((int) totalHorasNormais);
    }


    public static void lancaCartao(String emp, String data, String horas) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);

        // Verificações de segurança
        if (emp == null || emp.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        }
        if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
        if (!"horista".equals(empregado.getTipo())) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }
        if (horas == null || horas.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Horas nao podem ser nulas.");
        }
        double horasLancamento;
        try {
            horasLancamento = Double.parseDouble(horas.replace(',', '.'));
            if (horasLancamento <= 0) {
                throw new EmpregadoAtributosExceptions("Horas devem ser positivas.");
            }
        } catch (NumberFormatException e) {
            throw new EmpregadoAtributosExceptions("Horas invalidas.");
        }

        // Converte a data para objeto LocalDate
        try {
            LocalDate dataLancamento = LocalDate.parse(data, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }

        // Adiciona um novo ponto para o empregado
        pontosDosEmpregados.computeIfAbsent(emp, k -> new ArrayList<>()).add(new Ponto(data, horas));
    }
}
