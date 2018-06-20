package com.john.johndevframe.network.download;

/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/15 0015 16:01
 * <p/>
 * Description:下载进度中间类
 */

public class FileLoadEvent {

    private static FileLoadEvent INSTANCE = new FileLoadEvent();

    private FileLoadEvent() {
    }

    public static FileLoadEvent getInstance() {
        return INSTANCE;
    }


    private long total;
    private long bytesLoaded;

    long getBytesLoaded() {
        return bytesLoaded;
    }

   public long getTotal() {
        return total;
    }

    void setTotalProgress(long total) {
        this.total = total;
    }

    FileLoadEvent setProgress(long bytesLoaded) {
        this.bytesLoaded = bytesLoaded;
        return this;
    }

}
