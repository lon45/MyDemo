package com.example.mydemo.DownLoad.bean

import com.example.mydemo.DownLoad.DownLoaderManger
import java.io.Serializable

/**
 *Date: 2019/11/28
 *author: hxc
 */
data class DownLoadInfo (

    var lesson_id:Int = 0,//id
    var lesson_name:String = "",//名称
    var lesson_duration:Int = 0,//长度
    var lesson_size:Long = 0L,//文件大小

    var course_id:Int = 0,//课程id
    var course_name:String = "",//课程名称
    var course_cover_url:String = "",//课程图片
    var course_author:String = "",//课程作者

    var lesson_url:String = "",//下载地址
    var finished:Long = 0L,//下载已完成进度
    var lesson_save_path :String = "",//保存的路径
    var downLoad_state:Int = DownLoaderManger.STATE_NONE//


) :Serializable