package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.models.Ponto;
import br.ufal.ic.p2.wepayu.models.Servico;
import br.ufal.ic.p2.wepayu.models.Venda;
import br.ufal.ic.p2.wepayu.models.empregados.Empregado;
import java.io.FileNotFoundException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

public class SistemaController {
    public static LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<String, Empregado>();
    static ArrayList<Empregado> empregadosPersistencia = new ArrayList<Empregado>();
    public static Map<String, List<Ponto>> pontosDosEmpregados = new HashMap<>();
    static Map<String, List<Ponto>> pontosDosEmpregadosPersistencia = new HashMap<>();

    static Map<String, List<Venda>> vendasDosEmpregados = new HashMap<>();
    static Map<String, List<Venda>> vendasDosEmpregadosPersistencia = new HashMap<>();

    static Map<String, List<Servico>> servicoDosEmpregados = new HashMap<>();
    static Map<String, List<Servico>> servicoDosEmpregadosPersistencia = new HashMap<>();


    public static void encerrarSistema() throws FileNotFoundException {

        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregado.xml")));
        encoder.writeObject(empregadosPersistencia);
        encoder.close();

    }

    public static void zerarSistema() throws FileNotFoundException {
        empregados.clear();
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregado.xml")));
        for(Empregado i : empregadosPersistencia) {
            encoder.remove(i);
        }
        empregadosPersistencia.clear();


    }

    @SuppressWarnings("unchecked")
    public static void iniciarSistema() throws FileNotFoundException {

        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("empregado.xml")));
        empregadosPersistencia = (ArrayList<Empregado>) decoder.readObject();
        for(Empregado i : empregadosPersistencia) {
            empregados.put(i.getId(), i);

        }

        decoder.close();




    }
}
