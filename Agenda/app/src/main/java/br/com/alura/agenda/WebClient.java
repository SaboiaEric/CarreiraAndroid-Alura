package br.com.alura.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by erics on 12/07/2017.
 */

public class WebClient {
    public String Post(String json){

        try {

            URL url = new URL("https://www.caelum.com.br/mobile"); // busca url que será usada para conexão
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // abre conexão com a url

            connection.setRequestProperty("Content-type","application/json"); //Informa o servidor que ele enviará um json
            connection.setRequestProperty("Accept","application/json"); //Informa o servidor que ele quer receber um json


            // Informa que vai ser realizado um comando POST no servidor
            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream output = new PrintStream(connection.getOutputStream()); //Abre o formato de envio que será realizado para o servidor web
            output.println(json); // Envia o arquivo json

            connection.connect(); // Realiza efetivamente a conexão com o servidor web

            Scanner scanner = new Scanner(connection.getInputStream()); //Abre a conexão para coletar resposta do servidor web
            String resposta = scanner.next(); //Coleta a resposta do servidor e insere na variavel resposta
            return resposta; //restorna a resposta para o método que chamou o POST


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
