package com.library.base.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/27
 * @description 生成二维码的工具类
 */

public class QRCodeUtil {

    private static QRCodeUtil instance = null;

    private QRCodeUtil() {
    }

    public static QRCodeUtil getInstance() {
        if (instance == null) {
            instance = new QRCodeUtil();
        }
        return instance;
    }

    /**
     * 根据字符串生成二维码图片
     */
    public Bitmap initQrCode(String content) {
        int QR_WIDTH = 200; // 图像宽度
        int QR_HEIGHT = 200; // 图像高度
        return initQrCode(content, QR_WIDTH, QR_HEIGHT);
    }

    /**
     * 根据字符串生成二维码图片
     */
    public Bitmap initQrCode(String content, int QR_WIDTH, int QR_HEIGHT) {
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT, hints);

            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }

            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
