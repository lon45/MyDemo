package com.example.mydemo.countdown

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.util.AttributeSet
import android.widget.TextView


/**
 *Date: 2019/11/14
 *author: hxc
 * 有背景的计时器
 */
class MyCountDownTimer2 : TextView {

    private var countTime = 0L
    private var countDownTimer: MyCountDownTimer? = null

    private var mContext: Context? = context

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
    }

    //设置数据
    fun setTime(time: Int) {


        if (time <= 0) {
            text = "00:00:00"
            changeStyle()
            return
        }
        this.countTime = time * 1000L

        if (countDownTimer != null) {
            stopTimer()
        }
        startTimer()

    }

    private fun changeStyle() {
        var replaceText = text.toString().replace(":", "：")
        val spanString = SpannableString(replaceText)

//        val spanbg1 = BackgroundImageSpan(R.drawable.shape_circle_d18d57,mContext!!.resources.getDrawable(R.drawable.shape_circle_corner_efefef))
//        val spanbg2 = BackgroundImageSpan(R.drawable.shape_circle_d18d57,mContext!!.resources.getDrawable(R.drawable.shape_circle_corner_efefef))
//        val spanbg3 = BackgroundImageSpan(R.drawable.shape_circle_d18d57,mContext!!.resources.getDrawable(R.drawable.shape_circle_corner_efefef))


        val spanbg1 = BackgroundColorSpan(Color.parseColor("#D18D57"))
        val spanbg2 = BackgroundColorSpan(Color.parseColor("#D18D57"))
        val spanbg3 = BackgroundColorSpan(Color.parseColor("#D18D57"))

        if(replaceText.length<=9){
            spanString.setSpan(spanbg1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spanString.setSpan(spanbg2, 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spanString.setSpan(spanbg3, 6, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }else{

            spanString.setSpan(spanbg1, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spanString.setSpan(spanbg2, 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spanString.setSpan(spanbg3, 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        text = spanString
    }

    //计时器
    private inner class MyCountDownTimer(totleTime: Long, durTime: Long) : CountDownTimer(totleTime, durTime) {
        override fun onFinish() {
            stopTimer()
        }

        override fun onTick(millisUntilFinished: Long) {
            var timeStr = getTimeByLong(millisUntilFinished / 1000)
//            Log.i("111", timeStr)
            text = timeStr
            changeStyle()
        }

    }

    // hh:mm:ss
    var hour = 60 * 60
    var minute = 60
    fun getTimeByLong(time: Long): String {
        var timeStr = "00:00:00"
        if(time > 0){
            val h = time / hour
            val m = time % hour / minute
            val s = time % minute

            timeStr = String.format(" %02d : %02d : %02d ",h,m,s)
        }

        return  timeStr
    }


    private fun startTimer() {
        countDownTimer = MyCountDownTimer(countTime, 1000)
        countDownTimer!!.start()
    }

    private fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }


//    fun matchTime(timeStr: String): Boolean {
//        val regex = "^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])\$"
//        val p = Pattern.compile(regex)
//        p.matcher(timeStr)
//        return p.matcher(timeStr).matches()
//
//    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTimer()
    }
}