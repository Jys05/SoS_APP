package com.library.base.util.http;

import com.library.base.util.LogUtil;
import com.library.base.util.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/23
 * @description 添加头文件的数据字段
 */

public class RequestHeaderInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        okhttp3.Request.Builder builder = request.newBuilder();
        builder.addHeader("access", Http.access);
        builder.addHeader("authorization", Http.authorization);
        if (!StringUtil.isEmpty(Http.user)) {
            builder.addHeader("user", Http.user);
            LogUtil.e("RequestHeaderInterceptor", Http.user);
        }
        return chain.proceed(builder.build());
    }
}