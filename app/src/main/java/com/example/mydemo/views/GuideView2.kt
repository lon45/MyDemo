package com.example.mydemo.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.mydemo.R

/**
 *Date: 2020/8/25
 *author: hxc
 * 摄像头方向盘
 */
class GuideView2 : View {
    private val bitmapRes = intArrayOf(
        R.mipmap.icon_arrow_bottom_green,
        R.mipmap.icon_arrow_left_green,
        R.mipmap.icon_arrow_top_green,
        R.mipmap.icon_arrow_right_green
    )

    private var mContext: Context? = null

    //view 的宽高
    private var mWidth = 0
    private var mHeight = 0

    //距离边界10 防止边线绘制不全
    private var mSpace = 10

    //大圆半径
    private var mRadius = 0

    //小圆半径
    private var mSmallRadius = 0

    //圆心
    private var circlePoint = PointF()

    //中心圆画笔
    private val mPanPaint = Paint()

    //圆弧画笔
    private val mArcPaint = Paint()

    //圆弧所在矩形区域
    private var mArcRectF = RectF()

    //内圆弧所在矩形区域
    private var mSmallArcRectF = RectF()

    private var mRegionList = arrayListOf(Region(),Region(),Region(),Region())

    //当前的下标
    private var currentIndex = -1

    constructor(mContext: Context) : this(mContext, null)
    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)
    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        mContext,
        attrs,
        defStyleAttr
    ) {
        this.mContext = mContext
    }

    init {
        mPanPaint.isAntiAlias = true
        mPanPaint.strokeWidth = 1f
        mPanPaint.color = Color.parseColor("#66F2F2F2")
        mPanPaint.style = Paint.Style.FILL

        mArcPaint.isAntiAlias = true
        mArcPaint.strokeWidth = 1f
        mArcPaint.color = Color.parseColor("#66F2F2F2")
        mArcPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        var imgSize = if (width > height) height else width
        setMeasuredDimension(imgSize, imgSize)

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
        mArcRectF.left = circlePoint.x - (mRadius)
        mArcRectF.top = circlePoint.y - (mRadius)
        mArcRectF.right = circlePoint.x + (mRadius)
        mArcRectF.bottom = circlePoint.y + (mRadius)
        //内圆弧区域
        mSmallArcRectF.left = circlePoint.x - (mSmallRadius)
        mSmallArcRectF.top = circlePoint.y - (mSmallRadius)
        mSmallArcRectF.right = circlePoint.x + (mSmallRadius)
        mSmallArcRectF.bottom = circlePoint.y + (mSmallRadius)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPan(canvas)
    }

    //绘制表盘
    private fun drawPan(canvas: Canvas) {
        Log.i("111111111111","${mRegionList.size}")
        canvas.drawCircle(circlePoint.x, circlePoint.y, mSmallRadius.toFloat() - 5, mPanPaint)
        //Touch圆弧
        var bitmap: Bitmap? = null
        for (i in 0 until 4) {
            val path = Path()
            val startAngle = 45 + (i - 1) * 90.toFloat() + 2
            val sweepAngle = 90.toFloat() - 2
            path.addArc(mSmallArcRectF, startAngle, sweepAngle)
            path.arcTo(mArcRectF, startAngle + sweepAngle, -sweepAngle)
            path.close()
            canvas.drawPath(path, mArcPaint)

            bitmap = BitmapFactory.decodeResource(resources, bitmapRes[i])
            if(i == 0){//bottom
                canvas.drawBitmap(bitmap, circlePoint.x - bitmap.width / 2, circlePoint.y + mRadius - bitmap.height*2, mArcPaint)
            }
            if(i == 1){//left
                canvas.drawBitmap(bitmap, circlePoint.x - mRadius + bitmap.width, circlePoint.y - bitmap.height/2, mArcPaint)
            }
            if(i == 2){//top
                canvas.drawBitmap(bitmap, circlePoint.x - bitmap.width / 2, circlePoint.y - mRadius + bitmap.height, mArcPaint)
            }
            if(i == 3){//right
                canvas.drawBitmap(bitmap, circlePoint.x + mRadius - bitmap.width * 2, circlePoint.y - bitmap.height/2, mArcPaint)
            }

            bitmap.recycle()
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)

        return true
    }
}