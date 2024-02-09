package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.models.Servico;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.ufal.ic.p2.wepayu.controllers.SistemaController.empregados;
import static br.ufal.ic.p2.wepayu.controllers.SistemaController.servicoDosEmpregados;
import static br.ufal.ic.p2.wepayu.utils.isValidDate.isValidDate;

public class ServicoController {
    public static void lancaTaxaServico(String emp, String data, String valor) throws Exception {
        if (emp == null || emp.isBlank()) {
            throw new EmpregadoAtributosExceptions("Identificacao do membro nao pode ser nula.");
        }

        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Membro nao existe.");
        }

        if (!empregado.getSindicalizado()) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh sindicalizado.");
        }

        if (valor == null || Double.parseDouble(valor) <= 0) {
            throw new EmpregadoAtributosExceptions("Valor deve ser positivo.");
        }

        if (data == null || !isValidDate(data)) {
            throw new EmpregadoAtributosExceptions("Data invalida.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataLancamento = LocalDate.parse(data, formatter);

        servicoDosEmpregados.computeIfAbsent(emp, k -> new ArrayList<>())
                .add(new Servico(data, valor));
    }

    public static String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        if (emp == null || emp.isBlank()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        }

        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }

        if (!empregado.getSindicalizado()) {
            throw new EmpregadoAtributosExceptions("Empregado nao eh sindicalizado.");
        }

        if (!isValidDate(dataInicial)) {
            throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        }

        if (!isValidDate(dataFinal)) {
            throw new EmpregadoAtributosExceptions("Data final invalida.");
        }

        LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        if (dataInicio.isAfter(dataFim)) {
            throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
        }

        List<Servico> servicos = servicoDosEmpregados.get(emp);
        if (servicos == null || servicos.isEmpty()) {
            return "0,00";
        }

        double totalTaxa = 0;
        for (Servico servico : servicos) {
            LocalDate dataVenda = LocalDate.parse(servico.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));
            if (!dataVenda.isBefore(dataInicio) && dataVenda.isBefore(dataFim.plusDays(1))) {
                totalTaxa += Double.parseDouble(servico.getValor().replace(',', '.'));
            }
        }

        return String.valueOf(totalTaxa);
    }
}
