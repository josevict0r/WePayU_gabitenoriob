package br.ufal.ic.p2.wepayu.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufal.ic.p2.wepayu.utils.convertDoubleToString;
import br.ufal.ic.p2.wepayu.utils.convertStringToDate;
import br.ufal.ic.p2.wepayu.utils.utilsFolha.convertStringToStringFolha;

public class FolhaController {

   

    public static void rodaFolha(String data, String saida) {
        

        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;

        
        String date = convertStringToStringFolha.convertStringToStringFolha(data);

         

        try (BufferedReader reader = new BufferedReader(new FileReader("folha-" + date + ".txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\s+");

                if (partes.length > 0 && partes[0].equalsIgnoreCase("TOTAL")) {
                    break; // Se encontrou a linha TOTAL, pare o processamento
                }

                if (partes.length >= 7) {
                    double salarioBruto = Double.parseDouble(partes[3].replace(",", ""));
                    double descontos = Double.parseDouble(partes[4].replace(",", ""));
                    double salarioLiquido = Double.parseDouble(partes[5].replace(",", ""));

                    totalSalarioBruto += salarioBruto;
                    totalDescontos += descontos;
                    totalSalarioLiquido += salarioLiquido;

                    // Implemente a lógica para salvar a saída no arquivo especificado
                    // usando a variável 'saida'
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String totalFolha(String data) {
        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;
         String date = convertStringToStringFolha.convertStringToStringFolha(data);

        try (BufferedReader reader = new BufferedReader(new FileReader("folha-" + date + ".txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\s+");

                if (partes.length > 0 && partes[0].equalsIgnoreCase("TOTAL")) {
                    break; 
                }

                if (partes.length >= 7) {
                    double salarioBruto = Double.parseDouble(partes[3].replace(",", ""));
                    double descontos = Double.parseDouble(partes[4].replace(",", ""));
                    double salarioLiquido = Double.parseDouble(partes[5].replace(",", ""));

                    totalSalarioBruto += salarioBruto;
                    totalDescontos += descontos;
                    totalSalarioLiquido += salarioLiquido;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String totales = convertDoubleToString.convertDoubleToString(totalSalarioBruto,2);

        return totales;
    }

    public static void criarAgenda(String agenda) {
        
    }
    
}
