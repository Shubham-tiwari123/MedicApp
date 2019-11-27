package com.medical.client.utils;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String convertJavaToJson(Object object){
        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    // making generic class function
    public static <T> T convertJsonToJava(String jsonString, Class<T> obj){
        T result=null;
        try {
            result = mapper.readValue(jsonString,obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
