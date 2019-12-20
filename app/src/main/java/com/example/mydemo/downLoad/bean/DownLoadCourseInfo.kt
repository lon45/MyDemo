package com.example.mydemo.downLoad.bean

import java.io.Serializable

/**
 *Date: 2019/11/28
 *author: hxc
 */
data class DownLoadCourseInfo (


    var course_id:Int = 0,//课程id
    var course_name:String = "",//课程名称
    var course_cover_url:String = "",//课程图片
    var course_author:String = "",//课程作者
    var course_state:Int = 0,// 0 正常，1 过期，2 下架（过期是本地超过3个月，下架是服务器端下架）
    var video_sum:Int = 0,//视频数
    var audio_sum:Int = 0,//音频数
    var total_size:Long = 0,//
    var lessonList:ArrayList<DownLoadInfo> = ArrayList<DownLoadInfo>()



) :Serializable