package br.ufal.ic.p2.wepayu.xmls;

import br.ufal.ic.p2.wepayu.models.empregados.Empregado;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XmlGenerator {

    public static void gerarXML(List<Empregado> empregados, String caminhoArquivo) {
        try (XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(caminhoArquivo)))) {
            xmlEncoder.writeObject(empregados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
