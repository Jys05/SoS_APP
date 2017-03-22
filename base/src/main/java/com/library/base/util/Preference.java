package com.library.base.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.library.base.util.json.JsonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Summary ：负责保存不会丢失数据
 * Created by zhangdm on 2015/12/14.
 */
public class Preference {

    // share数据保存

    private final String SHARED_PREFERENCE_NAME = "com.hzz.developbase";
    private static Preference catche;
    private SharedPreferences spf;

    public static Preference instance(Context context) {
        if (catche == null) {
            catche = new Preference(context);
        }
        return catche;
    }

    public static Preference getInstance() {
        return catche;
    }

    public Preference(Context context) {
        spf = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        spf.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return spf.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return spf.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        spf.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return spf.getString(key, "");
    }

    public void putInt(String key, int value) {
        spf.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return spf.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return spf.getInt(key, defaultValue);
    }

    public void putLong(String key, long value) {
        spf.edit().putLong(key, value).commit();
    }

    public long getLong(String key) {
        return spf.getLong(key, 0);
    }

    public long getLong(String key, long def) {
        return spf.getLong(key, def);
    }

    public void putHashSet(String key, HashSet hashSet) {
        spf.edit().putStringSet(key, hashSet).commit();
    }

    public Set<String> getHashSet(String key) {
        return spf.getStringSet(key, new HashSet<String>());
    }

    /**
     * 清空所有数据
     */
    public void clearData() {
        spf.edit().clear().commit();
    }

    /**
     * 清除指定某个数据的值
     *
     * @param key
     */
    public void remove(String key) {
        spf.edit().remove(key).commit();
    }

    public void commit() {
        spf.edit().commit();
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if (StringUtil.isEmpty(getString(key))) {
            return null;
        }
        return JsonUtil.fromJsonObject(getString(key), clazz);
    }

    public <T> List<T> getObjects(String key, Class<T> clazz) {
        if (StringUtil.isEmpty(getString(key))) {
            return null;
        }
        return JsonUtil.fromJsonArray(getString(key), clazz);
    }

    public void putObject(String key, Object object) {
        if (null == object) {
            putString(key, "");
        } else {
            String str = JsonUtil.toJson(object);
            putString(key, str);
        }
    }


}
