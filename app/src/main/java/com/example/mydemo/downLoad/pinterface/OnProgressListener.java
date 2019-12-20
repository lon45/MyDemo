package com.example.mydemo.DownLoad.pinterface;

import com.example.mydemo.DownLoad.bean.DownLoadInfo;

//下载进度接口
public interface OnProgressListener {
    /**
     * 准备下载
     */
    void onPrepare(DownLoadInfo bean);

    /**
     * 开始下载
     */
    void onStart(DownLoadInfo bean);

    /**
     * 下载中
     */
    void onProgress(DownLoadInfo bean);

    /**
     * 暂停
     */
    void onStop(DownLoadInfo bean);

    /**
     * 下载完成
     */
    void onFinish(DownLoadInfo bean);

    /**
     * 下载失败
     */
    void onError(DownLoadInfo bean);

    /**
     * 删除成功
     */
    void onDelete(DownLoadInfo bean);
}