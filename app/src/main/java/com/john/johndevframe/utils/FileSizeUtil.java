package com.john.johndevframe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/5/21 10:02
 * <p>
 * Description: 获取文件的大小
 */
@SuppressWarnings("unused")
public class FileSizeUtil {
    // 获取文件大小单位为B的double值
    public static final int SIZE_TYPE_B  = 1;
    // 获取文件大小单位为KB的double值
    public static final int SIZE_TYPE_KB = 2;
    // 获取文件大小单位为MB的double值
    public static final int SIZE_TYPE_MB = 3;
    // 获取文件大小单位为GB的double值
    public static final int SIZE_TYPE_GB = 4;

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath
     *         文件路径
     * @param sizeType
     *         获取大小的类型1为B、2为KB、3为MB、4为GB
     *
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file      = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取失败!");
        }
        return FormatFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath
     *         文件路径
     *
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file      = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取失败!");
        }
        return FormatFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            System.out.println("文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     */
    private static long getFileSizes(File file) throws Exception {
        long size    = 0;
        File files[] = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                size += getFileSizes(f);
            } else {
                size += getFileSize(f);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     */
    public static String FormatFileSize(long size) {
        DecimalFormat df        = new DecimalFormat("#.00");
        String wrongSize = "0B";
        String fileSizeString;
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "KB";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    private static double FormatFileSize(long size, int sizeType) {
        DecimalFormat df           = new DecimalFormat("#.00");
        double        fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) size));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) size / 1024));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) size / 1048576));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) size / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
