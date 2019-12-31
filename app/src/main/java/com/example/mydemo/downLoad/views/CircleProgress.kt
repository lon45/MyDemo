package com.example.mydemo.downLoad.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.mydemo.util.Utils

/**
 *Date: 2019/12/3
 *author: hxc
 */
class CircleProgress : View {

    private var backColor = Color.parseColor("#D9D9D9")//背景色
    private var foreColor = Color.parseColor("#C36F27")//前景色
    private var value = 0//当前值
    private var sweepAngle = 0f//弧度
    private var circleWidth = 5f//圆环宽度

    private var circle_r = 20f//半径
    private var circle_center = Point(0, 0)//圆心坐标

    private val mPaint = Paint()


    private var mContext: Context? = null

    constructor (context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context

        mPaint.strokeWidth = circleWidth
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE

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

    fun setFgColor(backColor: Int) {
        this.foreColor = foreColor
        invalidate()
    }

    fun setWidth(circleWidth: Float) {
        this.circleWidth = circleWidth
        invalidate()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)

        if (w > 0 && h > 0) {
            if (w < h) {
                circle_r = w.toFloat() / 2 - Utils.dpToPx(mContext!!,5f)
            } else {
                circle_r = h.toFloat() / 2 - Utils.dpToPx(mContext!!,5f)
            }
            circle_center = Point(w / 2, h / 2)

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
        mPaint.color = backColor
        canvas?.drawArc(oval, 0.toFloat(), 360.toFloat(), false, mPaint)
        //绘制 上层圆环
//        var oval = RectF(mPadding,mPadding,mWidth.toFloat() -mPadding,mHeight.toFloat() -mPadding)
        mPaint.color = foreColor
        canvas?.drawArc(oval, 270.toFloat(), sweepAngle, false, mPaint)

    }


}
