package br.ufal.ic.p2.wepayu.utils;


import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class isValidDate {
    public static boolean isValidDate(String data) throws Exception {

        if (!data.matches("^\\d{1,2}\\s*(\\/|-)\\s*\\d{1,2}\\s*(\\/|-)\\s*\\d{4}$")) {
            return false;
        }
        // Divide a string em dia, mês e ano
        String[] partesData = data.split("(\\/|-)");
        int dia = Integer.parseInt(partesData[0]);
        int mes = Integer.parseInt(partesData[1]);
        int ano = Integer.parseInt(partesData[2]);

        // Verifica se o dia e o mês estão dentro dos limites válidos
        if (dia < 1 || dia > 31 || mes < 1 || mes > 12) {
            return false;
        }

        // Verifica se o ano é bissexto
        boolean isBissexto = (ano % 4 == 0) && ((ano % 100 != 0) || (ano % 400 == 0));

        // Ajusta o número de dias em fevereiro para anos bissextos
        int maxDiasFevereiro = isBissexto ? 29 : 28;

        // Verifica se o dia é válido para o mês
        if (mes == 2 && dia > maxDiasFevereiro) {
            return false;
        }

        // Verifica se a string tem o formato correto

        // Se todos os testes passarem, a data é válida
        return true;
    }


}