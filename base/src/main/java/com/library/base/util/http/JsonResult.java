package com.library.base.util.http;

import com.library.base.util.json.JsonUtil;

import java.util.List;

public class JsonResult<T> {

    public int code;
    public String message;
    public T data;
    public boolean success;

    public JsonResult() {
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 由字符串构造
     *
     * @param value
     * @return
     */
    public static JsonResult fromString(String value) {
        if (value == null) {
            return null;
        }
        JsonResult result = JsonUtil.fromJsonObject(value, JsonResult.class);
        return result;
    }

    public boolean isOk() {
        if (code == 200) return true;
        return false;
    }

    public String getData() {
        return String.valueOf(data);
    }

    public String toString() {
        return JsonUtil.toJson(this);
    }

}
