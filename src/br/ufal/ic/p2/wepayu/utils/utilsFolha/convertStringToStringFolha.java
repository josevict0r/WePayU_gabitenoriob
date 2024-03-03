package br.ufal.ic.p2.wepayu.utils.utilsFolha;



public class convertStringToStringFolha {
   

    public static String convertStringToStringFolha(String data) {
        // a data vem 7/1/2005
        //tem q voltar 2005-01-07
        
        //7-1-2005
        String[] partes = data.split("/");
String[] finalArray = new String[3];
int cont = 2;

for (String string : partes) {
    finalArray[cont] = string;
    cont--;
}

if (finalArray[1].length() == 1) {
        finalArray[1] = "0" + finalArray[1];
    }

    if (finalArray[0].length() == 1) {
        finalArray[0] = "0" + finalArray[0];
    }

    String result = finalArray[2] + "-" + finalArray[1] + "-" + finalArray[0];
    return result;
    }
}
