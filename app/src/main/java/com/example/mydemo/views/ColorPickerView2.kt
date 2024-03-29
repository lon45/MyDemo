package com.example.mydemo.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 *Date: 2021/1/13
 *author: hxc
 *  * 取色器
 * 所有注释单位为dp的全局变量，初始都是dp值，在使用之前会乘上屏幕像素(mDensity)称为px值
 */
class ColorPickerView2 @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {
    internal annotation class PANEL {
        companion object {
            var SAT_VAL = 0
            var HUE = 1
        }
    }

    /**
     * H矩形的宽度（单位：dp）
     */
    private var mHuePanelWidth = 30f

    /**
     * H、SV矩形间的间距（单位：dp）
     */
    private var mPanelSpacing = 10f

    /**
     * 当mode为MeasureSpec.UNSPECIFIED时的首选高度（单位：dp）
     */
    private var mPreferredHeight = 200f

    /**
     * 当mode为MeasureSpec.UNSPECIFIED时的首选宽度（单位：dp）
     */
    private var mPreferredWidth = mPreferredHeight + mHuePanelWidth + mPanelSpacing

    /**
     * SV指示器的半径（单位：dp）
     */
    private var mSVTrackerRadius = 5f

    /**
     * SV指示器的半径（单位：dp）
     */
    private var mHTrackerHeight = 4f

    /**
     * H、SV矩形与父布局的边距（单位：dp）
     */
    private var mRectOffset = 2f

    /**
     * 屏幕密度
     */
    private var mDensity = 1f

    /**
     * 绘制SV的画笔
     */
    private var mSatValPaint: Paint? = null

    /**
     * 绘制SV指示器的画笔
     */
    private var mSatValTrackerPaint: Paint? = null

    /**
     * 绘制H的画笔
     */
    private var mHuePaint: Paint? = null

    /**
     * 绘制H指示器的画笔
     */
    private var mHueTrackerPaint: Paint? = null

    /**
     * 绘制H、SV矩形的边线的画笔
     */
    private var mBorderPaint: Paint? = null

    //H、V着色器
    private var mHueShader: Shader? = null
    private var mValShader: Shader? = null

    //HSV的默认值
    private var mHue = 360f
    private var mSat = 0f
    private var mVal = 0f

    /**
     * 用于显示被选择H的位置的指示器的颜色
     */
    private var mSliderTrackerColor = -0xe3e3e4

    /**
     * H、SV矩形的边框颜色
     */
    private var mBorderColor = -0x919192

    /**
     * 记录上一次被点击的颜色板
     */
    @PANEL
    private var mLastTouchedPanel = PANEL.SAT_VAL

    /**
     * ColorPickerView的padding
     *
     * @return padding（单位：px）
     */
    /**
     * 边距
     */
    var drawingOffset = 0f
        private set

    /**
     * H指示器
     */
    private var mDrawingRect: RectF? = null

    /**
     * 用于选择SV的矩形
     */
    private var mSatValRect: RectF? = null

    /**
     * 用于选择H的矩形
     */
    private var mHueRect: RectF? = null

    /**
     * SV指示器
     */
    private var mStartTouchPoint: Point? = null
    private var mListener: OnColorChangedListener? = null

    interface OnColorChangedListener {
        fun onColorChanged(color: Int)
    }

    private fun init() {
        mDensity = context.resources.displayMetrics.density //获取屏幕密度
        mSVTrackerRadius *= mDensity //灰度饱和度指示器的半径
        mHTrackerHeight *= mDensity //色相指示器高度
        mRectOffset *= mDensity //H、SV矩形与父布局的边距
        mHuePanelWidth *= mDensity //H矩形的宽度
        mPanelSpacing *= mDensity //H、SV矩形间的间距
        mPreferredHeight *= mDensity //当mode为MeasureSpec.UNSPECIFIED时的首选高度
        mPreferredWidth *= mDensity //当mode为MeasureSpec.UNSPECIFIED时的首选宽度
        drawingOffset = calculateRequiredOffset() //计算所需位移
        initPaintTools() //初始化画笔、画布
        isFocusable = true //设置可获取焦点
        isFocusableInTouchMode = true //设置在被触摸时会获取焦点
    }

    /**
     * mSVTrackerRadius、
     * mRectOffset、
     * BORDER_WIDTH * mDensity
     * 三者的最大值
     * 的1.5倍
     *
     * @return 边距
     */
    private fun calculateRequiredOffset(): Float {
        var offset = Math.max(mSVTrackerRadius, mRectOffset)
        offset = Math.max(offset, BORDER_WIDTH * mDensity)
        return offset * 1.5f
    }

    private fun initPaintTools() {
        mSatValPaint = Paint()
        mSatValTrackerPaint = Paint()
        mHuePaint = Paint()
        mHueTrackerPaint = Paint()
        mBorderPaint = Paint()
        mSatValTrackerPaint!!.style = Paint.Style.STROKE
        mSatValTrackerPaint!!.strokeWidth = 2f * mDensity
        mSatValTrackerPaint!!.isAntiAlias = true
        mHueTrackerPaint!!.color = mSliderTrackerColor
        mHueTrackerPaint!!.style = Paint.Style.STROKE
        mHueTrackerPaint!!.strokeWidth = 2f * mDensity
        mHueTrackerPaint!!.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthAllowed = MeasureSpec.getSize(widthMeasureSpec)
        var heightAllowed = MeasureSpec.getSize(heightMeasureSpec)
        widthAllowed =
            if (isUnspecified(widthMode)) mPreferredWidth.toInt() else widthAllowed
        heightAllowed =
            if (isUnspecified(heightMode)) mPreferredHeight.toInt() else heightAllowed
        var width = widthAllowed
        var height = (widthAllowed - mPanelSpacing - mHuePanelWidth).toInt()
        //当根据宽度计算出来的高度大于可允许的最大高度时 或 当前是横屏
        if (height > heightAllowed || "landscape" == tag) {
            height = heightAllowed
            width = (height + mPanelSpacing + mHuePanelWidth).toInt()
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        if (mDrawingRect!!.width() <= 0 || mDrawingRect!!.height() <= 0) return
        drawSatValPanel(canvas) //绘制SV选择区域
        drawHuePanel(canvas) //绘制右侧H选择区域
    }

    /**
     * 绘制S、V选择区域（矩形）
     *
     * @param canvas 画布
     */
    private fun drawSatValPanel(canvas: Canvas) {
        //描边（先画一个大矩形, 再在内部画一个小矩形，就可以显示出描边的效果）
        mBorderPaint!!.color = mBorderColor
        canvas.drawRect(
            mDrawingRect!!.left,
            mDrawingRect!!.top,
            mSatValRect!!.right + BORDER_WIDTH,
            mSatValRect!!.bottom + BORDER_WIDTH,
            mBorderPaint!!
        )

        //组合着色器 = 明度线性着色器 + 饱和度线性着色器
        val mShader = generateSVShader()
        mSatValPaint!!.shader = mShader
        canvas.drawRect(mSatValRect!!, mSatValPaint!!)

        //初始化选择器的位置
        val p = satValToPoint(mSat, mVal)
        //绘制显示SV值的选择器
        mSatValTrackerPaint!!.color = -0x1000000
        canvas.drawCircle(
            p.x.toFloat(),
            p.y.toFloat(),
            mSVTrackerRadius - 1f * mDensity,
            mSatValTrackerPaint!!
        )
        //绘制外圆
        mSatValTrackerPaint!!.color = -0x222223
        canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), mSVTrackerRadius, mSatValTrackerPaint!!)
    }

    /**
     * 创建SV着色器(明度线性着色器 + 饱和度线性着色器)
     *
     * @return 着色器
     */
    private fun generateSVShader(): ComposeShader {
        //明度线性着色器
        if (mValShader == null) {
            mValShader = LinearGradient(
                mSatValRect!!.left, mSatValRect!!.top, mSatValRect!!.left, mSatValRect!!.bottom,
                -0x1, -0x1000000, Shader.TileMode.CLAMP
            )
        }
        //HSV转化为RGB
        val rgb = Color.HSVToColor(floatArrayOf(mHue, 1f, 1f))
        //饱和线性着色器
        val satShader: Shader = LinearGradient(
            mSatValRect!!.left, mSatValRect!!.top, mSatValRect!!.right, mSatValRect!!.top,
            -0x1, rgb, Shader.TileMode.CLAMP
        )
        //组合着色器 = 明度线性着色器 + 饱和度线性着色器
        return ComposeShader(
            mValShader!!,
            satShader,
            PorterDuff.Mode.MULTIPLY
        )
    }

    /**
     * 绘制右侧H选择区域
     *
     * @param canvas 画布
     */
    private fun drawHuePanel(canvas: Canvas) {
        val rect = mHueRect
        mBorderPaint!!.color = mBorderColor
        canvas.drawRect(
            rect!!.left - BORDER_WIDTH,
            rect.top - BORDER_WIDTH,
            rect.right + BORDER_WIDTH,
            rect.bottom + BORDER_WIDTH,
            mBorderPaint!!
        )
        //初始化H线性着色器
        if (mHueShader == null) {
            val hue = IntArray(361)
            var count = 0
            var i = hue.size - 1
            while (i >= 0) {
                hue[count] =
                    Color.HSVToColor(floatArrayOf(i.toFloat(), 1f, 1f))
                i--
                count++
            }
            mHueShader = LinearGradient(
                rect.left,
                rect.top,
                rect.left,
                rect.bottom,
                hue,
                null,
                Shader.TileMode.CLAMP
            )
            mHuePaint!!.shader = mHueShader
        }
        canvas.drawRect(rect, mHuePaint!!)
        val halfHTrackerHeight = mHTrackerHeight / 2
        //初始化H选择器选择条位置
        val p = hueToPoint(mHue)
        val r = RectF()
        r.left = rect.left - mRectOffset
        r.right = rect.right + mRectOffset
        r.top = p.y - halfHTrackerHeight
        r.bottom = p.y + halfHTrackerHeight

        //绘制选择条
        canvas.drawRoundRect(r, 2f, 2f, mHueTrackerPaint!!)
    }

    private fun hueToPoint(hue: Float): Point {
        val rect = mHueRect
        val height = rect!!.height()
        val p = Point()
        p.y = (height - hue * height / 360f + rect.top).toInt()
        p.x = rect.left.toInt()
        return p
    }

    private fun satValToPoint(sat: Float, `val`: Float): Point {
        val height = mSatValRect!!.height()
        val width = mSatValRect!!.width()
        val p = Point()
        p.x = (sat * width + mSatValRect!!.left).toInt()
        p.y = ((1f - `val`) * height + mSatValRect!!.top).toInt()
        return p
    }

    private fun pointToSatVal(x: Float, y: Float): FloatArray {
        var x = x
        var y = y
        val rect = mSatValRect
        val result = FloatArray(2)
        val width = rect!!.width()
        val height = rect.height()
        x = if (x < rect.left) {
            0f
        } else if (x > rect.right) {
            width
        } else {
            x - rect.left
        }
        y = if (y < rect.top) {
            0f
        } else if (y > rect.bottom) {
            height
        } else {
            y - rect.top
        }
        result[0] = 1f / width * x
        result[1] = 1f - 1f / height * y
        return result
    }

    private fun pointToHue(y: Float): Float {
        var y = y
        val rect = mHueRect
        val height = rect!!.height()
        y = if (y < rect.top) {
            0f
        } else if (y > rect.bottom) {
            height
        } else {
            y - rect.top
        }
        return 360f - y * 360f / height
    }

    override fun onTrackballEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        var isUpdated = false
        if (event.action == MotionEvent.ACTION_MOVE) {
            when (mLastTouchedPanel) {
                PANEL.SAT_VAL -> {
                    var sat: Float
                    var `val`: Float
                    sat = mSat + x / 50f
                    `val` = mVal - y / 50f
                    if (sat < 0f) {
                        sat = 0f
                    } else if (sat > 1f) {
                        sat = 1f
                    }
                    if (`val` < 0f) {
                        `val` = 0f
                    } else if (`val` > 1f) {
                        `val` = 1f
                    }
                    mSat = sat
                    mVal = `val`
                    isUpdated = true
                }
                PANEL.HUE -> {
                    var hue = mHue - y * 10f
                    if (hue < 0f) {
                        hue = 0f
                    } else if (hue > 360f) {
                        hue = 360f
                    }
                    mHue = hue
                    isUpdated = true
                }
            }
        }
        if (isUpdated) {
            if (mListener != null) {
                mListener!!.onColorChanged(
                    Color.HSVToColor(
                        floatArrayOf(
                            mHue,
                            mSat,
                            mVal
                        )
                    )
                )
            }
            invalidate()
            return true
        }
        return super.onTrackballEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isUpdated = false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartTouchPoint = Point(event.x.toInt(), event.y.toInt())
                isUpdated = moveTrackersIfNeeded(event)
            }
            MotionEvent.ACTION_MOVE -> isUpdated = moveTrackersIfNeeded(event)
            MotionEvent.ACTION_UP -> {
                mStartTouchPoint = null
                isUpdated = moveTrackersIfNeeded(event)
            }
        }
        if (isUpdated) {
            if (mListener != null) {
                mListener!!.onColorChanged(
                    Color.HSVToColor(
                        floatArrayOf(
                            mHue,
                            mSat,
                            mVal
                        )
                    )
                )
            }
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun moveTrackersIfNeeded(event: MotionEvent): Boolean {
        if (mStartTouchPoint == null) return false
        var update = false
        val startX = mStartTouchPoint!!.x
        val startY = mStartTouchPoint!!.y
        if (mHueRect!!.contains(startX.toFloat(), startY.toFloat())) {
            mLastTouchedPanel = PANEL.HUE
            mHue = pointToHue(event.y)
            update = true
        } else if (mSatValRect!!.contains(startX.toFloat(), startY.toFloat())) {
            mLastTouchedPanel = PANEL.SAT_VAL
            val result = pointToSatVal(event.x, event.y)
            mSat = result[0]
            mVal = result[1]
            update = true
        }
        return update
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mDrawingRect = RectF()
        mDrawingRect!!.left = drawingOffset + paddingLeft
        mDrawingRect!!.right = w - drawingOffset - paddingRight
        mDrawingRect!!.top = drawingOffset + paddingTop
        mDrawingRect!!.bottom = h - drawingOffset - paddingBottom
        //当DatePickerView的长宽改变时，重新计算SV、H矩形大小
        setUpSatValRect()
        setUpHueRect()
    }

    private fun setUpSatValRect() {
        val dRect = mDrawingRect
        val panelSide = dRect!!.height() - BORDER_WIDTH * 2
        val left = dRect.left + BORDER_WIDTH
        val top = dRect.top + BORDER_WIDTH
        val bottom = top + panelSide
        val right = left + panelSide
        mSatValRect = RectF(left, top, right, bottom)
    }

    private fun setUpHueRect() {
        val dRect = mDrawingRect
        val left =
            dRect!!.right - mHuePanelWidth + BORDER_WIDTH
        val top = dRect.top + BORDER_WIDTH
        val bottom = dRect.bottom - BORDER_WIDTH
        val right = dRect.right - BORDER_WIDTH
        mHueRect = RectF(left, top, right, bottom)
    }

    /**
     * 设置颜色改变监听器
     *
     * @param listener 颜色改变监听器
     */
    fun setOnColorChangedListener(listener: OnColorChangedListener?) {
        mListener = listener
    }

    /**
     * 获取边框颜色
     *
     * @return 边框颜色
     */
    /**
     * 设置边框颜色
     *
     * @param color 边框颜色
     */
    var borderColor: Int
        get() = mBorderColor
        set(color) {
            mBorderColor = color
            invalidate()
        }

    /**
     * 获取当前颜色
     *
     * @return 当前颜色
     */
    /**
     * 设置选择的颜色
     *
     * @param color 被选择的颜色
     */
    var color: Int
        get() = Color.HSVToColor(floatArrayOf(mHue, mSat, mVal))
        set(color) {
            setColor(color, false)
        }

    /**
     * 设置被选择的颜色
     *
     * @param color    被选择的颜色
     * @param callback 是否触发OnColorChangedListener
     */
    fun setColor(color: Int, callback: Boolean) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        mHue = hsv[0]
        mSat = hsv[1]
        mVal = hsv[2]
        if (callback && mListener != null) {
            mListener!!.onColorChanged(
                Color.HSVToColor(
                    floatArrayOf(
                        mHue,
                        mSat,
                        mVal
                    )
                )
            )
        }
        invalidate()
    }

    var sliderTrackerColor: Int
        get() = mSliderTrackerColor
        set(color) {
            mSliderTrackerColor = color
            mHueTrackerPaint!!.color = mSliderTrackerColor
            invalidate()
        }

    companion object {
        /**
         * 显示H、SV的矩形的边框粗细（单位：dp）
         */
        private const val BORDER_WIDTH = 1f
        private fun isUnspecified(mode: Int): Boolean {
            return !(mode == MeasureSpec.EXACTLY || mode == MeasureSpec.AT_MOST)
        }
    }

    init {
        init()
    }
}