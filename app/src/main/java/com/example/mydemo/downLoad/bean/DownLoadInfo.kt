package com.example.mydemo.downLoad.bean

import com.example.mydemo.downLoad.DownLoaderManger
import java.io.Serializable

/**
 *Date: 2019/11/28
 *author: hxc
 */
data class DownLoadInfo (

    var lesson_id:Int = 0,//id
    var lesson_name:String = "",//名称
    var lesson_duration:Int = 0,//时长
    var lesson_size:Long = 0L,//文件大小
    var lesson_type:String = "",// 文件类型 video  audio
    var lesson_chapter:Int = 0,// 章节序号

    var course_id:Int = 0,//课程id
    var course_name:String = "",//课程名称
    var course_cover_url:String = "",//课程图片
    var course_author:String = "",//课程作者
    var course_chapter_num : Int= 0,//课程总章节数

    var lesson_url:String = "",//下载地址
    var finished:Long = 0L,//下载已完成进度
    var lesson_save_path :String = "",//保存的路径
    var downLoad_state:Int = DownLoaderManger.STATE_NONE,//
    var share_qrcode:String = "",//分享二维码
    var share_url:String = "",//分享url
    var update_time:Long = 0L,//最后更新时间
    // 不加入数据库，只是下载列表做删除选中
    var isChecked:Boolean = false


) :Serializable