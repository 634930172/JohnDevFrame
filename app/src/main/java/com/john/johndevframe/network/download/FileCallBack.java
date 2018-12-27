package com.john.johndevframe.network.download;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/15 0015 16:02
 * <p/>
 * Description:文件回调类
 */

public abstract class FileCallBack<T> {
    private static final String TAG="FileCallBack";
    private String destFileDir;
    private String destFileName;


    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long progress);

    public abstract void onStart();

    public abstract void onCompleted();

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            //onCompleted();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }




}



