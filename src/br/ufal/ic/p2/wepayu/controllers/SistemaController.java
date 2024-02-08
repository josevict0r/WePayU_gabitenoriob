package br.ufal.ic.p2.wepayu.controllers;

import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SistemaController {

    static LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<String, Empregado>();
    static ArrayList<Empregado> empregadosPersistencia = new ArrayList<Empregado>();

    public static void encerrarSistema() throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregados.xml")));

        encoder.writeObject(empregadosPersistencia);

        encoder.close();
    }

    public static void zerarSistema() throws FileNotFoundException {
        empregados.clear();
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("empregados.xml")));
        for(Empregado i : empregadosPersistencia) {
            encoder.remove(i);// nao testei
        }
        empregadosPersistencia.clear();
    }

    public static void iniciarSistema() throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("empregados.xml")));
        empregadosPersistencia = (ArrayList<Empregado>) decoder.readObject();

        for(Empregado i : empregadosPersistencia) {
            empregados.put(i.getId(), i);

        }

        decoder.close();
    }
}

