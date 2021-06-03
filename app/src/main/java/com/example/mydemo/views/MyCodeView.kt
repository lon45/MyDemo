package com.example.mydemo.views

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mydemo.R
import kotlin.math.max


/**
 *Date: 2020/12/14
 *author: hxc
 */
class MyCodeView : RelativeLayout {

    private val maxLength = 6

    private var imm: InputMethodManager? = null
    private var mContext: Context? = null
    private var tv1: TextView? = null
    private var tv2: TextView? = null
    private var tv3: TextView? = null
    private var tv4: TextView? = null
    private var tv5: TextView? = null
    private var tv6: TextView? = null

    private var underLine1: View? = null
    private var underLine2: View? = null
    private var underLine3: View? = null
    private var underLine4: View? = null
    private var underLine5: View? = null
    private var underLine6: View? = null

    private var etInput: EditText? = null

    private var code = ArrayList<String>()

    constructor(mContext: Context) : this(mContext, null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs, 0)

    constructor(mContext: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        mContext,
        attrs,
        defStyleAttr
    ) {
        this.mContext = mContext
        initView()
    }

    private fun initView() {
        if (mContext != null) {
            imm = mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = LayoutInflater.from(mContext).inflate(R.layout.layout_code, this)
            tv1 = view.findViewById(R.id.tv_1)
            tv2 = view.findViewById(R.id.tv_2)
            tv3 = view.findViewById(R.id.tv_3)
            tv4 = view.findViewById(R.id.tv_4)
            tv5 = view.findViewById(R.id.tv_5)
            tv6 = view.findViewById(R.id.tv_6)
            underLine1 = view.findViewById(R.id.underline1)
            underLine2 = view.findViewById(R.id.underline2)
            underLine3 = view.findViewById(R.id.underline3)
            underLine4 = view.findViewById(R.id.underline4)
            underLine5 = view.findViewById(R.id.underline5)
            underLine6 = view.findViewById(R.id.underline6)

            showCode()

            etInput = view.findViewById(R.id.et_input)

            etInput!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    if (editable != null && editable.isNotEmpty()) {
                        etInput!!.setText("")
                        if (code.size < maxLength) {
                            code.add(editable.toString().trim())
                            showCode()
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })

            // 监听验证码删除按键
            etInput!!.setOnKeyListener(OnKeyListener { view, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.action == KeyEvent.ACTION_DOWN && code.size > 0) {
                    code.removeAt(code.size - 1)
                    showCode()
                    return@OnKeyListener true
                }
                false
            })

        }
    }


    private fun showCode() {
        var c1 = ""
        var c2 = ""
        var c3 = ""
        var c4 = ""
        var c5 = ""
        var c6 = ""
        if (code.size >= 1) {
            c1 = code[0]
        }
        if (code.size >= 2) {
            c2 = code[1]
        }
        if (code.size >= 3) {
            c3 = code[2]
        }
        if (code.size >= 4) {
            c4 = code[3]
        }
        if (code.size >= 5) {
            c5 = code[4]
        }
        if (code.size >= 6) {
            c6 = code[5]
        }
        if (tv1 != null && tv2 != null && tv3 != null && tv4 != null && tv5 != null && tv6 != null) {
            tv1!!.text = c1
            tv2!!.text = c2
            tv3!!.text = c3
            tv4!!.text = c4
            tv5!!.text = c5
            tv6!!.text = c6

            setColor()//设置高亮颜色
            callBack()//回调
        }


    }

    /**
     * 设置高亮颜色
     */
    private fun setColor() {
        val color_default: Int = Color.parseColor("#999999")
        val color_focus: Int = Color.parseColor("#3F8EED")
        underLine1!!.setBackgroundColor(color_default)
        underLine2!!.setBackgroundColor(color_default)
        underLine3!!.setBackgroundColor(color_default)
        underLine4!!.setBackgroundColor(color_default)
        underLine5!!.setBackgroundColor(color_default)
        underLine6!!.setBackgroundColor(color_default)
        if (code.size == 0) {
            underLine1!!.setBackgroundColor(color_focus)
        }
        if (code.size == 1) {
            underLine2!!.setBackgroundColor(color_focus)
        }
        if (code.size == 2) {
            underLine3!!.setBackgroundColor(color_focus)
        }
        if (code.size == 3) {
            underLine4!!.setBackgroundColor(color_focus)
        }
        if (code.size == 4) {
            underLine5!!.setBackgroundColor(color_focus)
        }
        if (code.size == 5) {
            underLine6!!.setBackgroundColor(color_focus)
        }
    }

    /**
     * 回调
     */
    private fun callBack() {
        if (onInputListener == null) {
            return
        }
        if (code.size == maxLength) {
            onInputListener!!.onFinish(getPhoneCode())
        } else {
            onInputListener!!.onCurrent(code.size)
        }
    }

    fun showSoft(){
        if(etInput != null) {
            etInput!!.isFocusable = true
            etInput!!.isFocusableInTouchMode = true
            etInput!!.requestFocus()
        }
    showSoftInput()
    }

    /**
     * 显示键盘
     */
    private fun showSoftInput() {
        //显示软键盘
        if (imm != null && etInput != null) {
            etInput!!.postDelayed({ imm!!.showSoftInput(etInput, 0) }, 200)
        }
    }

    //定义回调
    interface OnInputListener {
        fun onFinish(code: String)
        fun onCurrent(position:Int)
    }

    private var onInputListener: OnInputListener? = null
    fun setOnInputListener(onInputListener: OnInputListener?) {
        this.onInputListener = onInputListener
    }

    /**
     * 获得手机号验证码
     * @return 验证码
     */
    private fun getPhoneCode(): String {
        val sb = StringBuilder("")
        for (code in code) {
            sb.append(code)
        }
        return sb.toString()
    }
}