package com.example.mydemo.downLoad;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.example.mydemo.downLoad.bean.DownLoadInfo;
import com.example.mydemo.downLoad.db.DownLoadDbHelper;
import com.example.mydemo.downLoad.pinterface.Observerable;
import com.example.mydemo.downLoad.pinterface.OnProgressListener;
import com.example.mydemo.downLoad.task.DownLoadExecutor;
import com.example.mydemo.downLoad.task.DownLoadTask;
import com.example.mydemo.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.mydemo.util.ConstantKt.DIR_CHE;

public class DownLoaderManger implements Observerable {

    public static final int STATE_NONE = -1;
    /**
     * 开始下载
     */
    public static final int STATE_START = 0;
    /**
     * 等待中
     */
    public static final int STATE_WAITING = 1;
    /**
     * 下载中
     */
    public static final int STATE_DOWNLOADING = 2;
    /**
     * 暂停
     */
    public static final int STATE_PAUSED = 3;
    /**
     * 下载完毕
     */
    public static final int STATE_DOWNLOADED = 4;
    /**
     * 下载失败
     */
    public static final int STATE_ERROR = 5;
    /**
     * 删除下载成功
     */
    public static final int STATE_DELETE = 6;

    public static String FILE_PATH = DIR_CHE + "/" + Utils.INSTANCE.getUserId();//文件下载保存路径
    public final static String FILE_PATH_ROOT = DIR_CHE;//文件下载保存路径
    private DownLoadDbHelper helper;//数据库帮助类
    private SQLiteDatabase db;
    /**
     * 是否清理缓存
     */
    public static boolean isClearCache = true;

    public static boolean isClearAll = true;

    /**
     * 用于记录所有下载的任务，方便在取消下载时，通过id能找到该任务进行删除
     */
    private Map<String, DownLoadTask> mTaskMap = new ConcurrentHashMap<String, DownLoadTask>();

    public Map<String, DownLoadTask> getmTaskMap() {
        return mTaskMap;
    }

    public void setmTaskMap(Map<String, DownLoadTask> mTaskMap) {
        this.mTaskMap = mTaskMap;
    }

    /**
     * 全局记录当前正在下载的bean
     */
    public static DownLoadInfo down_bean;

    public static DownLoaderManger manger;

    private DownLoaderManger(DownLoadDbHelper helper) {
        this.helper = helper;
        db = helper.getReadableDatabase();
        //初始化的時候把文件路径先创建出来
        File dir = new File(DownLoaderManger.FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }

    /**
     * 单例模式
     *
     * @param helper 数据库帮助类
     * @return
     */
    public static synchronized DownLoaderManger getInstance(DownLoadDbHelper helper) {
        if (manger == null) {
            synchronized (DownLoaderManger.class) {
                if (manger == null) {
                    manger = new DownLoaderManger(helper);
                }
            }
        }
        return manger;
    }

    //    /**
//     * 开始下载任务（app 重新启动以后,把数据库中等待中的加入到线程池）
//     */
    public synchronized void start() {
        db = helper.getReadableDatabase();
        List<DownLoadInfo> list = helper.queryDataWaiting(db);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DownLoadTask task = new DownLoadTask(list.get(i), helper, handler, mTaskMap);// 创建一个下载任务，放入线程池
                // 线程放入map里面方便管理
                mTaskMap.put(list.get(i).getLesson_url(), task);
                DownLoadExecutor.execute(task);
            }
        }

    }
//
//    /**
//     * 停止下载任务
//     */
//    public void stop(String url) {
//        map.get(url).setStop(true);
//    }

//    /**
//     * 重新下载任务
//     */
//    public void restart(String url) {
//        stop(url);
//        try {
//            File file = new File(map.get(url).getFileName());
//            if (file.exists()) {
//                file.delete();
//            }
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        db = helper.getWritableDatabase();
//        helper.resetData(db, url);
//        start(url);
//    }

//    /**
//     * 获取当前任务状态
//     */
//    public boolean getCurrentState(String url) {
//        return map.get(url).isDownLoading();
//    }

//    /**
//     * 添加下载任务
//     *
//     * @param info 下载文件信息
//     */
//    public void addTask(DownLoadInfo info) {
//        //判断数据库是否已经存在这条下载信息
//        if (!helper.isExist(db, info)) {
//            db = helper.getWritableDatabase();
//            helper.insertData(db, info);
//            map.put(info.getAudio(), info);
//        } else {
//            //从数据库获取最新的下载信息
//            db = helper.getReadableDatabase();
//            DownLoadInfo o = helper.queryData(db, info.getAudio());
//            if (!map.containsKey(info.getAudio())) {
//                map.put(info.getAudio(), o);
//            }
//        }
//    }

    /**
     * 开启下载，需要传入一个DownAppBean对象
     */
    public synchronized void download(DownLoadInfo loadBean) {
//        Utils.INSTANCE.log("111","loadBean.getDownLoad_state() = "+loadBean.getDownLoad_state());
        if (loadBean != null && !helper.isExistData(db, loadBean)) {
            helper.insertDownLoadData(db, loadBean);
        }
        File dir = new File(DownLoaderManger.FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 先判断是否有这个下载信息
        DownLoadInfo bean = helper.queryDownLoadDataByLessonId(db, loadBean.getLesson_id(), loadBean.getLesson_type());

        if (bean == null) {// 如果没有，则根据loadBean创建一个新的下载信息
            bean = loadBean;
            helper.insertDownLoadData(db, bean);
        }
        if (bean.getDownLoad_state() == STATE_DOWNLOADED) {
            File file = new File(bean.getLesson_save_path());
            if (file != null) {
                if (!file.exists()) {
                    bean.setDownLoad_state(STATE_ERROR);
                    helper.updateDownLoadData(db, bean);
                    notifyDownloadStateChanged(bean);
                }
                // 文件加密了，大小不一致
//                else if (file.length() != bean.getLesson_size()) {
//                    file.delete();
//                    bean.setDownLoad_state(STATE_ERROR);
//                    notifyDownloadStateChanged(bean);
//                }
            } else {
                bean.setDownLoad_state(STATE_ERROR);
                helper.updateDownLoadData(db, bean);
                notifyDownloadStateChanged(bean);
            }

        }
//        else if (bean.getDownLoad_state() == STATE_START || bean.getDownLoad_state() == STATE_DOWNLOADING) {
//            if (!mTaskMap.containsKey(bean.getLesson_url())) {
//                bean.setDownLoad_state(STATE_PAUSED);
//                helper.updateDownLoadData(db, bean);
//                notifyDownloadStateChanged(bean);
//            }
//        }

        // 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR、STATE_DELETE。只有这4种状态才能进行下载，其他状态不予处理
        else if (bean.getDownLoad_state() == STATE_NONE
                || bean.getDownLoad_state() == STATE_PAUSED
                || bean.getDownLoad_state() == STATE_DELETE
                || bean.getDownLoad_state() == STATE_ERROR) {
            // 下载之前，把状态设置为STATE_WAITING，因为此时并没有产开始下载，只是把任务放入了线程池中，当任务真正开始执行时，才会改为STATE_DOWNLOADING
            bean.setDownLoad_state(STATE_WAITING);
            helper.updateDownLoadData(db, bean);
            // 每次状态发生改变，都需要回调该方法通知所有观察者
            notifyDownloadStateChanged(bean);

            DownLoadTask task = new DownLoadTask(bean, helper, handler, mTaskMap);// 创建一个下载任务，放入线程池
            // 线程放入map里面方便管理
            mTaskMap.put(bean.getLesson_url(), task);
            DownLoadExecutor.execute(task);
        } else if (bean.getDownLoad_state() == STATE_START
                || bean.getDownLoad_state() == STATE_DOWNLOADING
                || bean.getDownLoad_state() == STATE_WAITING) {// 如果正在下载则暂停

            if (mTaskMap.containsKey(bean.getLesson_url())) {
                DownLoadTask task = mTaskMap.get(bean.getLesson_url());
//                DownLoadTask task = mTaskMap.remove((bean.getLesson_url()));
//                Utils.INSTANCE.log("111", task.toString());
                task.getInfo().setDownLoad_state(STATE_PAUSED);
                mTaskMap.remove(bean.getLesson_url());
                helper.updateDownLoadData(db, task.getInfo());
                // 取消还在排队中的线程
                if (DownLoadExecutor.cancel(task)) {
//                    mObservers.get(bean.id).onStop(task.bean);
                    onStop(task.getInfo());
                }

                notifyDownloadStateChanged(task.getInfo());
            } else {
                bean.setDownLoad_state(STATE_PAUSED);
                helper.updateDownLoadData(db, bean);
                notifyDownloadStateChanged(bean);
            }
        }
    }

    //    /**
//     * 添加下载任务
//     *
//     * @param infos 下载文件信息
//     */
//    public void addTask(ArrayList<DownLoadInfo> infos) {
//        for (int i = 0; i < infos.size(); i++) {
//            addTask(infos.get(i));
//        }
//    }
    //查询单条数据，如果文件不存在，返回状态为错误  STATE_ERROR
    public DownLoadInfo queryDataByLessonId(int lesson_id, String lesson_type) {
        DownLoadInfo bean = helper.queryDownLoadDataByLessonId(db, lesson_id, lesson_type);
        if (bean != null && bean.getDownLoad_state() == STATE_DOWNLOADED) {
            File file = new File(bean.getLesson_save_path());
            if (!file.exists()) {
                bean.setDownLoad_state(STATE_ERROR);
                helper.updateDownLoadData(db, bean);
            } else {

            }
        }

        return bean;
    }

    //查询当前下载中的单条数据，如果没有就获取最近更新的暂停
    public DownLoadInfo queryDataByNew() {
        DownLoadInfo bean = helper.queryDownLoadDataByNew(db);
        if (bean != null && bean.getDownLoad_state() == STATE_DOWNLOADED) {
            File file = new File(bean.getLesson_save_path());
            if (!file.exists()) {
                bean.setDownLoad_state(STATE_ERROR);
                helper.updateDownLoadData(db, bean);
            } else {

            }
        }

        return bean;
    }

    public int querySumDownloading() {

        return helper.querySumDownloading(db);

    }

    public List<DownLoadInfo> queryDownLoadDataByCourseId(String courseId) {
        List<DownLoadInfo> list = helper.queryDownLoadDataByCourseId(db, courseId);
        List<DownLoadInfo> _list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DownLoadInfo bean = list.get(i);
            if (bean.getDownLoad_state() == STATE_DOWNLOADED) {
                File file = new File(bean.getLesson_save_path());
                if (file.exists()) {
                    _list.add(bean);
                }
            }

        }
        return _list;
    }

    public List<DownLoadInfo> queryDataFinish() {
        List<DownLoadInfo> list = helper.queryDataFinish(db);
        List<DownLoadInfo> _list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DownLoadInfo bean = list.get(i);
            if (bean.getDownLoad_state() == STATE_DOWNLOADED) {
                File file = new File(bean.getLesson_save_path());
                if (file.exists()) {
                    _list.add(bean);
                }
            }

        }
        return _list;
    }


    public boolean queryDataWaiting() {
        List<DownLoadInfo> list = helper.queryDataWaiting(db);

        return list.size() > 0;
    }

    public List<DownLoadInfo> queryDataDownloading() {
        List<DownLoadInfo> list = helper.queryDataDownloading(db);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DownLoadInfo bean = list.get(i);

                if (bean.getDownLoad_state() == STATE_DOWNLOADED) {
                    File file = new File(bean.getLesson_save_path());
                    if (!file.exists()) {
                        bean.setDownLoad_state(STATE_ERROR);
                        helper.updateDownLoadData(db, bean);
                    }
                } else {

                    if (!mTaskMap.containsKey(bean.getLesson_url())) {
                        bean.setDownLoad_state(STATE_PAUSED);
                        helper.updateDownLoadData(db, bean);
                    }
                }

            }
        }
        return list;
    }

    @Override
    public void registerObserver(OnProgressListener o) {
        if (!list.contains(o)) {
            list.add(o);
        }
    }

    @Override
    public void removeObserver(OnProgressListener o) {
        list.remove(o);
    }

    @Override
    public void notifyObserver() {

    }

    @Override
    public void onPrepare(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onPrepare(bean);
        }

    }

    @Override
    public void onStart(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onStart(bean);
        }
    }

    @Override
    public void onProgress(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onProgress(bean);
        }
    }

    @Override
    public void onStop(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onStop(bean);
        }
    }

    @Override
    public void onFinish(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onFinish(bean);
        }
    }

    @Override
    public void onError(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onError(bean);
        }
    }

    @Override
    public void onDelete(DownLoadInfo bean) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).onDelete(bean);
        }
    }


    /**
     * 当下载状态发送改变的时候回调
     */
    private ExecuteHandler handler = new ExecuteHandler();

    /**
     * 拿到主线程Looper
     */
    @SuppressLint("HandlerLeak")
    private class ExecuteHandler extends Handler {
        private ExecuteHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            DownLoadInfo bean = (DownLoadInfo) msg.obj;
            for (int i = 0; i < list.size(); i++) {
                OnProgressListener observer = list.get(i);
                switch (bean.getDownLoad_state()) {
                    case STATE_START:// 开始下载
                        observer.onStart(bean);
                        break;
                    case STATE_WAITING:// 准备下载
                        observer.onPrepare(bean);
                        break;
                    case STATE_DOWNLOADING:// 下载中
                        observer.onProgress(bean);
                        break;
                    case STATE_PAUSED:// 暂停
                        observer.onStop(bean);
                        break;
                    case STATE_DOWNLOADED:// 下载完毕
                        observer.onFinish(bean);
                        break;
                    case STATE_ERROR:// 下载失败
                        observer.onError(bean);
                        break;
                    case STATE_DELETE:// 删除成功
                        observer.onDelete(bean);
                        break;
                }
            }
        }

    }

    /**
     * 当下载状态发送改变的时候调用
     */
    private void notifyDownloadStateChanged(DownLoadInfo bean) {
        Message message = handler.obtainMessage();
        message.obj = bean;
        handler.sendMessage(message);
    }


//    public synchronized void deleteDownTaskById(int book_id) {
//        List<DownLoadInfo> list = queryDataById(book_id);
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                deleteDownTask(list.get(i));
//            }
//        }
//        helper.deleteData(db, book_id);
//    }


    /**
     * 删除当前正在下载的任务
     */
    public void deleteDownTask(DownLoadInfo bean) {
        if (mTaskMap.containsKey(bean.getLesson_url())) {
            // 拿到当前任务
            DownLoadTask task = mTaskMap.get(bean.getLesson_url());
            // 暂停下载任务(等于取消了该线程)
            task.getInfo().setDownLoad_state(STATE_DELETE);
            mTaskMap.remove(bean.getLesson_url());
        }
        // 再更改删除界面状态(这是也调一次是怕在没下载的时候删除)
        bean.setDownLoad_state(STATE_DELETE);
        notifyDownloadStateChanged(bean);

        // 最后删除数据库数据
//        helper.updateDownLoadData(db, bean);
        helper.deleteDownLoadData(db, bean);
        // 删除文件
        File file = new File(bean.getLesson_save_path());
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
        }
        file = null;
    }

    /**
     * 销毁的时候关闭线程池以及当前执行的线程，并清空所有数据和把当前下载状态存进数据库
     */
    public void destory() {
        DownLoadExecutor.stop();
        list.clear();
        mTaskMap.clear();
//        FILE_PATH = FILE_PATH_ROOT;
        if (down_bean != null) {//下载中的保存状态
//            down_bean.setDownLoad_state(STATE_PAUSED);
            if (down_bean.getDownLoad_state() == STATE_DOWNLOADING) {
                down_bean.setDownLoad_state(STATE_WAITING);
                helper.updateDownLoadData(db, down_bean);
            }

        }

    }


    public long getCacheSize() {
        long size = 0;
        if (isClearCache) {
            if (!isClearAll) { // 删除当前文件目录下
                File file = new File(FILE_PATH);
                if (file != null && file.exists()) {
                    if (file.isDirectory()) {
                        File[] subFile = file.listFiles();
                        if (subFile != null) {
                            for (int i = 0; i < subFile.length; i++) {
                                if (subFile[i] != null) {
                                    size += subFile[i].length();
                                }
                            }
                        }
                    }
                }
            } else {
                File file = new File(FILE_PATH_ROOT);
                if (file != null && file.exists()) {
                    if (file.isDirectory()) {
                        File[] subFile = file.listFiles();
                        if (subFile != null) {
                            for (int i = 0; i < subFile.length; i++) {
                                if (subFile[i] != null) {
                                    if (subFile[i].isDirectory()) {
                                        File[] childSubFile = subFile[i].listFiles();
                                        for (int j = 0; j < childSubFile.length; j++) {
                                            if (childSubFile[j] != null) {
                                                size += childSubFile[j].length();
                                            }
                                        }
                                    } else {
                                        size += subFile[i].length();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return size;
    }


    /**
     * 清理文件
     */
    public void clearCache(UpdateUi uu) {
        if (isClearCache) {
            if (!isClearAll) {
                // 删除在数据库里的文件
                List<DownLoadInfo> infos = helper.queryDownLoadData(db);
                // 删除文件
                if (!infos.isEmpty()) {
                    for (int i = 0; i < infos.size(); i++) {
                        DownLoadInfo bean = infos.get(i);
                        deleteDownTask(bean);
                    }
                }
                // 防止文件未删除干净
                File file = new File(FILE_PATH);
                if (file != null && file.exists()) {
                    if (file.isDirectory()) {
                        File[] subFile = file.listFiles();
                        if (subFile != null) {
                            for (int i = 0; i < subFile.length; i++) {
                                if (subFile[i] != null) {
                                    subFile[i].delete();
                                }
                            }
                        }
                    }
                }
            } else {
                // 删除在数据库里的文件
                List<DownLoadInfo> infos = helper.queryDownLoadData(db);
                // 删除文件
                if (!infos.isEmpty()) {
                    for (int i = 0; i < infos.size(); i++) {
                        DownLoadInfo bean = infos.get(i);
                        deleteDownTask(bean);
                    }
                }

                // 防止文件为删除干净
                File file = new File(FILE_PATH_ROOT);
                if (file != null && file.exists()) {
                    if (file.isDirectory()) {
                        File[] subFile = file.listFiles();
                        if (subFile != null) {
                            for (int i = 0; i < subFile.length; i++) {
                                if (subFile[i] != null) {
                                    if (subFile[i].isDirectory()) {
                                        File[] childSubFile = subFile[i].listFiles();
                                        for (int j = 0; j < childSubFile.length; j++) {
                                            if (childSubFile[j] != null) {
                                                childSubFile[j].delete();
                                            }
                                        }
                                        subFile[i].delete();
                                    } else {
                                        subFile[i].delete();
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        if (uu != null) {
            uu.updateUI("");
        }

    }


}
