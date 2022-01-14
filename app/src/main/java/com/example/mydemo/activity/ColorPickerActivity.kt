package com.example.mydemo.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.example.mydemo.R
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.views.ColorPickerView
import com.example.mydemo.views.MyColorPickerView
import kotlinx.android.synthetic.main.activity_color_picker.*




/**
 *Date: 2020/12/4
 *author: hxc
 * 颜色选择demo
 */
class ColorPickerActivity :BaseActivity() {


    private var mHsv:FloatArray? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_color_picker
    }

    override fun initView() {

        seekbar_1.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            change()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        mv.setColor(Color.parseColor("#FFF0F5"))
        mv.setOnColorChangedListener(object : MyColorPickerView.OnColorChangedListener{
            override fun onColorChange(hsv: FloatArray) {
                mHsv = hsv
                change()
            }

        })


    }

    override fun addListener() {

    }


    fun change(){
        if(mHsv == null){
            return
        }
//        Log.i("progress","${seekbar_1.progress * 255 / 100}")
//        view_color.setBackgroundColor(Color.HSVToColor(seekbar_1.progress * 255 / 100,mHsv))
//        tv_color.text = "${Utils.colorIntToRGB(Color.HSVToColor(seekbar_1.progress * 255 / 100,mHsv))}"
        /**改变 hsv中的V**/
        var progress = seekbar_1.progress.toFloat() * 3 / 10 + 70
        mHsv!![2] = progress / 100
        view_color.setBackgroundColor(Color.HSVToColor(mHsv))
        tv_color.text = "${Utils.colorIntToRGB(Color.HSVToColor(mHsv))}"
    }

    override fun onResume() {
        super.onResume()
//        mv.setColor(Color.parseColor("#ff0000"))
        mv.setColor(Color.parseColor("#FFF0F5"))
    }

}