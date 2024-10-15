package com.berwald.api.Service;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiServiceMovies {
    public static void main(String [] args){
        System.out.println("Aloha! ");

        try{
            //Configurando o cliente HTTP com o proxy
            HttpClient client = HttpClient.newBuilder()
                    .proxy(ProxySelector.of(new InetSocketAddress("proxy.br.bosch.com",8080)))
                    .build();

            String requisitar;

            System.out.println("Digite o filme: ");
            requisitar = System.console().readLine();

            if(requisitar != null){
                String encodedTitle = URLEncoder.encode(requisitar, StandardCharsets.UTF_8);

                //Criando requisição HTPP
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.omdbapi.com/?t="+encodedTitle+"&apikey=ee37a021"))
                        .build();

                //Enviando a resposta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                //Retorno
                System.out.println(response.body());
            } else{
                System.out.println("Digite um valor válido.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
