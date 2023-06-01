package com.west2_5.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json 相关格式化
 */
public class JsonUtils {

    // 定义jackson对象
    private static ObjectMapper MAPPER = new ObjectMapper();

    {
        MAPPER.setSerializationInclusion(Include.ALWAYS);
    }

    /**
     * 将对象转换成json字符串。
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode stringToJsonNode(String data) {
        try {
            JsonNode jsonNode = MAPPER.readTree(data);
            return jsonNode;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T objectToPojo(Object jsonData, Class<T> beanType) throws Exception {
        T t = MAPPER.readValue(MAPPER.writeValueAsString(jsonData), beanType);
        return t;
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) throws Exception {
        T t = MAPPER.readValue(jsonData, beanType);
        return t;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将object数据转换成pojo对象list
     */
    public static <T>List<T> objectToList(Object jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(MAPPER.writeValueAsString(jsonData), javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将JSON数据转换成Map
     */
    public static Map<String,Object> jsonToMap(String jsonData) {
        try {
            Map map = MAPPER.readValue(jsonData, Map.class);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
