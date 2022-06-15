package com.example.demoserver;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonHelper {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if(objectMapper!=null){
            return objectMapper;
        }
        JsonFactory f = new JsonFactory().configure(JsonParser.Feature.ALLOW_COMMENTS,true);
        return objectMapper = new ObjectMapper(f);
    }
}
