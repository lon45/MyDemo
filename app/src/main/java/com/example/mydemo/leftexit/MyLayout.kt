package com.example.mydemo.leftexit

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


/**
 *Date: 2022/4/27
 *author: hxc
 */
class MyLayout : FrameLayout {
    private var mActivity: Activity? = null
    private enum class TouchState{
        LEFT,TOP,RIGHT,BOTTOM,CENTER
    }
    private var _x = 0f
    private var _y = 0f
    private var mTouchState = TouchState.CENTER

    private var scrollValue = 0

    constructor(mContext: Context) : this(mContext, null)
    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)
    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        mContext,
        attrs,
        defStyleAttr
    )

    fun bind(activity: Activity) {
        mActivity = activity
        val decorView = mActivity!!.window.decorView as ViewGroup
        val child: View = decorView.getChildAt(0)
        decorView.removeView(child)
        addView(child)
        decorView.addView(this) //把整个布局添加的窗口的ViewGroup中

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                _x = ev.x
                _y = ev.y
                if(_y < 10 && if(mLeftScrollListener == null) false else mLeftScrollListener!!.canTopScroll()){
                    mTouchState = TouchState.TOP
                    return true
                }
                if(_y > 470 && if(mLeftScrollListener == null) false else mLeftScrollListener!!.canBottomScroll()){
                    mTouchState = TouchState.BOTTOM
                    return true
                }
                if(_x < 10 && if(mLeftScrollListener == null) false else mLeftScrollListener!!.canLeftScroll()){
                    mTouchState = TouchState.LEFT
                    return true
                }

            }
            MotionEvent.ACTION_MOVE -> {
                if(mTouchState == TouchState.TOP && (ev.y - _y) > 10){//下滑
                    mTouchState = TouchState.CENTER
                    mLeftScrollListener?.topScrollFinish()
                }
                if(mTouchState == TouchState.BOTTOM){
                    scrollValue = when {
                        _y - ev.y  < 0 -> {
                            0
                        }
                        _y - ev.y  > this.height -> {
                            this.height
                        }
                        else -> {
                            (_y - ev.y).toInt()
                        }
                    }
                    this.scrollY = scrollValue
                }
                if(mTouchState == TouchState.LEFT){//&& (ev.x - _x) > 20
                    scrollValue = when {
                        ev.x - _x < 0 -> {
                            0
                        }
                        ev.x - _x > this.width -> {
                            -this.width
                        }
                        else -> {
                            -(ev.x - _x).toInt()
                        }
                    }
                    this.scrollX = scrollValue
                }
            }
            MotionEvent.ACTION_UP -> {
                when(mTouchState){
                    TouchState.LEFT -> {
                        if(scrollValue > -this.width / 2){
                            this.scrollX = 0
                            mLeftScrollListener?.leftScrollFinish()
                        } else {
                            this.scrollX = this.width
                        }
                    }
                    TouchState.BOTTOM -> {
                        if(scrollValue > this.height / 3){
                            this.scrollY = this.height
                            mLeftScrollListener?.bottomScrollFinish()
                        } else {
                            this.scrollY = 0
                        }
                    }
                }
                mTouchState = TouchState.CENTER

            }
        }
        return super.dispatchTouchEvent(ev)
    }


//    private fun canDownSmooth():Boolean{
//        return when(this.javaClass){
//            SettingActivity::class.java -> {
//                false
//            }
//            else -> {
//                true
//            }
//        }
//        return true
//    }

//    private fun downSmooth(){
//        if(!canDownSmooth()){
//            return
//        }
//        val intent = Intent(this,SettingActivity::class.java)
//        startActivity(intent)
//        overridePendingTransition(R.anim.anim_in,R.anim.anim_no)
//        ActivityManager.finishActivityByClass(SleepActivity::class.java)
//    }

//    private fun canTopSmooth():Boolean{
//        return when(this.javaClass){
//            HomeActivity::class.java,LoginActivity::class.java,InitActivity::class.java -> {
//                false
//            }
//            else -> {
//                true
//            }
//        }
//        return true
//    }

//    private fun topSmooth(){
//        if(!canTopSmooth()){
//            return
//        }
//        when(this.javaClass){
//            SettingActivity::class.java -> {
//                exitActivity()
//                overridePendingTransition(R.anim.anim_no,R.anim.anim_out)
//            }
//            HomeActivity::class.java,LoginActivity::class.java,InitActivity::class.java -> {
//
//            }
//            else -> {
//                ActivityManager.cleanActivityByClass(HomeActivity::class.java)
//            }
//        }

//    }

//    private fun canLeftSmooth():Boolean{
//        return when(this.javaClass){
//            HomeActivity::class.java,LoginActivity::class.java,InitActivity::class.java -> {
//                false
//            }
//            else -> {
//                true
//            }
//        }
//        return true
//    }

//    private fun LeftSmooth(){
//        if(!canLeftSmooth()){
//            return
//        }
//        when(this.javaClass){
//            SettingActivity::class.java -> {
//                exitActivity()
//                overridePendingTransition(R.anim.anim_no,R.anim.anim_out)
//            }
//            else -> {
//                exitActivity()
//            }
//        }

//    }

    private var mLeftScrollListener:ScrollListener? = null

    fun setListener(leftScrollListener:ScrollListener){
        mLeftScrollListener = leftScrollListener
    }

    interface ScrollListener{
        fun canTopScroll():Boolean
        fun canLeftScroll():Boolean
        fun canBottomScroll():Boolean
        fun topScrollFinish()
        fun leftScrollFinish()
        fun bottomScrollFinish()
    }

}