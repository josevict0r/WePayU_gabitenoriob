package br.ufal.ic.p2.wepayu.controllers;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import br.ufal.ic.p2.wepayu.utils.convertDoubleToString;
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

   /*public static double totalHoristas(String data) {
    double valorTotal = 0.0;

    for (Ponto ponto : pontos.values()) {
    if (empregados.get(ponto.getId()).getTipo().equalsIgnoreCase("horista")) {
        double salarioHora = Double.parseDouble(empregados.get(ponto.getId()).getSalario().replace(",", "."));

        if (ponto.getData().toString().equals(data)) {
            double horas = ponto.getHoras();
            valorTotal += horas * salarioHora;
        }
    }
}
    return valorTotal;
}

public static double totalVendedores(String data) {
    DateTimeFormatter formatter;
    if (data.split("/").length == 2) {
        formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
    } else {
        formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    LocalDate localDate = LocalDate.parse(data, formatter);

    int dia = localDate.getDayOfMonth();
    int ultimoDiaDoMes = localDate.lengthOfMonth();
    if (!(dia == ultimoDiaDoMes))
        return 0.0;

    double valorTotal = 0.0;

    for (Map.Entry<String, Venda> entry : vendas.entrySet()) {
        String id = entry.getKey();
        Venda venda = entry.getValue();

        // Check if the employee is salaried
        if (empregados.get(id).getTipo().equalsIgnoreCase("assalariado")) {
            double salarioDoEmpregado = Double.parseDouble(empregados.get(id).getSalario().replace(",", "."));
            valorTotal += salarioDoEmpregado;
        } else if (empregados.get(id).getTipo().equalsIgnoreCase("comissionado")) {
            // Logic for commissioned employees if needed
        }
    }
    return valorTotal;
}

public static String totalFolha(String data) {
    double valorTotal = totalHoristas(data) + totalVendedores(data);
    DecimalFormat formato = new DecimalFormat("#,##0.00");
    return formato.format(valorTotal);
}*/
    public static String totalFolha(String data) {
        double totalSalarioBruto = 0;
        double totalDescontos = 0;
        double totalSalarioLiquido = 0;
       Double total = 0.00;
         String date = convertStringToStringFolha.convertStringToStringFolha(data);
         String basePath = Paths.get("").toAbsolutePath().toString(); // Obtém o caminho absoluto do diretório atua
try (BufferedReader br = new BufferedReader(new FileReader(basePath + "/ok/folha-" + date + ".txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("TOTAL FOLHA")) {
                    total = extractTotal(line);
                    break;
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return convertDoubleToString.convertDoubleToString(total,2);
    }

    private static double extractTotal(String line) {
        // Extrai o valor total da linha, considerando o formato do exemplo
        String[] parts = line.split("\\s+");
        return Double.parseDouble(parts[parts.length - 1].replace(".", ","));
    }
    public static void criarAgenda(String agenda) {
        
    }
    
}
