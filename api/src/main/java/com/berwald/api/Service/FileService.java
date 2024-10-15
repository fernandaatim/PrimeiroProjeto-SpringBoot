package com.berwald.api.Service;

import com.berwald.api.Model.Consulta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private static final String FILE_PATH="historico_consultar.json";

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public void saveHistory(Consulta consulta){
        List <Consulta> consultas = loadHistory();
        if(consultas == null){
            consultas = new ArrayList<>();
        }
        consultas.add(consulta);
        System.out.println("Consultas a serem salvas: "+consultas);
        try(Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(consultas, writer);
        }catch(IOException e){
            System.out.println("ERRO: "+e.getMessage());
        }
    }

    public List<Consulta> loadHistory() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Consulta>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

}

//    public void saveDataToFile(String data) {
//Usando try-with-resources para garantir o fechamento do writer
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
//            writer.write(data);
//            System.out.println("Dados salvos com sucesso!!");
//        } catch (IOException e) {
//            System.out.println("Erro ao salvar dados: " + e.getMessage());
//        }
//    }
