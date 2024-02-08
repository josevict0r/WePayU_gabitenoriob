package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
import br.ufal.ic.p2.wepayu.models.empregados.Ponto;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class SistemaController {

    static LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<String, Empregado>();
    static ArrayList<Empregado> empregadosPersistencia = new ArrayList<Empregado>();
    static Map<String, List<Ponto>> pontosDosEmpregados = new HashMap<>();
    static Map<String, List<Ponto>> pontosDosEmpregadosPersistencia = new HashMap<>();

    public static void encerrarSistema() throws FileNotFoundException {
        XMLEncoder encoderEmp = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregados.xml")));

        encoderEmp.writeObject(empregadosPersistencia);

        encoderEmp.close();

        /*XMLEncoder encoderPonto = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("pontos.xml")));

        encoderPonto.writeObject(pontosDosEmpregadosPersistencia);

        encoderPonto.close();*/
    }

    public static void zerarSistema() throws FileNotFoundException {
        empregados.clear();
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregados.xml")));
        for(Empregado i : empregadosPersistencia) {
            encoder.remove(i);
        }
        empregadosPersistencia.clear();

       /* pontosDosEmpregados.clear();
        XMLEncoder encoderPontos = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("pontos.xml")));
        for (Map.Entry<String, List<Ponto>> entry : pontosDosEmpregadosPersistencia.entrySet()) {
            for (Ponto ponto : entry.getValue()) {
                encoderPontos.remove(ponto);
            }
        }
        encoderPontos.close();
        pontosDosEmpregadosPersistencia.clear();*/

    }

    @SuppressWarnings("unchecked")
    public static void iniciarSistema() throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("empregados.xml")));
        empregadosPersistencia = (ArrayList<Empregado>) decoder.readObject();

        for (Empregado i : empregadosPersistencia) {
            empregados.put(i.getId(), i);

        }
        decoder.close();


       /* XMLDecoder decoderPontos = new XMLDecoder(new BufferedInputStream(new FileInputStream("pontos.xml")));
        Map<String, List<Ponto>> pontosPersistidos = new HashMap<>();

        while (true) {
            try {
                Map<String, String> pontoData = (Map<String, String>) decoderPontos.readObject();
                String emp = pontoData.get("emp");
                String data = pontoData.get("data");
                String horas = pontoData.get("horas");

                // Verifica se já existe uma lista de pontos associada a esse empregado
                if (!pontosPersistidos.containsKey(emp)) {
                    pontosPersistidos.put(emp, new ArrayList<>());
                }

                // Cria um novo ponto com os dados lidos e adiciona à lista de pontos do empregado
                Ponto ponto = new Ponto( data, horas);
                pontosPersistidos.get(emp).add(ponto);
            } catch (Exception e) {
                // Se não houver mais objetos para ler, o loop termina
                break;
            }
        }

        decoderPontos.close();

// Agora, você pode atribuir pontosPersistidos ao seu mapa pontosDosEmpregadosPersistencia
        pontosDosEmpregadosPersistencia = pontosPersistidos;*/


    }

}

