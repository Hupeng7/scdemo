package com.sc.common.util.req;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author hp
 * @time 2018/10/15.
 */
public class RequestHandler {
    public static String getParams(HttpServletRequest request, String key) {
        try {
            ServletInputStream servletInputStream = request.getInputStream();
            String body = StreamUtils.copyToString(servletInputStream, Charset.forName("utf-8"));
            JSONObject jsonObject = JSON.parseObject(body);
            return jsonObject.getString(key);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
