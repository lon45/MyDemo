package com.example.mydemo.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import com.example.mydemo.R
import kotlin.math.*

/**
 *Date: 2021/1/13
 *author: hxc
 */
class ColorPickerView: View {

    private val BASE_RADIO = 1.0f //饱和度倍数，0 - 1，饱和度越高颜色越艳丽，可根据具体需求调整

    private var mContext: Context? = null
    private var mBigCircle // 外圈半径
            = 0
    private var mRudeRadius // 可移动小球的半径
            = 0
    private var mCenterColor // 可移动小球的颜色
            = 0
    private val mRudeStrokeWidth = 5
    private var mBitmapBack // 背景图片
            : Bitmap? = null
    private var mPaint // 背景画笔
            : Paint = Paint()
    private var mCenterPaint // 可移动小球画笔
            : Paint = Paint()
    private var mCenterPoint // 中心位置
            : Point? = null
    private var mRockPosition // 小球当前位置
            : Point? = null
    private var mListener // 小球移动的监听
            : OnColorChangedListener? = null
    private var length // 小球到中心位置的距离
            = 0.0
    private var mHue = 0.0 // 0 - 360.0 //色相

    private val mBrightness = 1.0 //0 - 1.0 //亮度

    private var mSaturation = 1.0 // 0 - 1.0 //饱和度

    private val mHSV = FloatArray(3)

    constructor(context: Context):this(context,null) {
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?):this(context,attrs,0) {
    }

    constructor(context: Context?,@Nullable attrs: AttributeSet?,defStyleAttr: Int):super(context, attrs, defStyleAttr){
        mContext = context
        init(attrs!!)
    }

    /**
     * @param attrs
     */
    private fun init(attrs: AttributeSet) {
        // 获取自定义组件的属性
        val types: TypedArray = mContext!!.obtainStyledAttributes(
            attrs,
            R.styleable.color_picker
        )
        try {
            mBigCircle = types.getDimensionPixelOffset(
                R.styleable.color_picker_circle_radius, 100
            )
            mRudeRadius = types.getDimensionPixelOffset(
                R.styleable.color_picker_center_radius, 10
            )
            mCenterColor = types.getColor(
                R.styleable.color_picker_center_color,
                Color.WHITE
            )
        } finally {
            types.recycle() // TypeArray用完需要recycle
        }

        //此处根据UI要求，可使用UI图片代替绘制取色盘
        // 将背景图片大小设置为属性设置的直径
        //mBitmapBack = BitmapFactory.decodeResource(getResources(),
        //       R.drawable.hsb_circle_hard);
        //mBitmapBack = Bitmap.createScaledBitmap(mBitmapBack, mBigCircle * 2,
        //       mBigCircle * 2, false);


        // 中心位置坐标
        mCenterPoint = Point(mBigCircle, mBigCircle)
        mRockPosition = Point(mCenterPoint)
        // 初始化背景画笔和可移动小球的画笔
        mPaint.isAntiAlias = true
        mBitmapBack = createColorWheelBitmap(mBigCircle * 2, mBigCircle * 2)
        mCenterPaint = Paint()
        mCenterPaint.color = mCenterColor
        mCenterPaint.strokeWidth = mRudeStrokeWidth.toFloat()
        mCenterPaint.style = Paint.Style.STROKE
    }


    /**
     * 绘制取色盘，可根据UI需求，使用资源图片代替
     * @param width
     * @param height
     * @return
     */
    private fun createColorWheelBitmap(width: Int, height: Int): Bitmap? {
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val colorCount = 12
        val colorAngleStep = 360 / colorCount
        val colors = IntArray(colorCount + 1)
        val hsv = floatArrayOf(0f, 1f, 1f)
        for (i in colors.indices) {
            hsv[0] = (360 - i * colorAngleStep % 360).toFloat()
            colors[i] = Color.HSVToColor(hsv)
        }
        colors[colorCount] = colors[0]
        val sweepGradient = SweepGradient(width.toFloat() / 2, height.toFloat() / 2, colors, null)
        val radialGradient = RadialGradient(
            width.toFloat() / 2,
            height.toFloat() / 2,
            mBigCircle.toFloat(),
            -0x1,
            0x00FFFFFF,
            Shader.TileMode.CLAMP
        )
        val composeShader =
            ComposeShader(sweepGradient, radialGradient, PorterDuff.Mode.SRC_OVER)
        mPaint.shader = composeShader
        val canvas = Canvas(bitmap)
        canvas.drawCircle(width.toFloat() / 2, height.toFloat() / 2, mBigCircle.toFloat(), mPaint)
        return bitmap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画背景图片
        canvas.drawBitmap(mBitmapBack!!, 0.toFloat(), 0.toFloat(), mPaint)
        // 画中心小球
        canvas.drawCircle(
            mRockPosition!!.x.toFloat(), mRockPosition!!.y.toFloat(), mRudeRadius.toFloat(),
            mCenterPaint
        )
    }

    fun setOnColorChangedListener(listener: OnColorChangedListener) {
        mListener = listener
    }

    // 颜色发生变化的回调接口
    interface OnColorChangedListener {
        fun onColorChange(hsb: FloatArray)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP, MotionEvent.ACTION_MOVE -> {
                length = getLength(
                    event.x, event.y, mCenterPoint!!.x.toFloat(),
                    mCenterPoint!!.y.toFloat()
                )
                if (length <= mBigCircle - mRudeRadius) {
                    mRockPosition!!.set(event.x.toInt(), event.y.toInt())
                } else {
                    mRockPosition = getBorderPoint(
                        mCenterPoint!!, Point(
                            event.x.toInt(), event.y.toInt()
                        ), mBigCircle - mRudeRadius - 5
                    )
                }
                val cX: Float = mCenterPoint!!.x.toFloat()
                val cY: Float = mCenterPoint!!.y.toFloat()
                val pX: Float = event.x
                val pY: Float = event.y
                mHue = getHue(cX, cY, pX, pY)
                if (mHue < 0) mHue += 360.0
                val deltaX = abs(cX - pX).toDouble()
                val deltaY = (cY - pY).toDouble()
                mSaturation = (deltaX * deltaX + deltaY * deltaY).pow(0.5) / mBigCircle * BASE_RADIO
                if (mSaturation <= 0) mSaturation = 0.0
                if (mSaturation >= 1.0) mSaturation = 1.0
            }
            else -> {
            }
        }
        val hue = mHue
        // 360.0,
        val sat = mSaturation
        val brt = mBrightness
        mHSV[0] = hue.toFloat()
        mHSV[1] = sat.toFloat()
        mHSV[2] = brt.toFloat()
        Log.d(
            "niexu",
            "onTouchEvent: mHSV[0] = " + mHSV[0] + "  mHSB[1]=" + mHSV[1] + "  mHSB[2]==" + mHSV[2]
        )
        mListener?.onColorChange(mHSV)
        invalidate()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 视图大小设置为直径
        setMeasuredDimension(mBigCircle * 2, mBigCircle * 2)
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private fun getLength(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): Double {
        return sqrt(
            (x1 - x2.toDouble()).pow(2.0) + (y1 - y2.toDouble()).pow(2.0)
        )
    }

    /**
     * @param a
     * @param b
     * @param cutRadius
     * @return
     */
    private fun getBorderPoint(a: Point, b: Point, cutRadius: Int): Point? {
        val radian = getRadian(a, b)
        return Point(
            a.x + (cutRadius * cos(radian.toDouble())).toInt(), a.x
                    + (cutRadius * sin(radian.toDouble())).toInt()
        )
    }

    /**
     * @param a
     * @param b
     * @return
     */
    private fun getRadian(a: Point, b: Point): Float {
        val lenA: Float = b.x.toFloat() - a.x.toFloat()
        val lenB: Float = b.y.toFloat() - a.y.toFloat()
        val lenC =
            sqrt(lenA * lenA + lenB * lenB.toDouble()).toFloat()
        var ang = acos(lenA / lenC.toDouble()).toFloat()
        ang *= if (b.y < a.y) -1 else 1
        return ang
    }

    /**
     * 计算角度，即HSB中的H
     *
     * @param rockX   rockY 小圆point
     * @param centerX centerY圆心point
     * @return
     */
    private fun getHue(
        centerX: Float,
        centerY: Float,
        rockX: Float,
        rockY: Float
    ): Double {
        var hue = 0.0
        val deltaA = abs(rockX - centerX).toDouble()
        val deltaB = abs(rockY - centerY).toDouble()
        val deltaC = getLength(centerX, centerY, rockX, rockY)
        if (centerX == rockX && centerY == rockY) {
            return 0.0
        }
        if (centerX == rockX) { //在Y轴上
            hue = if (centerY > rockY) {
                90.0
            } else {
                270.0
            }
            return hue
        }
        if (centerY == rockY) { //在X轴上
            hue = if (centerX > rockX) {
                180.0
            } else {
                0.0
            }
            return hue
        }
        if (rockX > centerX && centerY > rockY) { //第一象限
            hue = asin(deltaB / deltaC) * 180 / Math.PI
        } else if (rockX < centerX && rockY < centerY) { //第二象限
            hue = asin(deltaA / deltaC) * 180 / Math.PI + 90
        } else if (rockX < centerX && rockY > centerY) { //第三象限
            hue = asin(deltaB / deltaC) * 180 / Math.PI + 180
        } else if (rockX > centerX && rockY > centerY) { //第四象限
            hue = asin(deltaA / deltaC) * 180 / Math.PI + 270
        }
        Log.d("niexu", "getHue: hue =$hue")
        return hue
    }


    /**
     * 设置被选择的颜色
     *
     * @param color    被选择的颜色
     * @param callback 是否触发OnColorChangedListener
     */
    fun setColor(color: Int) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        mHue = hsv[0].toDouble()
        mHSV[0] = hsv[0]
        mHSV[1] = hsv[1]
        mHSV[2] = hsv[2]
        mListener?.onColorChange(floatArrayOf(mHue.toFloat(), mHSV[1], mHSV[2]))
        invalidate()
    }

}