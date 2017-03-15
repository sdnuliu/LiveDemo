package com.example.liuteng.livedemo.model;

/**
 * Created by liuteng on 2017/3/15.
 */

public interface ResListener<T> {
    void onSuccess(T t);

    void failed(String message);
}
