package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;

public class PontoController {
    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            return "0";
        }

        if (!Objects.equals(empregado.getTipo(), "horista")) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate inicio = LocalDate.parse(dataInicial, formatter);
        LocalDate fim = LocalDate.parse(dataFinal, formatter);

        try {
            // Verificar se a data inicial é anterior ou igual à data final
            if (inicio.isBefore(fim) || inicio.isEqual(fim)) {
                System.out.println("Datas válidas e ordem correta.");
            } else {
                throw new EmpregadoAtributosExceptions("Data inicial deve ser anterior ou igual à data final.");
            }

            // Aqui você precisa implementar a lógica para verificar o lançamento do cartão
            if (!(lancaCartao(emp, dataInicial, "0"))) { // Substitua "0" pelo valor apropriado de horas
                return "0";
            }

            Duration duracao = Duration.between(inicio.atStartOfDay(), fim.atStartOfDay());
            long horas = duracao.toHours();
            return String.valueOf(horas);
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }
    }

    public static String getHorasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            return "0";
        }

        if (!Objects.equals(empregado.getTipo(), "horista")) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh horista.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicio = LocalDate.parse(dataInicial, formatter);
        LocalDate fim = LocalDate.parse(dataFinal, formatter);

        try {
            // Verificar se a data inicial é anterior ou igual à data final
            if (inicio.isBefore(fim) || inicio.isEqual(fim)) {
                System.out.println("Datas válidas e ordem correta.");
            } else {
                throw new EmpregadoAtributosExceptions("Data inicial deve ser anterior ou igual à data final.");
            }

            // Aqui você precisa implementar a lógica para verificar o lançamento do cartão
            if (!(lancaCartao(emp, dataInicial, "0"))) { // Substitua "0" pelo valor apropriado de horas
                return "0";
            }

            Duration duracao = Duration.between(inicio.atStartOfDay(), fim.atStartOfDay());
            long horas = duracao.toHours();
            return String.valueOf(horas);
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }
    }

    public static boolean lancaCartao(String emp, String data, String horas) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);
        if (emp.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        } else if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        } else if (Double.parseDouble(horas.replace(',', '.')) < 0) { // Substitui a vírgula por ponto para parsing de double
            throw new EmpregadoAtributosExceptions("Horas devem ser positivas.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            LocalDate dataLancamento = LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }

        // Verificação adicional de mês válido
        String[] partesData = data.split("/");
        int mes = Integer.parseInt(partesData[1]);
        if (mes < 1 || mes > 12) {
            throw new EmpregadoAtributosExceptions("Data invalida: mês inválido.");
        }

        // Implemente aqui a lógica para verificar se o cartão foi lançado corretamente
        // Retorna verdadeiro se o lançamento foi bem-sucedido e falso caso contrário
        return true;
    }

}
