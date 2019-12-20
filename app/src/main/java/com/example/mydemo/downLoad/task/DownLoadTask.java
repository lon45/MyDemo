package com.example.mydemo.downLoad.task;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import com.example.mydemo.downLoad.bean.DownLoadInfo;
import com.example.mydemo.downLoad.Aes.FileEnDecryptManager;
import com.example.mydemo.downLoad.DownLoaderManger;
import com.example.mydemo.downLoad.db.DownLoadDbHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 下载文件线程
 * 从服务器获取需要下载的文件大小
 */
public class DownLoadTask implements Runnable {
    private DownLoadInfo info;
    private SQLiteDatabase db;
    private DownLoadDbHelper helper;//数据库帮助类
    private Handler listener;//进度回调监听
    private Map<String, DownLoadTask> mTaskMap;

    public DownLoadTask(DownLoadInfo info, DownLoadDbHelper helper, Handler listener, Map<String, DownLoadTask> mTaskMap) {
        this.info = info;
        this.helper = helper;
        this.db = helper.getReadableDatabase();
        this.listener = listener;
//        info.setDownLoad_state(DownLoaderManger.STATE_DOWNLOADING);
        this.mTaskMap = mTaskMap;
    }

    public DownLoadInfo getInfo() {
        return info;
    }

    public void setInfo(DownLoadInfo info) {
        this.info = info;
    }

    @Override
    public void run() {
        // 等待中就暂停了
        if (getInfo().getDownLoad_state() == DownLoaderManger.STATE_PAUSED) {
//            getInfo().setDownLoad_state(DownLoaderManger.STATE_PAUSED);
//            helper.updateDownLoadData(db, getInfo());
//            notifyDownloadStateChanged(getInfo());
            if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                mTaskMap.remove(getInfo().getLesson_url());
            }
//            db.close();
            return;
        } else if (getInfo().getDownLoad_state() == DownLoaderManger.STATE_DELETE) {// 等待中就删除直接回调界面，然后直接返回
//            getInfo().setDownLoad_state(DownLoaderManger.STATE_DELETE);
//            helper.updateDownLoadData(db, getInfo());
//            notifyDownloadStateChanged(getInfo());
            if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                mTaskMap.remove(getInfo().getLesson_url());
            }
//            db.close();
            return;
        }

        getInfo().setDownLoad_state(DownLoaderManger.STATE_START);// 开始下载
        helper.updateDownLoadData(db, getInfo());
        notifyDownloadStateChanged(getInfo());

        if (getLength()) {
        HttpURLConnection connection = null;
        RandomAccessFile rwd = null;
        // 当前下载的进度
        long compeleteSize = 0;
        File dir = new File(DownLoaderManger.FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
//            Utils.INSTANCE.log("111","info.getLesson_save_path() = "+info.getLesson_save_path());
        File file = new File(info.getLesson_save_path());// 获取下载文件
//            Utils.INSTANCE.log("111","file = "+file.getPath());
        if (!file.exists()) {
            // 如果文件不存在
            info.setFinished(0);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 如果存在就拿当前文件的长度，设置当前下载长度
            // (这样的好处就是不用每次在下载文件的时候都需要写入数据库才能记录当前下载的长度，一直操作数据库是很费资源的)
            compeleteSize = file.length();
            //下载完成
            if (compeleteSize == info.getLesson_size()) {
                info.setDownLoad_state(DownLoaderManger.STATE_DOWNLOADED);
                helper.updateDownLoadData(db, info);
                if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                    mTaskMap.remove(getInfo().getLesson_url());
                }
                notifyDownloadStateChanged(info);
                return;
            }
        }

        try {
            URL url = new URL(info.getLesson_url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            //从上次下载完成的地方下载
            //设置下载位置(从服务器上取要下载文件的某一段)
            connection.setRequestProperty("Range", "bytes=" + compeleteSize + "-");//设置下载范围// + info.getLesson_size()
            //设置文件写入位置
//            File file = new File( info.getFileName());
            rwd = new RandomAccessFile(file, "rwd");
            //从文件的某一位置开始写入
            rwd.seek(compeleteSize);
//            compeleteSize += info.getFinished();
//            Utils.INSTANCE.log("111","1111111 = 1"+connection.getResponseCode());
            if (connection.getResponseCode() == 206 || connection.getResponseCode() == 200) {//文件部分下载，返回码为206
                if(getInfo().getDownLoad_state() == DownLoaderManger.STATE_START){//判断是开始状态  不然由于联网状态，暂停会被打断
                InputStream is = connection.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                int len;
                info.setDownLoad_state(DownLoaderManger.STATE_DOWNLOADING);
                helper.updateDownLoadData(db, info);
                notifyDownloadStateChanged(getInfo());
                int count = 0; //计数，每5次刷新一次界面，不然会很卡
                while ((len = is.read(buffer)) != -1) {
                    if (getInfo().getDownLoad_state() == DownLoaderManger.STATE_PAUSED) {
//                        getInfo().setDownLoad_state(DownLoaderManger.STATE_PAUSED);
//                        helper.updateDownLoadData(db, info);
//                        notifyDownloadStateChanged(getInfo());
                        if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                            mTaskMap.remove(getInfo().getLesson_url());
                        }
                        return;
                    } else if (getInfo().getDownLoad_state() == DownLoaderManger.STATE_DELETE) {// 等待中就删除直接回调界面，然后直接返回
//                        getInfo().setDownLoad_state(DownLoaderManger.STATE_DELETE);
//                        helper.updateDownLoadData(db, info);
//                        notifyDownloadStateChanged(getInfo());
                        if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                            mTaskMap.remove(getInfo().getLesson_url());
                        }
                        return;
                    }
                    DownLoaderManger.down_bean = info;

                    //写入文件
                    rwd.write(buffer, 0, len);
                    compeleteSize += len;
                    //更新界面显示
                    info.setFinished(compeleteSize);
                    helper.updateDownLoadData(db, info);
                    count ++;
                    if(count == 5){
                        count = 0;
                        notifyDownloadStateChanged(info);
                    }
                }
                //下载完成
                if (info.getLesson_size() == info.getFinished()) {
                    info.setDownLoad_state(DownLoaderManger.STATE_DOWNLOADED);
                    helper.updateDownLoadData(db, info);
                    if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                        mTaskMap.remove(getInfo().getLesson_url());
                    }
//                    DownLoaderManger.down_bean = null;//这个对象是用于下载中程序退出，保存状态的，下载完成不用保存

//                    helper.updateDownLoadData(db, info);
                    notifyDownloadStateChanged(info);

//                    db.close();
                } else {
                    info.setDownLoad_state(DownLoaderManger.STATE_ERROR);
                    info.setFinished(0); // 错误状态需要删除文件
                    helper.updateDownLoadData(db, info);
                    if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                        mTaskMap.remove(getInfo().getLesson_url());
                    }
                    notifyDownloadStateChanged(info);
                    file.delete();
//                    db.close();
                }
             }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (rwd != null) {
                    rwd.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        } else {//文件长度为0 提示下载错误
            info.setDownLoad_state(DownLoaderManger.STATE_ERROR);
            info.setFinished(0); // 错误状态需要删除文件
            helper.updateDownLoadData(db, info);
            if (mTaskMap.containsKey(getInfo().getLesson_url())) {
                mTaskMap.remove(getInfo().getLesson_url());
            }
            notifyDownloadStateChanged(info);
            File file = new File(info.getLesson_save_path());// 获取下载文件
            if (file.exists()) {
                file.delete();
            }
        }
    }


    /**
     * 首先开启一个线程去获取要下载文件的大小（长度）
     */
    private boolean getLength() {
        HttpURLConnection connection = null;
        try {
            //连接网络
            URL url = new URL(info.getLesson_url());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            int length = -1;
            if (connection.getResponseCode() == 200) {//网络连接成功
                //获得文件长度
                length = connection.getContentLength();
            }
            if (length <= 0) {
                //连接失败
                return false;
            }
            //创建文件保存路径
            File dir = new File(DownLoaderManger.FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            info.setLesson_size(length);
            helper.updateDownLoadData(db,info);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //释放资源
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 当下载状态发送改变的时候调用
     */
    private synchronized void notifyDownloadStateChanged(DownLoadInfo bean) {
//        Utils.INSTANCE.log("111","notifyDownloadStateChanged");
        if (listener != null) {

            if(bean.getDownLoad_state() == DownLoaderManger.STATE_DOWNLOADED){//下载完成的文件需要加密
                File file = new File(info.getLesson_save_path());
                if(file.exists()){
                    encrypt(bean,file.getPath());
                }

            } else {
                Message message = listener.obtainMessage();
                message.obj = bean;
                listener.sendMessage(message);
            }


        }

    }
    //视频加密
    public void encrypt(final DownLoadInfo bean, final String FILE_PATH) {
        if (!FileEnDecryptManager.getInstance().isEncrypt(FILE_PATH)) {
            new Thread() {
                public void run() {
//                    Utils.INSTANCE.log("111","encrypt");
                    FileEnDecryptManager.getInstance().encryptFile(FILE_PATH,bean.getLesson_type().equalsIgnoreCase("video") ? FileEnDecryptManager.VideoFile:FileEnDecryptManager.AudioFile);
                    Message message = listener.obtainMessage();
                    message.obj = bean;
                    listener.sendMessage(message);
                }
            }.start();
        }
    }

}
