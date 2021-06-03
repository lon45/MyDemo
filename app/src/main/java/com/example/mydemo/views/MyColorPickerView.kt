package com.example.mydemo.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.Nullable
import com.example.mydemo.util.Utils
import kotlin.math.*

/**
 *Date: 2021/1/13
 *author: hxc
 */
class MyColorPickerView : View {

    private var mContext: Context? = null
    /** 绘制hsv的画笔**/
    private var mPaint = Paint()
    /** 圆心**/
    var mCenterPoint = PointF(0F, 0F)
    /** 半径**/
    var mRadius = 0f

    /** 气泡半径**/
    val mPopRadius = 25
    /** 气泡画笔**/
    var mPopPaint = Paint()
    /** 气泡边框画笔**/
    var mPopBorderPaint = Paint()
    /** 气泡圆心**/
    var mPopPoint = PointF(0F, 0F)

    /** 当前颜色值 **/
    var currentColor = 0

    /** 颜色分区 **/
    val colorCount = 6
    /** 颜色角度 **/
    val colorAngleStep = 360 / colorCount
    /** 每个分区颜色  **/
    val colors = IntArray(colorCount + 1)
    /** hsv **/
    val hsv = floatArrayOf(0f, 1f, 1f)

    /** 是否从外面传入颜色 **/
    private var isSetColor = false


    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        init()
    }

    private fun init() {
        mPaint.isAntiAlias = true

        //计算每一个分区颜色
        for (i in colors.indices) {
            hsv[0] = (360 - i * colorAngleStep % 360).toFloat()
//            Log.i("colorAngleStep","${(360 - i * colorAngleStep % 360).toFloat()}")
            colors[i] = Color.HSVToColor(hsv)
        }
        colors[colorCount] = colors[0]

        mPopPaint.isAntiAlias = true
        mPopPaint.style = Paint.Style.FILL
        mPopPaint.color = Color.parseColor("#E8E8E8")

        mPopBorderPaint.isAntiAlias = true
        mPopBorderPaint.style = Paint.Style.STROKE
        mPopBorderPaint.color = Color.parseColor("#88D3D3D3")
        mPopBorderPaint.strokeWidth = 2f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBg(canvas)

        drawPop(canvas)
    }
    /**绘制hsv背景**/
    private fun drawBg(canvas: Canvas) {
        val sweepGradient = SweepGradient(mCenterPoint.x, mCenterPoint.y, colors, null)
        val radialGradient = RadialGradient(
            mCenterPoint.x, mCenterPoint.y,
            mRadius,
            intArrayOf(
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#CCFFFFFF"),
                Color.parseColor("#00FFFFFF")
            ),
            null,
            Shader.TileMode.CLAMP
        )
        val composeShader =
            ComposeShader(sweepGradient, radialGradient, PorterDuff.Mode.SRC_OVER)

        mPaint.shader = composeShader

        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, mRadius, mPaint)
    }
    /**绘制气泡**/
    private fun drawPop(canvas: Canvas) {
        /**改变气泡颜色**/
        mPopPaint.color = currentColor

        if (isSetColor) {
            val hsv = FloatArray(3)
            Color.colorToHSV(currentColor, hsv)
//            Log.i("colorAngleStep", "hsv${hsv[0]}|$${hsv[1]}|${hsv[2]}")
            mPopPoint.x =
                mCenterPoint.x + mRadius * hsv[1] * cos((hsv[0] * Math.PI / 180)).toFloat()
            mPopPoint.y =
                mCenterPoint.y - mRadius * hsv[1] * sin((hsv[0] * Math.PI / 180)).toFloat()

            isSetColor = false
        }
//        Log.i("colorAngleStep","${mPopPoint.x}|${mPopPoint.y}")
        /**气泡边框 start**/
        var border = 1
        val borderRectf = RectF(
            mPopPoint.x - mPopRadius - border,
            mPopPoint.y - mPopRadius * 2 - mPopRadius / 2 - border,
            mPopPoint.x + mPopRadius + border,
            mPopPoint.y - mPopRadius / 2 + border
        )
        canvas.drawArc(borderRectf, 135F, 270F, false, mPopBorderPaint)
        var mBorderPath = Path()
        mBorderPath.moveTo(mPopPoint.x, mPopPoint.y + border)
        mBorderPath.lineTo(
            mPopPoint.x + mPopRadius * cos(45 * Math.PI / 180).toFloat() + border,
            mPopPoint.y - mPopRadius * cos(45 * Math.PI / 180).toFloat() - 2
        )
        mBorderPath.lineTo(
            mPopPoint.x - mPopRadius * cos(45 * Math.PI / 180).toFloat() - border,
            mPopPoint.y - mPopRadius * cos(45 * Math.PI / 180).toFloat() - 2
        )
        mBorderPath.close()
        canvas.drawPath(mBorderPath, mPopBorderPaint)
        /**气泡边框 end**/
        /**气泡 start**/
        val rf = RectF(
            mPopPoint.x - mPopRadius, mPopPoint.y - mPopRadius * 2 - mPopRadius / 2,
            mPopPoint.x + mPopRadius, mPopPoint.y - mPopRadius / 2
        )
        canvas.drawArc(rf, 135F, 270F, false, mPopPaint)

        var mPath = Path()
        mPath.moveTo(mPopPoint.x, mPopPoint.y + border)
        mPath.lineTo(
            mPopPoint.x + mPopRadius * cos(45 * Math.PI / 180).toFloat(),
            mPopPoint.y - mPopRadius * cos(45 * Math.PI / 180).toFloat() - 2
        )
        mPath.lineTo(
            mPopPoint.x - mPopRadius * cos(45 * Math.PI / 180).toFloat(),
            mPopPoint.y - mPopRadius * cos(45 * Math.PI / 180).toFloat() - 2
        )
        mPath.close()
        canvas.drawPath(mPath, mPopPaint)
        /**气泡 end**/

        //将颜色传出去
        if(onColorChangedListener != null){
            onColorChangedListener!!.onColorChange(pointToHsv(mCenterPoint.x,mCenterPoint.y,mPopPoint.x,mPopPoint.y))
        }

//        canvas.drawCircle(mPopPoint.x,mPopPoint.y,5f,mPopPaint)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 视图大小设置为直径
        mCenterPoint.x = w.toFloat() / 2
        mCenterPoint.y = h.toFloat() / 2

        mPopPoint.x = w.toFloat() / 2
        mPopPoint.y = h.toFloat() / 2

        mRadius = if (w > h) h.toFloat() / 2 else w.toFloat() / 2 - mPopRadius*2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE,MotionEvent.ACTION_UP ->{
                if(sqrt(((event.y - mCenterPoint.y).pow(2.0f)+ (event.x - mCenterPoint.x).pow(2.0f)).toDouble()).toFloat() <= mRadius ){
                    mPopPoint.y = event.y
                    mPopPoint.x = event.x
                    currentColor = Color.HSVToColor(pointToHsv(mCenterPoint.x,mCenterPoint.y,mPopPoint.x,mPopPoint.y))
                    invalidate()
                }
            }
            else -> {
            }
        }

        return true
    }
    /**将点击的位置转为 hsv**/
    private fun pointToHsv(centerX: Float, centerY: Float, popX: Float, popY: Float) :FloatArray{
        val hsv = floatArrayOf(0f, 1f, 1f)

        hsv[0] = getHue(centerX, centerY, popX, popY).toFloat()
        hsv[1] = getSat(centerX, centerY, popX, popY)

//        Log.i("colorAngleStep", "hsvhsv ${hsv[0]}|${hsv[1]}|${hsv[2]}")
//        Log.i("pointToHsv", "${Utils.colorIntToRGB(Color.HSVToColor(hsv))}")
        return hsv
    }

    /**从外部设置颜色**/
    fun setColor(color: Int) {
        currentColor = color
        isSetColor = true
        invalidate()
    }

    private var onColorChangedListener :OnColorChangedListener? = null

    fun setOnColorChangedListener(onColorChangedListener :OnColorChangedListener){
        this.onColorChangedListener = onColorChangedListener
    }

    interface OnColorChangedListener{
        fun onColorChange(hsv: FloatArray)
    }

    /**
     * 计算角度，即HSB中的H
     *
     * @param popX   pop 小圆point
     * @param centerX center圆心point
     * @return
     */
    private fun getHue(centerX: Float, centerY: Float, popX: Float, popY: Float): Double {
        var hue = 0.0
        val deltaA = abs(popX - centerX).toDouble()
        val deltaB = abs(popY - centerY).toDouble()
        val deltaC = sqrt(
            deltaB.pow(2.0) + deltaA.pow(2.0)
        )
        if (centerX == popX && centerY == popY) {
            return 0.0
        }
        if (centerX == popX) { //在Y轴上
            hue = if (centerY > popY) {
                90.0
            } else {
                270.0
            }
            return hue
        }
        if (centerY == popY) { //在X轴上
            hue = if (centerX > popX) {
                180.0
            } else {
                0.0
            }
            return hue
        }
        if (popX > centerX && centerY > popY) { //第一象限
            hue = asin(deltaB / deltaC) * 180 / Math.PI
        } else if (popX < centerX && popY < centerY) { //第二象限
            hue = asin(deltaA / deltaC) * 180 / Math.PI + 90
        } else if (popX < centerX && popY > centerY) { //第三象限
            hue = asin(deltaB / deltaC) * 180 / Math.PI + 180
        } else if (popX > centerX && popY > centerY) { //第四象限
            hue = asin(deltaA / deltaC) * 180 / Math.PI + 270
        }
        return hue
    }

    /**
     * 计算角度，即HSB中的S
     *
     * @param popX   pop 小圆point
     * @param centerX center圆心point
     * @return
     */
    private fun getSat(centerX: Float, centerY: Float, popX: Float, popY: Float): Float {
        return sqrt((popX - centerX).pow(2.0f) + ((popY - centerY).pow(2.0f)).toDouble()).toFloat() / mRadius
    }
}