package com.example.mydemo.downLoad.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mydemo.downLoad.bean.DownLoadInfo
import com.example.mydemo.downLoad.DownLoaderManger
import com.example.mydemo.util.Utils


/**
 *Date: 2019/11/28
 *author: hxc
 * test
 */
class DownLoadDbHelper(mContext: Context) : SQLiteOpenHelper(mContext, Utils.getUserId() + "download.db", null, 1) {

    //课时信息
    private val LESSON_ID = "lesson_id"//课时id
    private val LESSON_NAME = "lesson_name"//课时名称
    private val LESSON_DURATION = "lesson_duration"//课时时长
    private val LESSON_SIZE = "lesson_size"//课时总长度
    private val LESSON_TYPE = "lesson_type"// 文件类型 audio video
    private val LESSON_CHAPTER = "lesson_chapter"// 章节序号

    //课程信息(包含多个课时)
    private val COURSE_ID = "course_id"//课程id
    private val COURSE_NAME = "course_name"//课程名称
    private val COURSE_COVER_URL = "course_cover_url"//课程图片
    private val COURSE_AUTHOR = "course_author"//课程作者
    private val COURSE_CHAPTER_NUM = "course_chapter_num"//总章节数


    private val FINISHED = "finished"//下载已完成进度
    private val LESSON_URL = "lesson_url"//url
    private val LESSON_SAVE_PATH = "lesson_save_path"//保存的路径
    private val DOWNLOAD_STATE = "downLoad_state"//

    private val DOWNLOAD_UPDATE_TIME = "downLoad_update_time"//

    //分享字段
    private val SHARE_QR = "share_qr"//
    private val SHARE_URL = "share_url"//

    private var DOWNLOAD_TABLE = "download_info"//下载 表名

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(
            "create table if not exists $DOWNLOAD_TABLE ($LESSON_ID integer,$LESSON_NAME varchar,$LESSON_DURATION varchar,$LESSON_SIZE integer,$LESSON_TYPE varchar,$LESSON_CHAPTER integer," +
                    "$COURSE_ID integer,$COURSE_NAME varchar,$COURSE_COVER_URL varchar,$COURSE_AUTHOR varchar,$COURSE_CHAPTER_NUM integer," +
                    "$FINISHED integer,$LESSON_URL varchar,$LESSON_SAVE_PATH varchar,$DOWNLOAD_STATE integer,$SHARE_QR varchar,$SHARE_URL varchar,$DOWNLOAD_UPDATE_TIME integer)"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    /**
     * 查询下载信息是否存在  判断 课时id 文件类型
     */
    @Synchronized
    fun isExistData(db: SQLiteDatabase, info: DownLoadInfo): Boolean {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$LESSON_ID=? and $LESSON_TYPE=?",
            arrayOf(info.lesson_id.toString(), info.lesson_type),
            null,
            null,
            null,
            null
        )
        val exist = cursor.moveToNext()
        cursor.close()
        return exist

    }

    //查询 正在下载的任务，如果没有正在下载的任务，取最新修改的一条数据
    @Synchronized
    fun queryDownLoadDataByNew(db: SQLiteDatabase): DownLoadInfo? {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$DOWNLOAD_STATE=?",
            arrayOf(DownLoaderManger.STATE_DOWNLOADING.toString()),
            null,
            null,
            null,
            null
        )
        var info: DownLoadInfo? = null
        if (cursor != null) {
            if (cursor.moveToNext()) {
                info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))
            }
            cursor.close()
        }

        if (info == null) {
            val cursor = db.query(
                DOWNLOAD_TABLE,
                null,
                "$DOWNLOAD_STATE =?",
                arrayOf(DownLoaderManger.STATE_PAUSED.toString()),
                null,
                null,
                "$DOWNLOAD_UPDATE_TIME desc",
                null
            )
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    info = DownLoadInfo()
                    info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                    info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                    info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                    info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                    info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                    info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                    info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                    info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                    info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                    info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                    info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                    info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                    info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                    info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                    info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                    info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                    info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))
                }
                cursor.close()
            }

        }
        return info

    }


    //查询 某条任务
    @Synchronized
    fun queryDownLoadDataByLessonId(db: SQLiteDatabase, lesson_id: Int, lesson_type: String): DownLoadInfo? {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$LESSON_ID=? and $LESSON_TYPE=?",
            arrayOf(lesson_id.toString(), lesson_type),
            null,
            null,
            null,
            null
        )
        var info: DownLoadInfo? = null
        if (cursor != null) {
            if (cursor.moveToNext()) {
                info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

            }
            cursor.close()
        }
        return info

    }

    //查询该某个课程所有的数据
    @Synchronized
    fun queryDownLoadDataByCourseId(db: SQLiteDatabase, course_id: String): ArrayList<DownLoadInfo> {
        val cursor =
            db.query(DOWNLOAD_TABLE, null, "$COURSE_ID=?", arrayOf(course_id), null, null, "$LESSON_CHAPTER asc", null)
        val list = ArrayList<DownLoadInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))
                info.course_chapter_num = cursor.getInt(cursor.getColumnIndex(COURSE_CHAPTER_NUM))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

                list.add(info)
            }
            cursor.close()
        }

        return list

    }

    //查询待所有的任务
    @Synchronized
    fun queryDownLoadData(db: SQLiteDatabase): ArrayList<DownLoadInfo> {
        val cursor = db.query(DOWNLOAD_TABLE, null, null, null, null, null, null)
        val list = ArrayList<DownLoadInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

                list.add(info)
            }
            cursor.close()
        }

        return list

    }

    //查询待下载的任务数量  下载中，等待中，暂停中
    @Synchronized
    fun querySumDownloading(db: SQLiteDatabase): Int {
        var num = 0
        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ?",
            arrayOf(
                DownLoaderManger.STATE_DOWNLOADING.toString(),
                DownLoaderManger.STATE_WAITING.toString(),
                DownLoaderManger.STATE_PAUSED.toString()
            ),
            null,
            null,
            null
        )
        if (cursor != null) {
            num = cursor.count
        }
        cursor?.close()
        return num

    }

    //查询待下载的任务  下载中，等待中，暂停中
    @Synchronized
    fun queryDataDownloading(db: SQLiteDatabase): ArrayList<DownLoadInfo> {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ?",
            arrayOf(
                DownLoaderManger.STATE_DOWNLOADING.toString(),
                DownLoaderManger.STATE_WAITING.toString(),
                DownLoaderManger.STATE_PAUSED.toString(),
                DownLoaderManger.STATE_START.toString()
            ),
            null,
            null,
            null
        )
        val list = ArrayList<DownLoadInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

                list.add(info)
            }
            cursor.close()
        }

        return list

    }

    //查询待下载的任务  根据 下载状态 自动开启下载功能
    @Synchronized
    fun queryDataWaiting(db: SQLiteDatabase): ArrayList<DownLoadInfo> {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$DOWNLOAD_STATE = ? or $DOWNLOAD_STATE = ?",
            arrayOf(DownLoaderManger.STATE_DOWNLOADING.toString(), DownLoaderManger.STATE_WAITING.toString()),
            null,
            null,
            null
        )
        val list = ArrayList<DownLoadInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

                list.add(info)
            }
            cursor.close()
        }

        return list

    }

    //查询完成的任务  根据 下载状态  课程id分类
    @Synchronized
    fun queryDataFinish(db: SQLiteDatabase): ArrayList<DownLoadInfo> {

        val cursor = db.query(
            DOWNLOAD_TABLE,
            null,
            "$DOWNLOAD_STATE = ?",
            arrayOf(DownLoaderManger.STATE_DOWNLOADED.toString()),
            null,
            null,
            "$COURSE_ID desc"
        )
        val list = ArrayList<DownLoadInfo>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))
                info.lesson_type = cursor.getString(cursor.getColumnIndex(LESSON_TYPE))
                info.lesson_chapter = cursor.getInt(cursor.getColumnIndex(LESSON_CHAPTER))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
                info.update_time = cursor.getLong(cursor.getColumnIndex(DOWNLOAD_UPDATE_TIME))

                info.share_qrcode = cursor.getString(cursor.getColumnIndex(SHARE_QR))
                info.share_url = cursor.getString(cursor.getColumnIndex(SHARE_URL))

                list.add(info)
            }
            cursor.close()
        }

        return list

    }

    /**
     * 插入一条下载信息
     */
    @Synchronized
    fun insertDownLoadData(db: SQLiteDatabase, info: DownLoadInfo) {
        val values = ContentValues()

        values.put(LESSON_ID, info.lesson_id)
        values.put(LESSON_NAME, info.lesson_name)
        values.put(LESSON_DURATION, info.lesson_duration)
        values.put(LESSON_SIZE, info.lesson_size)
        values.put(LESSON_TYPE, info.lesson_type)
        values.put(LESSON_CHAPTER, info.lesson_chapter)

        values.put(COURSE_ID, info.course_id)
        values.put(COURSE_NAME, info.course_name)
        values.put(COURSE_COVER_URL, info.course_cover_url)
        values.put(COURSE_AUTHOR, info.course_author)
        values.put(COURSE_CHAPTER_NUM, info.course_chapter_num)

        values.put(LESSON_SAVE_PATH, info.lesson_save_path)
        values.put(LESSON_URL, info.lesson_url)
        values.put(FINISHED, info.finished)
        values.put(DOWNLOAD_STATE, info.downLoad_state)

        values.put(SHARE_QR, info.share_qrcode)
        values.put(SHARE_URL, info.share_url)

        values.put(DOWNLOAD_UPDATE_TIME, System.currentTimeMillis())
        db.insert(DOWNLOAD_TABLE, null, values)
    }

    /**
     * 更新一条下载信息 根据 课时id 和文件类型
     */
    @Synchronized
    fun updateDownLoadData(db: SQLiteDatabase, info: DownLoadInfo) {
        val values = ContentValues()
        values.put(LESSON_DURATION, info.lesson_duration)
        values.put(LESSON_SIZE, info.lesson_size)
        values.put(FINISHED, info.finished)
        values.put(DOWNLOAD_STATE, info.downLoad_state)
        values.put(DOWNLOAD_UPDATE_TIME, System.currentTimeMillis())

        db.update(
            DOWNLOAD_TABLE,
            values,
            "$LESSON_ID=? and $LESSON_TYPE=?",
            arrayOf(info.lesson_id.toString(), info.lesson_type)
        )
    }

    /**
     * 刪除一条下载信息  根据 课时id 和文件类型
     */
    @Synchronized
    fun deleteDownLoadData(db: SQLiteDatabase, info: DownLoadInfo) {
        db.delete(
            DOWNLOAD_TABLE,
            "$LESSON_ID=? and $LESSON_TYPE=?",
            arrayOf(info.lesson_id.toString(), info.lesson_type)
        )
    }

    /**
     * 删表
     */
    @Synchronized
    fun dropDownLoadData(db: SQLiteDatabase) {
        db.execSQL("drop table $DOWNLOAD_TABLE")
    }

}