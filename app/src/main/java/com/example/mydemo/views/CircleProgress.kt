package com.nader.tysmart.ui.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


/**
 *Date: 2020/8/25
 *author: hxc
 * 空调温度面板
 */
class CircleProgress : View {

    private var mContext : Context? = null

    //是否是初始化，减少表盘的刷新
    private var isInit = true
    //view 的宽高
    private var mWidth = 0
    private var mHeight = 0
    /** 起始角度*/
    private val  startAngle = 150f
    /** 绘制过的角度*/
    private val  sweepAngle = 240f
    /** 每一份角度*/
    private var mAngle:Double = 0.0

    //距离边界10 防止边线绘制不全
    private var mSpace = 10
    //半径
    private var mRadius = 0
    //圆心
    private var circlePoint = PointF()
    //灰色画笔
    private val mGrayPaint = Paint()
    //圆弧所在矩形区域
    private var mCircleRectF = RectF()
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
    //最大值
    private var maxValue = 30
    //最小值
    private var minvalue = 10
    //当前值
    private var currentValue = minvalue
    //室温
    private var roomValue = 0
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

        mGrayPaint.isAntiAlias = true
        mGrayPaint.strokeWidth = 1f
        mGrayPaint.color = Color.parseColor("#88FFFFFF")
        mGrayPaint.style = Paint.Style.STROKE

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
        //圆心
        circlePoint.x = mWidth.toFloat() / 2
        circlePoint.y = mHeight.toFloat() / 2
        //view区域
        mCircleRectF.left = mSpace.toFloat()
        mCircleRectF.top = mSpace.toFloat()
        mCircleRectF.right = (mWidth - mSpace.toFloat())
        mCircleRectF.bottom = (mHeight - mSpace.toFloat())

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(isInit) {//表盘只绘制一遍
            isInit
            drawGrayArc(canvas)
        }

        drawWhiteArc(canvas)

        drawCenterText(canvas)

        drawBottomText(canvas)

    }
    //绘制表盘
    private fun drawGrayArc(canvas: Canvas){
        //圆弧
        canvas.drawArc(mCircleRectF,startAngle,sweepAngle,false,mGrayPaint)
        //表盘
        for(i in 0 until (lineCount+1)){
            val cosAngle =
                cos(Math.toRadians((360 - sweepAngle) / 2 + mAngle * i))
                    .toFloat()
            val sinAngle =
                sin(Math.toRadians((360 - sweepAngle) / 2 + mAngle * i))
                    .toFloat()

            canvas.drawLine(
                circlePoint.x - (mRadius - 5) * sinAngle,
                circlePoint.y + (mRadius - 5) * cosAngle,
                circlePoint.x - (mRadius - 15) * sinAngle,
                circlePoint.y + (mRadius - 15) * cosAngle,
                mGrayPaint)
        }
        //y的偏移量 （加号减号往下移）
        val y_ = 25

        //减号
        val startCosAngle =cos(Math.toRadians((360 - sweepAngle) / 2 + mAngle * 0)).toFloat()
        val startSinAngle =sin(Math.toRadians((360 - sweepAngle) / 2 + mAngle * 0)).toFloat()
        var startPoint = PointF(circlePoint.x - (mRadius - 5) * startSinAngle,circlePoint.y + (mRadius - 5) * startCosAngle)

        canvas.drawLine(startPoint.x,startPoint.y + y_,startPoint.x + buttonRadius,startPoint.y + y_,mWhitePaint)
        // 减号的中心
        mButtonMinusPoint.x = startPoint.x + buttonRadius/2
        mButtonMinusPoint.y = startPoint.y + y_

        //加号
        val endCosAngle =cos(Math.toRadians((360 - sweepAngle) / 2 + mAngle * lineCount)).toFloat()
        val endSinAngle =sin(Math.toRadians((360 - sweepAngle) / 2 + mAngle * lineCount)).toFloat()
        var endPoint = PointF(circlePoint.x - (mRadius - 5) * endSinAngle,circlePoint.y + (mRadius - 5) * endCosAngle)

        canvas.drawLine(endPoint.x - buttonRadius,endPoint.y + y_,endPoint.x,endPoint.y + y_,mWhitePaint)
        canvas.drawLine(endPoint.x - buttonRadius/2,endPoint.y + y_ - buttonRadius/2,endPoint.x - buttonRadius/2,endPoint.y + y_ + buttonRadius/2,mWhitePaint)
        // 加号的中心
        mButtonAddPoint.x = endPoint.x - buttonRadius/2
        mButtonAddPoint.y = endPoint.y + y_

    }

    private fun drawWhiteArc(canvas: Canvas){
        lastIndex = currentIndex
        //进度圆弧
        mWhitePaint.style = Paint.Style.STROKE
        canvas.drawArc(mCircleRectF,startAngle,(mAngle * currentIndex).toFloat(),false,mWhitePaint)

        val currentCosAngle =cos(Math.toRadians((360 - sweepAngle) / 2 + mAngle * currentIndex)).toFloat()
        val currentSinAngle =sin(Math.toRadians((360 - sweepAngle) / 2 + mAngle * currentIndex)).toFloat()
        var currentPoint = PointF(circlePoint.x - mRadius * currentSinAngle,circlePoint.y + mRadius * currentCosAngle)
        mWhitePaint.style = Paint.Style.FILL
        canvas.drawCircle(currentPoint.x,currentPoint.y,10f,mWhitePaint)

    }
    //绘制中间温度（目标温度）
    private fun drawCenterText(canvas: Canvas){

        mWhiteTextPaint.textSize = centerTextSize
        val textBounds = Rect()
        val textW: Float = mWhiteTextPaint.measureText("$currentValue")
        //计算文字高度
        mWhiteTextPaint.getTextBounds("田", 0, 1, textBounds)
        val textH: Float = textBounds.height().toFloat()

        canvas.drawText("$currentValue",circlePoint.x - textW/2,circlePoint.y + textH / 5,mWhiteTextPaint)
        var du = "℃"
        mWhiteTextPaint.textSize = 18f
        canvas.drawText(du,circlePoint.x + textW/2 + 5,circlePoint.y - textH * 3 / 5,mWhiteTextPaint)


    }
    //绘制底部温度（当前温度）
    private fun drawBottomText(canvas: Canvas){

        var bottomText = "室温$roomValue℃"
        val textBounds = Rect()
        val textW: Float = mGrayTextPaint.measureText(bottomText)
        //计算文字高度
        mGrayTextPaint.getTextBounds("田", 0, 1, textBounds)
        val textH: Float = textBounds.height().toFloat()

        canvas.drawText(bottomText,circlePoint.x - textW/2,circlePoint.y + mRadius / 2 + textH + 10,mGrayTextPaint)

    }

    fun setValue(value: Int,roomValue:Int){
        this.currentValue = value
        this.roomValue = roomValue
        var index = lineCount.toFloat() *(currentValue - minvalue) / (maxValue - minvalue)
        startAnim(index)
    }

    //更新温度
    fun updateValue(value:Int){
        this.currentValue = value
//        currentIndex = lineCount.toFloat() *(currentValue - minvalue) / (maxValue - minvalue)
        var index = lineCount.toFloat() *(currentValue - minvalue) / (maxValue - minvalue)
        startAnim(index)
    }
    //动画效果
    private fun startAnim(index:Float){
        val anim = ValueAnimator.ofFloat(lastIndex, index)
        anim.duration = 300
        anim.addUpdateListener { animation ->
            currentIndex = animation.animatedValue as Float
            invalidate()

        }
        anim.start()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)

        //处理拖动事件
        //处理拖动事件
        val currentX = event.x
        val currentY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                if(checkAdd(currentX,currentY)){
                    if(currentValue < maxValue){
                        updateValue(currentValue + 1)
                    }
                }
                if(checkMinus(currentX,currentY)){
                    if(currentValue > minvalue){
                        updateValue(currentValue - 1)
                    }
                }
            }
            MotionEvent.ACTION_MOVE ->{

            }
            MotionEvent.ACTION_UP ->{

            }

        }
        return true
    }


    private fun calDistance(x1: Float,y1: Float,x2: Float,y2: Float): Float {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2).toDouble()).toFloat()
    }
    private fun checkMinus(currentX: Float,currentY: Float): Boolean {
        val distance: Float = calDistance(currentX, currentY, mButtonMinusPoint.x, mButtonMinusPoint.y)
        return distance < 1.2 * buttonRadius
    }

    private fun checkAdd(currentX: Float,currentY: Float): Boolean {
        val distance: Float = calDistance(currentX, currentY, mButtonAddPoint.x, mButtonAddPoint.y)
        return distance < 1.2 * buttonRadius
    }
}