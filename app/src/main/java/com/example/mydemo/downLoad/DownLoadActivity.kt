package com.example.mydemo.downLoad

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.*
import android.text.format.Formatter
import android.util.Log
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.downLoad.bean.DownLoadInfo
import com.example.mydemo.downLoad.pinterface.OnProgressListener
import com.example.mydemo.R
import com.example.mydemo.downLoad.adapter.DownLoadAdapter
import com.example.mydemo.downLoad.db.DownLoadDbHelper
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.activity_down_load.*
import java.io.File

class DownLoadActivity : BaseActivity(), OnProgressListener {



    private var filePath = DownLoaderManger.FILE_PATH_ROOT + "/" + Utils.getUserId()
    //
    private var downLoader: DownLoaderManger? = null

    private val datas = ArrayList<DownLoadInfo>()

    override fun getLayoutId(): Int {
        return R.layout.activity_down_load
    }

    override fun initView() {

        val helper = DownLoadDbHelper(this)
        downLoader = DownLoaderManger.getInstance(helper)

        var info1 = DownLoadInfo()
        info1.lesson_id = 1
        info1.lesson_name = "0视频"
        info1.lesson_duration = 0
        info1.lesson_size = 0
        info1.lesson_type = "video"
        info1.lesson_chapter = 0
        info1.course_id = 1
        info1.course_name = "理论上来说（赠送课程1）"
        info1.course_cover_url = "https://staticdev.chuanghehui.com/pc-course-cover/1574229113222.png"
        info1.course_author = "XX · tittleABCDEFG"
        info1.course_chapter_num = 3
        info1.lesson_url = "http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4"
        info1.finished = 0
        info1.share_qrcode = ""
        info1.share_url = ""

        if (info1.lesson_url.isNotEmpty() && info1.lesson_url.contains("/")) {
            info1.lesson_save_path = "${DownLoaderManger.FILE_PATH}${info1.lesson_url.substring(info1.lesson_url.lastIndexOf("/"))}"
        }
        info1.downLoad_state = DownLoaderManger.STATE_NONE
        datas.add(info1)

        var info2 = DownLoadInfo()
        info2.lesson_id = 2
        info2.lesson_name = "1视频"
        info2.lesson_duration = 0
        info2.lesson_size = 0
        info2.lesson_type = "video"
        info2.lesson_chapter = 1
        info2.course_id = 1
        info2.course_name = "理论上来说（赠送课程1）"
        info2.course_cover_url = "https://staticdev.chuanghehui.com/pc-course-cover/1574229113222.png"
        info2.course_author = "XX · tittleABCDEFG"
        info2.course_chapter_num = 3
        info2.lesson_url = "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4"
        info2.finished = 0
        info2.share_qrcode = ""
        info2.share_url = ""

        if (info2.lesson_url.isNotEmpty() && info2.lesson_url.contains("/")) {
            info2.lesson_save_path = "${DownLoaderManger.FILE_PATH}${info2.lesson_url.substring(info2.lesson_url.lastIndexOf("/"))}"
        }
        datas.add(info2)

        var info3 = DownLoadInfo()
        info3.lesson_id = 3
        info3.lesson_name = "2视频"
        info3.lesson_duration = 0
        info3.lesson_size = 0
        info3.lesson_type = "video"
        info3.lesson_chapter = 2
        info3.course_id = 1
        info3.course_name = "理论上来说（赠送课程1）"
        info3.course_cover_url = "https://staticdev.chuanghehui.com/pc-course-cover/1574229113222.png"
        info3.course_author = "XX · tittleABCDEFG"
        info3.course_chapter_num = 3
        info3.lesson_url = "http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4"
        info3.finished = 0
        info3.share_qrcode = ""
        info3.share_url = ""

        if (info3.lesson_url.isNotEmpty() && info3.lesson_url.contains("/")) {
            info3.lesson_save_path = "${DownLoaderManger.FILE_PATH}${info3.lesson_url.substring(info3.lesson_url.lastIndexOf("/"))}"
        }
        datas.add(info3)


        for (info in datas) {
            var temInfo = downLoader!!.queryDataByLessonId(info.lesson_id,info.lesson_type)
            if (temInfo != null){
                Log.i("111",temInfo.toString())
                temInfo as DownLoadInfo
                if(temInfo.lesson_id == info.lesson_id) {

                    info.lesson_id = temInfo.lesson_id
                    info.lesson_name = temInfo.lesson_name
                    info.lesson_duration = temInfo.lesson_duration
                    info.lesson_size = temInfo.lesson_size

                    info.course_id = temInfo.course_id
                    info.course_name = temInfo.course_name
                    info.course_cover_url = temInfo.course_cover_url
                    info.course_author = temInfo.course_author

                    info.finished = temInfo.finished
                    info.lesson_url = temInfo.lesson_url
                    info.lesson_save_path = temInfo.lesson_save_path
                    info.downLoad_state = temInfo.downLoad_state
                }
            }

        }

        lv.adapter = DownLoadAdapter(this, datas) {

            if (requestStorage(this)) {

                if(it.downLoad_state != 4){
                    downLoader?.download(it)
                }

            }

        }


        if (requestStorage(this)) {
            tv_mem.text = "${Formatter.formatFileSize(
                baseContext,
                getUsedMemory(filePath)
            )} / ${getMemoryInfo(Environment.getExternalStorageDirectory())}"
        }

    }


    override fun addListener() {

    }

    override fun onResume() {
        super.onResume()
        downLoader?.registerObserver(this)
    }


    override fun onPause() {
        super.onPause()
        downLoader?.removeObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        downLoader?.destory()


    }

    @Synchronized
    private fun setBean(bean: DownLoadInfo?) {
        if (bean == null) {
            return
        }
        var index = -1
        for (i in datas.indices) {
            if (bean.lesson_url == datas[i].lesson_url) {
                index = i
            }
        }

        setText(index, bean)
    }

    private fun setText(position: Int, entity: DownLoadInfo?) {
        if (position != -1) {
            val contentView = lv.getChildAt(position)//核心方法
//            val tvDown = contentView.findViewById(R.id.tv_pro) as TextView
            when (entity!!.downLoad_state) {
                DownLoaderManger.STATE_START// 开始下载
                -> {
//                    tvDown.text = "开始下载"
                }
                DownLoaderManger.STATE_WAITING// 准备下载
                -> {
//                    tvDown.text = "准备下载"
                }
                DownLoaderManger.STATE_DOWNLOADING// 下载中
                -> {
//                    tvDown.text = "下载中"
                    if (entity.lesson_size > 0) {
//                        tvDown.append("${entity.finished * 100 / entity.lesson_size} %")
                    }
                }
                DownLoaderManger.STATE_PAUSED// 暂停
                -> {
//                    tvDown.text = "已暂停，点击继续下载"
                }
                DownLoaderManger.STATE_DOWNLOADED// 下载完毕
                -> {
//                    tvDown.text = "下载完毕"
                }
                DownLoaderManger.STATE_ERROR// 下载失败
                -> {
//                    tvDown.text = "下载失败"
                }
                DownLoaderManger.STATE_DELETE// 删除成功
                -> {
                }
            }
        }
    }


    override fun onPrepare(bean: DownLoadInfo?) {
        setBean(bean)
    }

    override fun onStart(bean: DownLoadInfo?) {
        setBean(bean)
    }

    override fun onProgress(bean: DownLoadInfo?) {
        Log.i("111","onProgress ${bean?.finished}")
        setBean(bean)
    }

    override fun onStop(bean: DownLoadInfo?) {
        setBean(bean)
    }

    override fun onFinish(bean: DownLoadInfo?) {
        Log.i("111","onFinish ${bean?.finished}")
        setBean(bean)
    }

    override fun onError(bean: DownLoadInfo?) {
        setBean(bean)
    }

    override fun onDelete(bean: DownLoadInfo?) {
        setBean(bean)
    }



    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
//    private fun download(info: DownLoadInfo) {
//        Thread {
//            val mClient = OkHttpClient()
//            val request = Request.Builder()
//                .url(info.lesson_url).get()
//                .build()
//            try {
//                val response = mClient.newCall(request).execute()
//                if (response != null && response.isSuccessful) {
//                    var contentLength = if (response.body() == null) 0 else response.body()!!.contentLength()
//                    response.close()
//                    info.lesson_size = contentLength
//
//                    Log.i("111", "contentLength = $contentLength")
//
//                    var msg = Message()
//                    msg.obj = info
//                    mHandler.sendMessage(msg)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Log.i("111", "e.printStackTrace() = ${e.printStackTrace()}")
//            }
//
//        }.start()
//    }


    private fun requestStorage(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
                return false
            }
        }
        return true
    }

    private fun afterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    private fun getUsedMemory(filePath: String): Long {// 获取缓存大小

        var size = 0L
        val file = File(filePath)
        if (file.exists()) {
            try {
                val fileList = file.listFiles()
                for (f in fileList) {
                    if (f.isDirectory) {
                        size += getUsedMemory(f.path)
                    } else {
                        size += f.length()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return size
    }


    /**
     * 根据路径获取内存状态
     * @param path
     * @return
     */
    private fun getMemoryInfo(path: File): String {
        // 获得一个磁盘状态对象
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong   // 获得一个扇区的大小
        val totalBlocks = stat.blockCountLong   // 获得扇区的总数
        val availableBlocks = stat.availableBlocksLong   // 获得可用的扇区数量
        // 总空间
        val totalMemory = Formatter.formatFileSize(this, totalBlocks * blockSize)
        // 可用空间
        val availableMemory = Formatter.formatFileSize(this, availableBlocks * blockSize)

        return availableMemory
    }



    //一些视频音频的链接

//    驯龙高手http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4
//
//    《紧急救援》
//
//    http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4
//
//    玩具总动员
//
//    http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4
//
//    《叶问4》先行预告甄子丹过招美
//
//    http://vfx.mtime.cn/Video/2019/03/19/mp4/190319212559089721.mp4
//
//    预告刘德华对决古天
//
//    http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4
//
//    差不多够用了
//
//    http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4
//
//    http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4
//
//    最后来一张图片的地址
//
//    http://pic37.nipic.com/20140113/8800276_184927469000_2.png
//   http://music.163.com/song/media/outer/url?id=1402851948.mp3
    //http://music.163.com/song/media/outer/url?id=32703006.mp3

}
