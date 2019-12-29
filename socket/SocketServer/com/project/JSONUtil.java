package com.project;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

class JSONUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    static String convertJavaToJson(Object object){
        String jsonResult = null;
        try {
            jsonResult = mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    // making generic class function
    static <T> T convertJsonToJava(String jsonString, Class<T> obj){
        T result=null;
        try {
            result = mapper.readValue(jsonString,obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
