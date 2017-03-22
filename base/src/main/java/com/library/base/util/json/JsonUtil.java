package com.library.base.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.library.base.util.StringUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description 处理JSON数据的工具类
 */
public class JsonUtil {
    private static Gson gson = null;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(JsonObject.class, new JsonDeserializer<Object>() {

            @Override
            public Object deserialize(JsonElement jsonElement, Type type,
                                      JsonDeserializationContext jsonDeserializationContext)
                    throws JsonParseException {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                return jsonObject;
            }
        });
        builder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

            @Override
            public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == src.longValue())
                    return new JsonPrimitive(src.longValue());
                return new JsonPrimitive(src);
            }
        });
        gson = builder.disableHtmlEscaping().create();
    }

    /**
     * JSON字符串转成对象
     *
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJsonObject(String content, Class<T> clazz) {
        if (StringUtil.isEmpty(content)) {
            return null;
        }
        Type type = new TypeBuilder(clazz, new Class[]{clazz});
        return gson.fromJson(content, type);
    }

    /**
     * JSON字符串转成数组
     *
     * @param content
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonArray(String content, Class<T> clazz) {
        if (StringUtil.isEmpty(content)) {
            return null;
        }
        // 生成List<T> 中的 List<T>
        Type listType = new TypeBuilder(List.class, new Class[]{clazz});
        return gson.fromJson(content, listType);
    }

    /**
     * 将对象转换成字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return gson.toJson(object);
    }

    /**
     * 将对象转换成map对象
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> toMap(Object obj) {
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, Map.class);
    }

    public static <T> T fromJsonObject(Object obj, Class<T> clazz) {
        Type type = new TypeBuilder(clazz, new Class[]{clazz});
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, type);
    }

    public static <T> List<T> fromJsonArray(Object obj, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new TypeBuilder(List.class, new Class[]{clazz});
        JsonElement element = gson.toJsonTree(obj);
        return gson.fromJson(element, listType);
    }
}
