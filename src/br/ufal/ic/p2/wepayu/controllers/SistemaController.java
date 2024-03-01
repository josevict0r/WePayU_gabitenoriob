package br.ufal.ic.p2.wepayu.controllers;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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

       try (BufferedOutputStream file = new BufferedOutputStream(
                new FileOutputStream("dados.xml"))) {
            XMLEncoder encoder = new XMLEncoder(file);
            empregados.forEach((id, empregado) -> {
                encoder.writeObject(empregado);
            });
            encoder.close();
        } catch (IOException e) {
            System.out.println("Arquivo nao encontrado");
        }

    }

    public static void zerarSistema() throws FileNotFoundException {
        empregados.clear();
        try {
            Files.delete(Path.of("dados.xml"));
        }catch (Exception e){
            System.out.println("Arquivo nao existe");
        }


    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Empregado> iniciarSistema() throws FileNotFoundException {

        LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<>();

        try(BufferedInputStream file = new BufferedInputStream(
                new FileInputStream("dados.xml"))){
            XMLDecoder decoder = new XMLDecoder(file);
            while(true){
                try{
                    Empregado aux = (Empregado) decoder.readObject();
                    empregados.put(aux.getId(), aux);
                }catch (Exception e) {
                    break;
                }
            }
            decoder.close();
        }catch (IOException e) {
            System.out.println("Arquivo nao encontrado");
        }
        return empregados;


    }

    public static LinkedHashMap<String, Empregado> getEmpregados() {
        return empregados;
    }
}
