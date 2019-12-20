package com.app.chuanghehui.commom

import android.os.Environment

import java.io.File

//========开发环境=======

const val TEST_API_SERVER_URL = "https://apitest.chuanghehui.com/"//开发接口域名
const val TEST_OSS_FILE_URL = "https://statictest.chuanghehui.com"//开发资源域名
const val TEST_H5_URL = "${TEST_OSS_FILE_URL}/test"//开发H5域名
const val TEST_SMALL_URL = "https://xcxapitest.chuanghehui.com/"//测试地址 公开课 小程序
const val TEST_SMALL_ACTIVITY_URL = "https://xcxapitest.chuanghehui.com/"//测试地址 活动 小程序
const val TEST_OFFICIAL_WEBSITE_H5_URL = "https://test.chuanghehui.com/view/"//官网


//========开发环境=======
const val TEST2_API_SERVER_URL = "https://apitest2.chuanghehui.com/"//开发接口域名
const val TEST2_OSS_FILE_URL = "https://statictest.chuanghehui.com"//开发资源域名
const val TEST2_H5_URL = "${TEST_OSS_FILE_URL}/test"//开发H5域名
//预发布
//const val API_SERVER_URL = "https://api2.chuanghehui.com/"//线上接口域名

//========线上环境=======
const val API_SERVER_URL = "https://api.chuanghehui.com/"//线上接口域名
const val OSS_FILE_URL = "https://static.chuanghehui.com" //线上资源域名
const val H5_URL = "${OSS_FILE_URL}/release"//线上H5域名
const val API_SMALL_URL = "https://xcxapi.chuanghehui.com/" //正式地址 公开课 小程序
const val API_SMALL_ACTIVITY_URL = "https://xcxapi.chuanghehui.com/"//测试地址 活动 小程序
const val OFFICIAL_WEBSITE_H5_URL = "https://www.chuanghehui.com/view/"//官网



@JvmField var BASE_SERVER_URL = ""
@JvmField var BASE_FILE_URL = ""
@JvmField var BASE_H5_URL = ""
@JvmField var BASE_SMALL_URL = ""
@JvmField var BASE_SMALL_ACTIVITY_URL = ""
@JvmField var BASE_OFFICIAL_WEBSITE_H5_URL = ""

@JvmField var SHOW_LOG = false
/**
 *  是否显示赠课功能
 */
@JvmField var SHOW_GIVEN_LESSON = true

@JvmField var CASUALLY_LOOK = true

@JvmField var  CLASS_SCHEDULE_URL = "${BASE_H5_URL}/index.html#/myCourse/calendar" //课程日历
@JvmField var  ARTICLE_URL = "${BASE_H5_URL}/index.html#/articleDetail/" //文章详情
@JvmField var  ACTIVITY_URL = "${BASE_H5_URL}/index.html#/activityDetail/"   //活动详情
@JvmField var  CHUANGKEBANG_URL = "${BASE_H5_URL}/index.html#/articlePacking/" //http://shopcdn.eintsoft.com/wetech-h5/index.html#/articlePacking/?list=[{"title":"创业服务","id":76},{"title":"赤兔伙伴","id":77},{"title":"创客空间","id":78}]&num=1
@JvmField var  COMMUNITY_URL = "${BASE_H5_URL}/index.html#/joinCommunity"
@JvmField var  FORM_URL = "${BASE_H5_URL}/index.html#/showForm"
@JvmField var  COURSE_DETAIL_URL = "${BASE_H5_URL}/index.html#/courseDetail"


//const val WX_APPID = "wxac853707db8b6ad7"
//const val WX_SECRET = "1bb4ed9b8f62d1d4318d5c06412448aa"


const val WX_APPID = "wx14c7e5b12004c607"
const val WX_SECRET = "70b04e1abf9fce0ec242aab101f98f76"

/**一次性订阅消息 模版id*/
const val WXtemplateID = "9ufmv4xqW1a36Ucz6WrTJIOPlC5JcqQ1fB7wSLIIHOs"

/**智齿在线客服*/
const val Customer_APPKEY = "4f80bece48014c48bdc71d41e66728fb"
/**智齿在线客服 群组 id*/
const val Customer_Group_Id = "80cdba1273564d04ac617a5321729a0b"
/**友盟appkey*/
const val UMENG_APPKEY = "58a3ffa85312dd7cb700047e"
////测试key
//const val UMENG_APPKEY = "5dede5cb570df309bd00037a"

//val DIR_ROOT = Environment.getExternalStorageDirectory().path
/**
 * 文件目录名称
 */
//@JvmField var  DIR_CHE = DIR_ROOT + File.separator + "com.app.chuanghehui"
@JvmField var  DIR_CHE = ""
/**
 * 通用图片地址
 *
 */
//@JvmField var  PIC_DIR_CHE = "$DIR_CHE/cache/PictureCache"// 原來用 PictureSelector
@JvmField var  PIC_DIR_CHE = ""// 原來用 PictureSelector


const val SP_KEY_USR_ID = "sp_key_usr_id"
const val SP_KEY_USR_SHARE_KEY = "sp_key_usr_share_key"
const val SP_KEY_DAILY_WORD = "sp_key_daily_word"
const val SP_KEY_SHOW_EVENT = "sp_key_show_event"
const val SP_IS_FIRST_LOGIN = "sp_is_first_login"
const val SP_NAME = "ss_name"
const val SP_LOGIN_INFO = "ss_login_info"
const val SP_IS_FIRST_SHOW_WELCOME = "sp_is_first_show_welcome"


const val COMMON_SCANCAPETURE_REQ_CODE = 10001
const val COMMON_SCANCAPETURE_MY_REQ_CODE = 10002//我的 扫码请求值 （包含 活动签到和课程签到，和其他地方区别开）

const val MAIN_ACTION_JUMPTOLESSON = "lesson"


