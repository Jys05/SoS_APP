package com.library.base.util.http;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/23
 * @description 网络请求类
 */
public class Http {

    public static String access = "11";
    public static String authorization = "ae0395611be5438eaf2ffd10720682a7";
    public static String user = "";
    //正式服务器地址
//    public static String baseUrl = "http://120.76.242.84:7777/huolieying/";
    //测试用的服务器
    public static String baseUrl = "http://zhsandu.imwork.net/huolieying/";
//    public static String baseUrl = "http://192.168.1.114:8080/huolieying/";

    public static Http http;

    private Retrofit mRetrofit;

    public Http(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RequestLogInterceptor())
                .addInterceptor(new RequestHeaderInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static boolean isLogin() {
        return true;
    }

    public static void initHttp(Context context) {
        http = new Http(context);
    }

    public static <T> T createApi(Class<T> tClass) {
        if (http != null) {
            return http.mRetrofit.create(tClass);
        } else {
            return null;
        }
    }

}
