package com.example.mydemo.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.mydemo.util.Utils

/**
 *author: hxc
 * 渐进色进度
 */
class MyCircleProgress : View {

    private var backColor = Color.parseColor("#D9D9D9")//背景色
    private var foreColor = intArrayOf(Color.parseColor("#FFE9D3A6"), Color.parseColor("#FFE9B57E")) //前景色
    private var value = 0//当前值
    private var sweepAngle = 0f//弧度
    private var circleWidth = 5f//圆环宽度

    private var circle_r = 20f//半径
    private var circle_center = Point(0, 0)//圆心坐标

    private val mBgPaint = Paint()

    private val mFgPaint = Paint()

    //    private var mSweepGradient: SweepGradient? = null
    private var mSweepGradient: LinearGradient? = null


    private var mContext: Context? = null

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context

        mBgPaint.strokeWidth = circleWidth
        mBgPaint.isAntiAlias = true
        mBgPaint.style = Paint.Style.STROKE
        mBgPaint.isDither = true

        mFgPaint.strokeWidth = circleWidth
        mFgPaint.isAntiAlias = true
        mFgPaint.style = Paint.Style.STROKE
        mFgPaint.isDither = true
    }

    fun setValue(value: Int) {
//        Utils.log("111","value = $value")
        this.value = value
        sweepAngle = value.toFloat() * 360 / 100
        invalidate()
    }

    fun setBgColor(backColor: Int) {
        this.backColor = backColor
        invalidate()
    }

    fun setFgColor(foreColor: IntArray) {
        this.foreColor = foreColor
        invalidate()
    }

    fun setWidth(circleWidth: Float) {
        this.circleWidth = circleWidth
        mBgPaint.strokeWidth = circleWidth
        mFgPaint.strokeWidth = circleWidth
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)

        if (w > 0 && h > 0) {
            if (w < h) {
                circle_r = w.toFloat() / 2 - Utils.dpToPx(mContext!!, 5f)
            } else {
                circle_r = h.toFloat() / 2 - Utils.dpToPx(mContext!!, 5f)
            }
            circle_center = Point(w / 2, h / 2)

//            mSweepGradient = SweepGradient(w.toFloat() / 2, h.toFloat() / 2, foreColor, null)
            mSweepGradient = LinearGradient(
                0.toFloat(),
                0.toFloat(),
                0.toFloat(),
                w.toFloat(),
                foreColor,
                null,
                Shader.TileMode.MIRROR
            )
//            val matrix = Matrix()
//            matrix.setRotate(180f, w.toFloat() / 2, h.toFloat() / 2)
//            mSweepGradient!!.setLocalMatrix(matrix)
        }

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //绘制 底层圆环
        var oval = RectF(
            circle_center.x - circle_r,
            circle_center.y - circle_r,
            circle_center.x + circle_r,
            circle_center.y + circle_r
        )
        mBgPaint.color = backColor
        canvas?.drawArc(oval, 0.toFloat(), 360.toFloat(), false, mBgPaint)
        //绘制 上层圆环
//        var oval = RectF(mPadding,mPadding,mWidth.toFloat() -mPadding,mHeight.toFloat() -mPadding)
        mFgPaint.strokeCap = Paint.Cap.ROUND
        mFgPaint.shader = mSweepGradient
        canvas?.drawArc(oval, 270.toFloat(), sweepAngle, false, mFgPaint)

    }


}
