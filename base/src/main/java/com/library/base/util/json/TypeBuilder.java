package com.library.base.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/26
 * @description 生成泛型类
 */
public class TypeBuilder implements ParameterizedType {
    private final Class raw;
    private final Type[] args;

    public TypeBuilder(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
