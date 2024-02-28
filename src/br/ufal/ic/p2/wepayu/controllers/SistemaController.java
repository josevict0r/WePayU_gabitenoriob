package br.ufal.ic.p2.wepayu.controllers;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.*;

import br.ufal.ic.p2.wepayu.models.Empregado;

public class SistemaController {
    public static LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<String, Empregado>();
    static ArrayList<Empregado> empregadosPersistencia = new ArrayList<Empregado>();
   
    private static Stack<HashMap<String, Empregado>> undo;
    private static Stack<HashMap<String, Empregado>> redo;

    public static void pushUndo(EmpregadoController e) throws Exception {

       /* if (!systemOn) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoPodeDarComandos();
            return;
        }*/

        HashMap<String, Empregado> novaHash = new HashMap<>();

        if (undo == null)
            undo = new Stack<>();

        undo.push(novaHash);
    }

    //apenas a lista!
    public static void pushUndo(HashMap<String, Empregado> e) throws Exception {

        /*if (!systemOn) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoPodeDarComandos();
            return;
        }*/

        if (undo == null)
            undo = new Stack<>();

        undo.push(e);
    }

    public static HashMap<String, Empregado> popUndo() throws Exception {
        /*if (!systemOn) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoPodeDarComandos();
            return null;
        }

        if (undo.empty()) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoHaComandoDesfazer();
            return  null;
        }*/

        HashMap<String, Empregado> e = undo.peek();

        if (undo.size() > 1)  SistemaController.pushRedo(e);

        undo.pop();

        return e;
    }

    public static void pushRedo(HashMap<String, Empregado> e) throws Exception {
        /*if (!systemOn) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoPodeDarComandos();
            return;
        }*/

        if (redo == null) redo = new Stack<>();

        redo.push(e);
    }

    public static HashMap<String, Empregado> popRedo() throws Exception {
        /*if (!systemOn) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoPodeDarComandos();
            return null;
        }

        if (redo.empty()) {
            ExceptionSystem exceptionSystem = new ExceptionSystem();
            exceptionSystem.msgNaoHaComandoFazer();
            return  null;
        }*/

        HashMap<String, Empregado> e = redo.peek();

        SistemaController.pushUndo(e);
        redo.pop();

        return e;
    }

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
