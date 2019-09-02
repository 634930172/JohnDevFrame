package com.john.johndevframe.utils;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2019/8/29 16:32
 * <p/>
 * Description:将assets文件下的资源转成文件的形式
 */
public class AssetUtils {

    private static final String TAG = "AssetUtils";

    public static File getFile(String filename) {
        return FliePathUtil.getIndividualCacheDirectory(ContextUtil.getContext(), "uploadFile", filename);
    }

    /**
     * 将Asset文件的图片转到SD卡文件存储
     */
    public static void saveFileFromAsset(String filename) {
        File file = FliePathUtil.getIndividualCacheDirectory(ContextUtil.getContext(), "uploadFile", filename);
        if (file == null) {
            Log.d(TAG, "file is not exit or null");
            return;
        }
        if (file.length() != 0) {
            Log.d(TAG, "saveFileFromAsset: file data has push");
            return;
        }
        InputStream in = null;
        OutputStream os = null;
        try {
            in = ContextUtil.getContext().getAssets().open(filename);
            os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[1024];
            int len = buffer.length;
            while ((bytesRead = in.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


}
