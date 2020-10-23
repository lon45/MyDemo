package com.example.mydemo.activity

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import com.example.mydemo.R
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.activity_black_white.*


class BlackWhiteActivity : BaseActivity() {

    private var isBW = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black_white)

        tv_play.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            isBW = !isBW
            change1()

        }


    }

    private fun change1(){
        val view: View = window.decorView
        val paint = Paint()
        val cm = ColorMatrix()
// 关键起作用的代码，Saturation，翻译成中文就是饱和度的意思。
// 官方文档说明：A value of 0 maps the color to gray-scale. 1 is identity.
// 原来如此，666
// 关键起作用的代码，Saturation，翻译成中文就是饱和度的意思。
// 官方文档说明：A value of 0 maps the color to gray-scale. 1 is identity.
// 原来如此，666
        if(isBW){
            cm.setSaturation(0f)
        } else {
            cm.setSaturation(1f)
        }

        paint.colorFilter = ColorMatrixColorFilter(cm)
        view.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }

}