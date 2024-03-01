package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.utils.convertDoubleToString;
import br.ufal.ic.p2.wepayu.utils.utilsFolha.ehSexta;
import br.ufal.ic.p2.wepayu.utils.utilsFolha.ehUltimoDiaMes;
import br.ufal.ic.p2.wepayu.utils.utilsFolha.getUltimaSexta;
import br.ufal.ic.p2.wepayu.controllers.*;

import javax.xml.stream.XMLStreamReader;

import java.io.*;
import java.util.*;

public class Folha {

    
    private String saida;


    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    
    public Double getTotalFolha(String data) throws Exception{
    Double total = 0d;
    LinkedHashMap<String, Empregado> empregados = SistemaController.getEmpregados();
    for(Map.Entry<String, Empregado> emp : empregados.entrySet()) {
        Empregado e = emp.getValue();
        switch (e.getTipo()) {
            case "horista" -> {
                // Checa se é sexta e pega a ultima sexta para calcular o salario dos horistas
                if (ehSexta.ehSexta(data)) {
                    String dataInicial = getUltimaSexta.getUltimaSexta(data);
                    total += ((Horista)e).getSalarioBruto(dataInicial, data);
                }
            }
            case "assalariado" -> {
                // Checa se é o ultimo dia do mes para calcular o salario dos assalariados
                if (ehUltimoDiaMes.ehUltimoDiaMes(data)) {
                    total += Double.parseDouble(e.getSalario());
                }
            }
            case "comissionado" -> {
                // Checa se está no escopo de a cada 2 sextas, pega o ultimo dia de pagamento e
                // calcula o salario dos comissionados
                if (Utils.ehDiaDePagamentoComissionado(data)) {
                    String dataInicial = Utils.getUltimoPagamentoComissionado(data);
                    total += ((Comissionado) e).getSalarioBruto(dataInicial, data);
                }
            }
        }
    }
    return total;
}

    private void geraDadosHoristas(String data) throws Exception{
        // Lê arquivo horista.txt
        SortedSet<String> dadosEmpregados = new TreeSet<>();
        List<Double> somaTotal = Arrays.asList(0D, 0D, 0D, 0D, 0D);

        // Percorre os empregados horistas e soma o total de pagamento (caso seja dia de pagamento)
        LinkedHashMap<String, Empregado> empregados = SistemaController.getEmpregados();
        for(Map.Entry<String, Empregado> emp : empregados.entrySet()) {
            Empregado e = emp.getValue();
            if(e.getTipo().equals("horista"))
            {
                String dataInicial;
                if(!ehSexta.ehSexta(data)) break;
                else dataInicial = Utils.getUltimaSexta(data);
                Object[] dados = ((Horista) e).getDadosEmLinha(dataInicial, data);
                dadosEmpregados.add((String) dados[0]);
                somaTotal = Utils.somarListas(somaTotal,
                        (List<Double>) dados[1]);
            }
        }


        try{
            BufferedReader reader = new BufferedReader(new FileReader("modelotxt/horistas.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            // Escreve o header dos horistas
            String linha;
            while((linha = reader.readLine()) != null)
            {
                writer.write(linha);
                writer.newLine();
            }

            reader.close();

            // Escreve os dados de cada horista em ordem alfabetica
            for(String dado: dadosEmpregados){
                writer.write(dado);
                writer.newLine();
            }

            // Escreve o valor total de pagamento dos horistas
            String total = String.format("\n%-36s %5s %5s %13s %9s %15s\n", "TOTAL HORISTAS",
                    convertDoubleToString.convertDoubleToString(somaTotal.get(0), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(1), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(2), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(3), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(4), 2)
            );

            writer.write(total);
            writer.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    };

    private void geraDadosAssalariados(String data) throws Exception{
        // Lê arquivo assalariados.txt
        SortedSet<String> dadosEmpregados = new TreeSet<>();
        List<Double> somaTotal = Arrays.asList(0D, 0D, 0D);

        // Percorre os empregados assalarios e soma o total de pagamento (caso seja dia de pagamento)
        LinkedHashMap<String, Empregado> empregados = SistemaController.getEmpregados();
    for(Map.Entry<String, Empregado> emp : empregados.entrySet()) {
            Empregado e = emp.getValue();
            if(e.getTipo().equals("assalariado"))
            {
                String dataInicial;
                if(!Utils.ehUltimoDiaMes(data)) break;
                else dataInicial = Utils.getPrimeiroDiaMes(data);

                Object[] dados = ((Assalariado) e).getDadosEmLinha(dataInicial, data);
                dadosEmpregados.add((String) dados[0]);
                somaTotal = Utils.somarListas(somaTotal,
                        (List<Double>) dados[1]);
            }
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader("modelotxt/assalariados.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            // Escreve header dos assalariados
            String linha;
            while((linha = reader.readLine()) != null)
            {
                writer.write(linha);
                writer.newLine();
            }

            reader.close();

            // Escreve os dados de cada assalariado em ordem alfabetica
            for(String dado: dadosEmpregados){
                writer.write(dado);
                writer.newLine();
            }

            // Escreve o valor total de pagamento dos assalariados
            String total = String.format("\n%-48s %13s %9s %15s\n", "TOTAL ASSALARIADOS",
                    Utils.doubleToString(somaTotal.get(0), false),
                    Utils.doubleToString(somaTotal.get(1), false),
                    Utils.doubleToString(somaTotal.get(2), false)
            );

            writer.write(total);
            writer.close();

        }catch (IOException e)
        {
            System.out.println("Arquivos nao encontrados");
        }


    }

    private void geraDadosComissionados(String data) throws Exception{
        SortedSet<String> dadosEmpregados = new TreeSet<>();
        List<Double> somaTotal = Arrays.asList(0D,0D,0D,0D,0D,0D);

        LinkedHashMap<String, Empregado> empregados = SistemaController.getEmpregados();
    for(Map.Entry<String, Empregado> emp : empregados.entrySet()) {
            Empregado e = emp.getValue();
            if(e.getTipo().equals("comissionado"))
            {
                String dataInicial;
                if(!Utils.ehDiaDePagamentoComissionado(data)) break;
                else dataInicial = Utils.getUltimoPagamentoComissionado(data);
                Object[] dados = ((Comissionado) e).getDadosEmLinha(dataInicial, data);
                dadosEmpregados.add((String) dados[0]);
                somaTotal = Utils.somarListas(somaTotal,
                        (List<Double>) dados[1]);
            }
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader("modelotxt/comissionados.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida, true));

            String linha;
            while((linha = reader.readLine()) != null)
            {
                writer.write(linha);
                writer.newLine();
            }

            reader.close();

            for(String dado: dadosEmpregados){
                writer.write(dado);
                writer.newLine();
            }

            String total = String.format("\n%-21s %8s %8s %8s %13s %9s %15s\n", "TOTAL COMISSIONADOS",
                    convertDoubleToString.convertDoubleToString(somaTotal.get(0), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(1), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(2), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(3), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(4), 2),
                    convertDoubleToString.convertDoubleToString(somaTotal.get(5), 2)
            );

            writer.write(total);
            writer.write("\nTOTAL FOLHA: " +
                    convertDoubleToString.convertDoubleToString(getTotalFolha(data), 2));
            writer.close();

        }catch (IOException e)
        {
            System.out.println("Arquivos nao encontrados");
        }

    }


    public void geraFolha(String data, String saida) throws Exception{

        setSaida(saida);
        String dia = saida.substring(6, 16);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida));

            // Escreve o titulo da folha de pagamento
            writer.write("FOLHA DE PAGAMENTO DO DIA " + dia);
            writer.newLine();

            writer.close();
        }catch (IOException e)
        {
            System.out.println("Arquivo nao encontrado");
        }

        // Escreve os blocos de informação sequencialmente
        geraDadosHoristas(data);
        geraDadosAssalariados(data);
        geraDadosComissionados(data);

    }


}

