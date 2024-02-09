package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoAtributosExceptions;
import br.ufal.ic.p2.wepayu.models.Ponto;
import br.ufal.ic.p2.wepayu.models.Venda;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


import static br.ufal.ic.p2.wepayu.controllers.SistemaController.*;
import static br.ufal.ic.p2.wepayu.utils.isValidDate.isValidDate;

public class VendasController {


    //O sistema anotará a informação do resultado de venda e a associará ao empregado correto.
    public static void lancaVenda(String emp, String data, String valor) throws EmpregadoAtributosExceptions {
        Empregado empregado = empregados.get(emp);
        //erros
        if(Double.parseDouble(valor) <= 0){
            throw new EmpregadoAtributosExceptions("Valor deve ser positivo.");
        }
        if (emp.isEmpty()) {
            throw new EmpregadoAtributosExceptions("Identificacao do empregado nao pode ser nula.");
        } else if (empregado == null) {
            throw new EmpregadoAtributosExceptions("Empregado nao existe.");
        }
         else if (empregado.getTipo() != "comissionado") {
            throw new EmpregadoAtributosExceptions("Empregado nao eh comissionado.");
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
            if (!vendasDosEmpregados.containsKey(emp)) {
                vendasDosEmpregados.put(emp, new ArrayList<>());
            }

            Venda venda = new Venda(data, valor);
            vendasDosEmpregados.get(emp).add(venda);

        }


    }

    public static String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws Exception {
        Empregado empregado = empregados.get(emp);
        if(empregado.getTipo() != "comissionado"){
            throw new EmpregadoAtributosExceptions("Empregado nao eh comissionado.");
        }

        // Verifica se pelo menos um dos pontos está dentro do intervalo de datas fornecido
        LocalDate dateInicial;
        LocalDate dateFinal;
        try {
            dateInicial = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        }

        // Validação da data final
        try {
            dateFinal = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new EmpregadoAtributosExceptions("Data final invalida.");
        }

        if (!isValidDate(String.valueOf(dateInicial))) {
            throw new EmpregadoAtributosExceptions("Data inicial invalida.");
        }
        if(!isValidDate(String.valueOf(dateFinal))){
            throw new EmpregadoAtributosExceptions("Data final invalida.");
        }

        List<Venda> vendas = vendasDosEmpregados.get(emp);
        if (vendas == null || vendas.isEmpty()) {
            return "0,00"; // Retorna zero formatado com vírgula
        }

        // Converte as datas para objetos LocalDate
        LocalDate dataInicio = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("d/M/yyyy"));

        if (dataInicio.isAfter(dataFim)) {
            throw new EmpregadoAtributosExceptions("Data inicial nao pode ser posterior aa data final.");
        }

        double totalVendas = 0;
        for (Venda venda : vendas) {
            LocalDate dataVenda = LocalDate.parse(venda.getDataInicial(), DateTimeFormatter.ofPattern("d/M/yyyy"));
            // Verifica se a data da venda está dentro do intervalo [dataInicio, dataFinal)
            if (!dataVenda.isBefore(dataInicio) && dataVenda.isBefore(dataFim.plusDays(1))) {
                totalVendas += Double.parseDouble(venda.getValor().replace(',', '.'));
            }
        }


        // Formata o valor com vírgula antes de retornar
        return String.format("%.2f", totalVendas).replace('.', ',');
    }



}

