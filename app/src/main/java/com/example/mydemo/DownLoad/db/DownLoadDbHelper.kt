package com.example.mydemo.DownLoad.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mydemo.DownLoad.bean.DownLoadInfo
import com.example.mydemo.Util.Utils


/**
 *Date: 2019/11/28
 *author: hxc
 * test
 */
class DownLoadDbHelper(mContext: Context) : SQLiteOpenHelper(mContext, Utils.getUserId()+"download.db", null, 1) {

    //课时信息
    private val LESSON_ID = "lesson_id"//课时id
    private val LESSON_NAME = "lesson_name"//课时名称
    private val LESSON_DURATION = "lesson_duration"//课时时长
    private val LESSON_SIZE = "lesson_size"//课时总长度

    //课程信息(包含多个课时)
    private val COURSE_ID = "course_id"//课程id
    private val COURSE_NAME = "course_name"//课程名称
    private val COURSE_COVER_URL = "course_cover_url"//课程图片
    private val COURSE_AUTHOR = "course_author"//课程作者

    private val FINISHED = "finished"//下载已完成进度
    private val LESSON_URL = "lesson_url"//url
    private val LESSON_SAVE_PATH = "lesson_save_path"//保存的路径
    private val DOWNLOAD_STATE = "downLoad_state"//

    private var DOWNLOAD_TABLE = "download_info"//下载 表名

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("create table if not exists $DOWNLOAD_TABLE ($LESSON_ID integer,$LESSON_NAME varchar,$LESSON_DURATION varchar,$LESSON_SIZE integer," +
                "$COURSE_ID integer,$COURSE_NAME varchar,$COURSE_COVER_URL varchar,$COURSE_AUTHOR varchar," +
                "$FINISHED integer,$LESSON_URL varchar,$LESSON_SAVE_PATH varchar,$DOWNLOAD_STATE integer)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {


    }

    /**
     * 查询下载信息是否存在
     */
    @Synchronized
    fun isExistData(db: SQLiteDatabase, info: DownLoadInfo): Boolean {

        val cursor = db.query(DOWNLOAD_TABLE, null, "$LESSON_ID=? and $COURSE_ID=?", arrayOf(info.lesson_id.toString(),info.course_id.toString()), null, null, null, null)
        val exist = cursor.moveToNext()
        cursor.close()
        return exist

    }


    @Synchronized
    fun queryDownLoadDataById(db: SQLiteDatabase, lesson_id: Int, course_id:Int): DownLoadInfo? {

        val cursor = db.query(DOWNLOAD_TABLE, null, "$LESSON_ID=? and $COURSE_ID=?", arrayOf(lesson_id.toString(),course_id.toString()), null, null, null, null)
        var info : DownLoadInfo? = null
        if (cursor != null) {
            if (cursor.moveToNext()) {
                info = DownLoadInfo()
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
            }
            cursor.close()
        }
        return info

    }

    @Synchronized
    fun queryDownLoadData(db: SQLiteDatabase): ArrayList<DownLoadInfo> {

        val cursor = db.query(DOWNLOAD_TABLE, null, null, null, null, null, null)
        val list = ArrayList<DownLoadInfo>()
        var info = DownLoadInfo()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                info.lesson_id = cursor.getInt(cursor.getColumnIndex(LESSON_ID))
                info.lesson_name = cursor.getString(cursor.getColumnIndex(LESSON_NAME))
                info.lesson_duration = cursor.getInt(cursor.getColumnIndex(LESSON_DURATION))
                info.lesson_size = cursor.getLong(cursor.getColumnIndex(LESSON_SIZE))

                info.course_id = cursor.getInt(cursor.getColumnIndex(COURSE_ID))
                info.course_name = cursor.getString(cursor.getColumnIndex(COURSE_NAME))
                info.course_cover_url = cursor.getString(cursor.getColumnIndex(COURSE_COVER_URL))
                info.course_author = cursor.getString(cursor.getColumnIndex(COURSE_AUTHOR))

                info.lesson_url = cursor.getString(cursor.getColumnIndex(LESSON_URL))
                info.finished = cursor.getLong(cursor.getColumnIndex(FINISHED))
                info.lesson_save_path = cursor.getString(cursor.getColumnIndex(LESSON_SAVE_PATH))
                info.downLoad_state = cursor.getInt(cursor.getColumnIndex(DOWNLOAD_STATE))
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

        values.put(COURSE_ID, info.course_id)
        values.put(COURSE_NAME, info.course_name)
        values.put(COURSE_COVER_URL, info.course_cover_url)
        values.put(COURSE_AUTHOR, info.course_author)

        values.put(LESSON_SAVE_PATH, info.lesson_save_path)
        values.put(LESSON_URL, info.lesson_url)
        values.put(FINISHED, info.finished)
        values.put(DOWNLOAD_STATE, info.downLoad_state)
        db.insert(DOWNLOAD_TABLE, null, values)
    }

    /**
     * 更新一条下载信息
     */
    @Synchronized
    fun updateDownLoadData(db: SQLiteDatabase, info: DownLoadInfo) {
        val values = ContentValues()
        values.put(LESSON_DURATION, info.lesson_duration)
        values.put(LESSON_SIZE, info.lesson_size)
        values.put(FINISHED, info.finished)
        values.put(DOWNLOAD_STATE, info.downLoad_state)

        db.update(DOWNLOAD_TABLE, values, "$LESSON_ID=? and $COURSE_ID=?", arrayOf(info.lesson_id.toString(),info.course_id.toString()))
    }

    /**
     * 刪除一条下载信息
     */
    @Synchronized
    fun deleteDownLoadData(db: SQLiteDatabase, info: DownLoadInfo) {
        db.delete(DOWNLOAD_TABLE, "$LESSON_ID=? and $COURSE_ID=?", arrayOf(info.lesson_id.toString(),info.course_id.toString()))
    }

    /**
     * 删表
     */
    @Synchronized
    fun dropDownLoadData(db: SQLiteDatabase) {
        db.execSQL("drop table $DOWNLOAD_TABLE")
    }

}