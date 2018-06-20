package com.john.johndevframe.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/4 0004 10:42
 * <p/>
 * Description:Bitmap工具类
 */

public class BitmapUtil {

    /**
     * Bitmap转byte[]
     *
     * @param bitmap Bitmap
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        LogUtil.e("size：" + baos.toByteArray().length / 1024 + "kb");
        return baos.toByteArray();
    }


    /**
     * byte[]转Bitmap
     *
     * @param bytes bytes
     * @return
     */
    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 质量压缩方法 比较全的方法了
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        LogUtil.e("size:" + baos.toByteArray().length / 1024);
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            LogUtil.e("循环size：" + baos.toByteArray().length / 1024 + " options:" + options);
            if (options == 0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }


    /**
     * 将像素减半以读取资源文件
     *
     * @param context 上下文
     * @param resId   资源ID
     * @return bitmap
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeResource(context.getResources(), resId, opt);
    }


    /**
     * 通过uri获取图片并进行压缩
     * TODO  压缩回调后的uri方法
     *
     * @param uri
     */
    public static Bitmap compressBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input             = ac.getContentResolver().openInputStream(uri);
        LogUtil.e("原始图片大小： "+ input.available()/1024+"kb");
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        if(input!=null){
            input.close();
        }
        int originalWidth  = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//开启抖动 过度效果更好
        bitmapOptions.inPurgeable = true;// 与inInputShareable同时设置才会有效
        bitmapOptions.inInputShareable = true;//当系统内存不够时候图片自动被回收
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//图片占位数
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        if(input!=null){
            input.close();
        }
        LogUtil.e("像素压缩后大小： " + bitmapToBytes(bitmap).length / 1024 + " kb  缩放比例： "+be);

        return qualityCompressImage(bitmap);//再进行质量压缩 意义不大
        //        return bitmap;
    }


    /**
     * 通过File获取图片并进行压缩
     *
     * @param file 文件名
     */
    public static Bitmap getBitmapFormFile(Activity activity, File file) {
        Uri uri = Uri.fromFile(file);
        try {
            return compressBitmapFormUri(activity, uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按尺寸压缩图片  以路径的形式
     * BitmapFactory.Options的inJustDecodeBounds为true的时候，BitmapFactory.decodeByteArray返回null；为false的时候，
     * BitmapFactory.decodeByteArray返回加载的Bitmap。BitmapFactory.decodeFile以及BitmapFactory.decodeByteArray都是一样的。
     */
    public static Bitmap compressBitmapFromPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        newOpts.inDither = true;//开启抖动 过渡效果更平滑
        BitmapFactory.decodeFile(srcPath, newOpts);//第一次采样
        int originalWidth = newOpts.outWidth;
        int originalHeight = newOpts.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalHeight > originalWidth && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//该模式是默认8888
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        newOpts.inJustDecodeBounds = false;//开始第二次采样需要返回false，在decode的时候bitmap才不为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//第二次采样
        return compressImage(bitmap);//再进行质量压缩
    }


    /**
     * 按尺寸压缩图片  以输入流的形式
     * BitmapFactory.Options的inJustDecodeBounds为true的时候，BitmapFactory.decodeByteArray返回null；为false的时候，
     * BitmapFactory.decodeByteArray返回加载的Bitmap。BitmapFactory.decodeFile以及BitmapFactory.decodeByteArray都是一样的。
     */
    public static Bitmap compressBitmapFromStream(InputStream stream) throws IOException {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//该模式是默认8888
        newOpts.inDither = true;//开启抖动 过渡效果更平滑
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        BitmapFactory.decodeStream(stream, null, newOpts);//第一次采样
        newOpts.inJustDecodeBounds = false;//开始第二次采样需要返回false，在decode的时候bitmap才不为空
        stream.reset();
        int originalWidth = newOpts.outWidth;
        int originalHeight = newOpts.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalHeight > originalWidth && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//该模式是默认8888
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, newOpts);//第二次采样
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e("质量压缩前>>>" + bitmapToBytes(bitmap).length / 1024);

        //        return compressImage(bitmap);//再进行质量压缩

        return bitmap;
    }


    public static Bitmap compressBitmapStreaming(InputStream input) throws IOException {
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional 565 8888
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.reset();
        //        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalHeight > originalWidth && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional 565 8888
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return compressImage(bitmap);//再进行质量压缩
        //        return bitmap;
    }


    /**
     * 质量压缩方法 如果大于100,将图片质量压缩一半处理
     *
     * @param image
     * @return
     */
    public static Bitmap qualityCompressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options == 0) {
                break;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        LogUtil.e("质量压缩后" + baos.toByteArray().length / 1024 + "kb");
        return BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
    }

}
