package com.library.base.util.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.library.base.util.ClippingUtil;
import com.library.base.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author zhangdeming
 * @date 创建时间 2016/11/25
 * @description 请求数据生成类
 */

public class RequestBodyFactory {

    public RequestBody create(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public RequestBody create(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public RequestBody create(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    public RequestBody create(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

    public RequestBody createPicture(String path) {
        return RequestBody.create(MediaType.parse("image/*")
                , compress(path));
    }

    public RequestBody createPicture(byte[] picData) {
        return RequestBody.create(MediaType.parse("image/*")
                , picData);
    }

    private byte[] compress(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


    private byte[] compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        return baos.toByteArray();
    }

}
