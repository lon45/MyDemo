package com.example.mydemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.views.MyRotateAnimation
import kotlinx.android.synthetic.main.activity_people.*

/**
 * @author hxc
 *
 *
 * 自诊界面
 */
class PeopleActivity : AppCompatActivity(), View.OnClickListener, MyRotateAnimation.InterpolatedTimeListener, View.OnTouchListener {

    private var enableRefresh: Boolean = false
    private var isFront = true

    private var isMan = true

    private var bmp: Bitmap? = null

    internal var animationLeftIn: Animation? = null
    internal var animationLeftOut: Animation? = null
    internal var animationRightIn: Animation? = null
    internal var animationRightOut: Animation? = null

    internal var animListener: Animation.AnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {
            // TODO Auto-generated method stub

        }

        override fun onAnimationRepeat(animation: Animation) {
            // TODO Auto-generated method stub

        }

        override fun onAnimationEnd(animation: Animation) {
            // TODO Auto-generated method stub
            if (!isMan) {
                rl_manbody_self.visibility = View.GONE
                rl_womanbody_self.visibility = View.VISIBLE
            } else {
                rl_manbody_self.visibility = View.VISIBLE
                rl_womanbody_self.visibility = View.GONE
            }

        }
    }

    private val colors = intArrayOf(-0x10000, -0xff0100, -0xffff01)

    private var firstInput = true

    private var xNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)

        initView()
    }

    private fun initView() {
        // TODO Auto-generated method stub

        ll_return_self.setOnClickListener(this)

        iv_manbody_front_self.setOnTouchListener(this)
        iv_manbody_back_self.setOnTouchListener(this)

        iv_womanbody_front_self.setOnTouchListener(this)
        iv_womanbody_back_self.setOnTouchListener(this)

        ll_turn_self.setOnClickListener(this)
        ll_gender_self.setOnClickListener(this)

        if (isMan) {
            rl_manbody_self.visibility = View.VISIBLE
            rl_womanbody_self.visibility = View.GONE
        } else {
            rl_manbody_self.visibility = View.GONE
            rl_womanbody_self.visibility = View.VISIBLE
        }
        showSwtichLayout(isFront)
    }

    private fun startTurnAnimator() {

        enableRefresh = true
        var rotateAnim: MyRotateAnimation? = null
        if (isMan) {
            val cX = rl_manbody_self!!.width / 2.0f
            val cY = rl_manbody_self!!.height / 2.0f

            rotateAnim = MyRotateAnimation(cX, cY, MyRotateAnimation.ROTATE_DECREASE)
            if (rotateAnim != null) {
                rotateAnim!!.setInterpolatedTimeListener(this)
                rotateAnim!!.fillAfter = true
                rl_manbody_self!!.startAnimation(rotateAnim)
            }
        } else {
            val cX = rl_womanbody_self!!.width / 2.0f
            val cY = rl_womanbody_self!!.height / 2.0f

            rotateAnim = MyRotateAnimation(cX, cY, MyRotateAnimation.ROTATE_DECREASE)
            if (rotateAnim != null) {
                rotateAnim!!.setInterpolatedTimeListener(this)
                rotateAnim!!.fillAfter = true
                rl_womanbody_self!!.startAnimation(rotateAnim)
            }
        }
    }

    private fun startGenderAnimator() {

        animationLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in)
        animationLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out)
        animationRightIn = AnimationUtils.loadAnimation(this, R.anim.push_right_in)
        animationRightOut = AnimationUtils.loadAnimation(this, R.anim.push_right_out)
        animationLeftIn!!.setAnimationListener(animListener)
        animationLeftOut!!.setAnimationListener(animListener)
        animationRightIn!!.setAnimationListener(animListener)
        animationRightOut!!.setAnimationListener(animListener)
        if (!isMan) {
            rl_manbody_self!!.startAnimation(animationLeftOut)
            rl_womanbody_self!!.startAnimation(animationLeftIn)
        } else {
            rl_manbody_self!!.startAnimation(animationRightIn)
            rl_womanbody_self!!.startAnimation(animationRightOut)
        }

    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub

        if (!Utils.isFastDoubleClick()) {
            return
        }
        when(v.id){
            R.id.ll_return_self ->{
                finish()
            }
            R.id.ll_turn_self ->{
                isFront = isFront xor true
                startTurnAnimator()
            }
            R.id.ll_gender_self ->{
                isMan = isMan xor true
                isFront = true
                startGenderAnimator()
            }

        }

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            finish()
            return false
        }
        return false
    }

    private fun showSwtichLayout(isTrue: Boolean) {
        if (isMan) {
            if (isTrue) {
                iv_manbody_front_self!!.visibility = View.VISIBLE
                iv_manbody_back_self!!.visibility = View.GONE
            } else {
                iv_manbody_back_self!!.visibility = View.VISIBLE
                iv_manbody_front_self!!.visibility = View.GONE
            }
        } else {
            if (isTrue) {
                iv_womanbody_front_self!!.visibility = View.VISIBLE
                iv_womanbody_back_self!!.visibility = View.GONE
            } else {
                iv_womanbody_back_self!!.visibility = View.VISIBLE
                iv_womanbody_front_self!!.visibility = View.GONE
            }
        }

    }

    override fun interpolatedTime(interpolatedTime: Float) {
        // 监听到翻转进度过半时，更新txtNumber显示内容。
        if (enableRefresh && interpolatedTime > 0.5f) {
            enableRefresh = false
            showSwtichLayout(isFront)
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        // Log.i("111", "event = " + event.getX() + "|" + event.getY());

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> getIndex(event)
            MotionEvent.ACTION_MOVE -> {
            }

            MotionEvent.ACTION_UP -> {
            }

            else -> {
            }
        }

        return false
    }

    private fun getIndex(event: MotionEvent): Int {
        var index = -1
        val x = if (event.rawX - xNum < 0) 0 else (event.rawX - xNum).toInt()
        val y = event.rawY.toInt()
        Log.e("getIndex", "$x =============$y")
        if (bmp!!.width > x && bmp!!.height > y) {
            val color = bmp!!.getPixel(x, y)

            for (i in colors.indices) {
                if (color == colors[i]) {
                    index = i
                    break
                }
            }
            Log.i("111", "$color  index$index")
        }

        return index
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus)
        if (firstInput) {
            firstInput = false

            val bm = BitmapFactory.decodeResource(resources, R.mipmap.dl_second_icon_man_icon_man_front1)
            val bmW = bm.width
            val bmH = bm.height

            val w = rl_manbody_self!!.width
            val h = rl_manbody_self!!.height
            Log.i("111", "h = $h|$bmH")
            val percention = h.toFloat() / bmH.toFloat()
            Log.i("111", "percention = $percention")
            val matrix = Matrix()
            matrix.postScale(percention, percention)
            bmp = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
            xNum = (w - bmp!!.width) / 2
            Log.i("111", "tempwh = " + bmp!!.width + "|" + bmp!!.height)

        }
    }

    companion object {

        fun resizeImage(bitmap: Bitmap, w: Int, h: Int): Bitmap {

            val width = bitmap.width
            val height = bitmap.height

            val scaleWidth = w.toFloat() / width
            val scaleHeight = h.toFloat() / height

            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)

            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)

        }
    }

}

