package com.api.chamados.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class ListaDadosSerializer extends JsonSerializer<List<String>> {
    @Override
    public void serialize(List<String> value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartArray();
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            List<Object> objects = objectMapper.readValue(value.get(0), new TypeReference<List<Object>>() {
            });

            for (Object object: objects){
                jsonGenerator.writeObject(object);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        jsonGenerator.writeEndArray();
    }
}
