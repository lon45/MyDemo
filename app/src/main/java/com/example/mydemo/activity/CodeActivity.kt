package com.example.mydemo.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.adapter.DragSortAdapter
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.util.Utils
import com.example.mydemo.views.MyCodeView
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.activity_code.*
import kotlinx.android.synthetic.main.activity_drag_sort.*
import java.util.*


/**
 *Date: 2020/12/4
 *author: hxc
 * 验证码demo
 */
class CodeActivity :BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        mCodeView.showSoft()
        mCodeView.setOnInputListener(object : MyCodeView.OnInputListener{
            override fun onFinish(code: String) {
                Log.e("setOnInputListener","$code")
            }

            override fun onCurrent(position:Int) {

            }

        })

    }

    override fun onResume() {
        super.onResume()
//        mCodeView.showSoft()
    }

}