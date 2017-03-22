package com.library.base.util.http;

import com.library.base.util.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/23
 * @description 请求的返回类
 */
public abstract class CallBack<T> implements Callback<JsonResult<T>> {
    @Override
    public void onResponse(Call<JsonResult<T>> call, Response<JsonResult<T>> response) {
        JsonResult<T> result = response.body();
        try {
            if (result == null) {
                fail(-1, "网络请求失败");
            } else {
                if (result.isOk()) {
                    success(result.data);
                } else {
                    fail(result.code, result.message);
                }
            }
        } catch (Exception e) {
            LogUtil.e("CallBack", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<JsonResult<T>> call, Throwable t) {
        try {
            fail(-1, "网络请求失败");
            t.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void success(T response);

    public abstract void fail(int code, String message);
}
