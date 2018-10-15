package com.sc.common.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

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


}
