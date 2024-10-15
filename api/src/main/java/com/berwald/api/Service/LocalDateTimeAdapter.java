package com.berwald.api.Service;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonSerializer <LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context){
        return new JsonPrimitive(formatter.format(src));
    }
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}

/*Overide é uma anotação em java que indica que um método está sobrescevendo um método da hiperclasse.
TypeAdapter é uma classe para criar adaptadores personalizados em Gson
JsonReader e o JsonWriter são usados para ler e escrever dados JSON
A classe LocalDateTimeAdapter extends TypeAdapter define uma nova classe LocalDateTimeAdapter que estende TypeAdapter para trabalhar com objetos LocalDateTime
A classe private final DateTimeFormatter cria um DateTimeFormatter que será usado para formatar e analisar LocalDateTime no formato ISO padrão
 */