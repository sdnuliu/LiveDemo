package com.example.liuteng.livedemo.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by liuteng on 2017/3/14.
 */

public class ResponseUtil {
    public <T> T transformDataToEntity(String data, Type type) throws Exception {
        try {
            Gson gson = new Gson();
            return gson.fromJson(data, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("The data parse failed.");
        }
    }
}
