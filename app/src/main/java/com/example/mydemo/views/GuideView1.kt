package com.example.mydemo.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*


/**
 *Date: 2020/8/25
 *author: hxc
 * 空调温度面板
 */
class GuideView1 : View {

    private var mContext : Context? = null

    //view 的宽高
    private var mWidth = 0
    private var mHeight = 0
//    /** 起始角度*/
//    private var  startAngle = 0f
    /** 绘制过的角度*/
    private val  sweepAngle = 90f
    /** 每一份角度*/
    private var mAngle:Double = 0.0

    //距离边界10 防止边线绘制不全
    private var mSpace = 10
    //半径
    private var mRadius = 0
    //半径
    private var mSmallRadius = 0
    //圆心
    private var circlePoint = PointF()
    //中心圆画笔
    private val mPanPaint = Paint()
    //圆弧画笔
    private val mArcPaint = Paint()
    //圆弧所在矩形区域
    private var mArcRectF = RectF()
    //刻度
    private val lineCount = 50
    //白色画笔
    private val mWhitePaint = Paint()
    //灰色文字画笔
    private val mGrayTextPaint = Paint()
    //白色文字画笔
    private val mWhiteTextPaint = Paint()
    //底部文字大小
    private var bottomTextSize = 25f
    //中心文字大小
    private var centerTextSize = 80f
    //加号减号 按钮半径
    private val buttonRadius = 26
    //减号中心 用来判断是否点击部位
    private var mButtonMinusPoint = PointF()
    //加号中心 用来判断是否点击部位
    private var mButtonAddPoint = PointF()
    //当前的下标
    private var currentIndex = 0f
    //上次的下标
    private var lastIndex = 0f// 保存记录，用于动画

    constructor(mContext: Context) : this(mContext,null)
    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs,0)
    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(mContext,attrs,defStyleAttr){
        this.mContext = mContext
    }

    init {
        mAngle = sweepAngle.toDouble() / lineCount

        mPanPaint.isAntiAlias = true
        mPanPaint.strokeWidth = 1f
        mPanPaint.color = Color.parseColor("#66F2F2F2")
        mPanPaint.style = Paint.Style.FILL

        mArcPaint.isAntiAlias = true
        mArcPaint.strokeWidth = 20f
        mArcPaint.color = Color.parseColor("#66F2F2F2")
        mArcPaint.style = Paint.Style.STROKE

        mWhitePaint.isAntiAlias = true
        mWhitePaint.strokeWidth = 3f
        mWhitePaint.color = Color.parseColor("#FFFFFFFF")
        mWhitePaint.style = Paint.Style.STROKE

        mGrayTextPaint.isAntiAlias = true
        mGrayTextPaint.color = Color.parseColor("#88FFFFFF")
        mGrayTextPaint.textSize = bottomTextSize


        mWhiteTextPaint.isAntiAlias = true
        mWhiteTextPaint.color = Color.parseColor("#FFFFFFFF")
        mWhiteTextPaint.textSize = centerTextSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        var imgSize = if(width > height) height else width
        setMeasuredDimension(imgSize,imgSize)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        mRadius = mWidth / 2 - mSpace
        mSmallRadius = mRadius * 2 / 3
        //圆心
        circlePoint.x = mWidth.toFloat() / 2
        circlePoint.y = mHeight.toFloat() / 2
        //view区域
        mArcRectF.left = mSpace.toFloat()
        mArcRectF.top = mSpace.toFloat()
        mArcRectF.right = (mWidth - mSpace.toFloat())
        mArcRectF.bottom = (mHeight - mSpace.toFloat())

        mArcPaint.strokeWidth = 20f//mRadius.toFloat() / 3

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawPan(canvas)

        drawTouchPan(canvas)
    }
    //绘制表盘
    private fun drawPan(canvas: Canvas){

        canvas.drawCircle(circlePoint.x,circlePoint.y,mSmallRadius.toFloat() - 5,mPanPaint)
        //圆弧
        for(i in 0 until 4){
            val path = Path()
            val x_1 = circlePoint.x + mRadius * cos((i * 90.toFloat() - 45) * PI / 180).toFloat()
            val y_1 = circlePoint.y + mRadius * sin((i * 90.toFloat() - 45) * PI / 180).toFloat()
            path.moveTo(x_1,y_1)
            path.arcTo(mArcRectF,i * 90.toFloat() + 1,90.toFloat(),false)
            val x_2 = circlePoint.x + mSmallRadius * cos((i * 90.toFloat() + 90 - 45) * PI / 180).toFloat()
            val y_2 = circlePoint.y + mSmallRadius * sin((i * 90.toFloat() + 90 - 45) * PI / 180).toFloat()
            path.lineTo(x_2,y_2)
            path.arcTo(mArcRectF,i * 90.toFloat() + 1,90.toFloat(),false)
            val x_3 = circlePoint.x + mSmallRadius * cos((i * 90.toFloat() - 45) * PI / 180).toFloat()
            val y_3 = circlePoint.y + mSmallRadius * sin((i * 90.toFloat() - 45) * PI / 180).toFloat()
            path.lineTo(x_3,y_3)
            canvas.drawPath(path,mPanPaint)

//            if(i == 0){
//                canvas.drawArc(mArcRectF,i * 90.toFloat() + 1,90.toFloat(),true,mArcPaint)
//            }
//            if(i == 1){
//                canvas.drawArc(mArcRectF,i * 90.toFloat() + 1,90.toFloat(),true,mArcPaint)
//            }
//            if(i == 2){
//                canvas.drawArc(mArcRectF,i * 90.toFloat() + 1,90.toFloat(),true,mArcPaint)
//            }
        }

    }

    private fun drawTouchPan(canvas: Canvas){

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)

        return true
    }

    /**
     * 计算两点之间的距离
     * */
    private fun calDistance(x1: Float,y1: Float,x2: Float,y2: Float): Float {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2).toDouble()).toFloat()
    }
    /**
     * 检查是否点击减号
     */
    private fun checkMinus(currentX: Float,currentY: Float): Boolean {
        val distance: Float = calDistance(currentX, currentY, mButtonMinusPoint.x, mButtonMinusPoint.y)
        return distance < 1.2 * buttonRadius
    }
    /**
     * 检查是否点击加号
     */
    private fun checkAdd(currentX: Float,currentY: Float): Boolean {
        val distance: Float = calDistance(currentX, currentY, mButtonAddPoint.x, mButtonAddPoint.y)
        return distance < 1.2 * buttonRadius
    }


    /**
     * 判断该点是否在弧线上（附近）
     */
    private fun checkOnArc(currentX: Float, currentY: Float): Boolean {
        val distance = calDistance(currentX, currentY, circlePoint.x, circlePoint.y)
        val degree = calDegreeByPosition(currentX, currentY)
        // 考虑到白色圆球半径为10 这里把距离加大
        return distance > mRadius - 30 && distance < mRadius + 30 && degree >= -10 && degree <= sweepAngle + 10
    }

    /**
     * 根据当前位置，计算出进度条已经转过的角度。
     */
    private fun calDegreeByPosition(currentX: Float,currentY: Float): Float {
        var a1 = atan(1.0f * (circlePoint.x - currentX) / (currentY - circlePoint.y).toDouble()) * 180f / Math.PI
        if (currentY < circlePoint.y) {
            a1 += 180f
        } else if (currentY > circlePoint.y && currentX > circlePoint.x) {
            a1 += 360f
        }
        return a1.toFloat() - (360 - sweepAngle) / 2
    }



}