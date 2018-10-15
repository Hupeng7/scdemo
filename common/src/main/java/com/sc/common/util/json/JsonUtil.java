package com.sc.common.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;
import java.util.Map;

/**
 * @author hp
 * @version 1.0
 * @description: json转换工具类
 * @date: 15:21 2018/10/15 0015
 */
public class JsonUtil {
    private static final SerializeConfig SERIALIZECONFIG;

    static {
        SERIALIZECONFIG = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        SERIALIZECONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        //使用和 json-lib 兼容的日期输出格式
        SERIALIZECONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    private static final SerializerFeature[] FEATURES = {
            // 输出空字段
            SerializerFeature.WriteMapNullValue,
            //list如果为null,输出为[],而不是null
            SerializerFeature.WriteNullListAsEmpty,
            //数字字段如果为null,输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            //Boolean字段如果为null,输出为false,而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            //字符串类型如果为null，输出为"",而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, SERIALIZECONFIG, FEATURES);
    }

    public static Object toJSON(Object object) {
        return JSON.toJSON(object);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, SERIALIZECONFIG);
    }

    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将javabean转化为序列化的json字符串
     *
     * @param keyValue
     * @return
     */
    public static Object pojoToJson(KeyValue keyValue) {
        String textJson = JSON.toJSONString(keyValue);
        Object objectJson = JSON.parse(textJson);
        return objectJson;
    }

    /**
     * 将String 转化为序列化的json字符串
     *
     * @param text
     * @return
     */
    public static Object stringToJson(String text) {
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    /**
     * json 字符串转化为map
     *
     * @param s
     * @return
     */
    public static Map jsonToMap(String s) {
        Map map = JSONObject.parseObject(s);
        return map;
    }

    /**
     * 将Map转化为String
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        String string = JSONObject.toJSONString(map);
        return string;
    }


}
