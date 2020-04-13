package com.example.mydemo.coordinator_Layout

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import com.example.mydemo.BaseActivity
import com.example.mydemo.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_coordinatorlayout.*

/**
 *Date: 2020/4/8
 *author: hxc
 */
class CoordinatorLayoutActivity : BaseActivity() {

    private val data:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinatorlayout)

        appbar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    //展开状态
                    var spanString = SpannableString("上官婉儿")
                    spanString.setSpan(AbsoluteSizeSpan(12,true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    coll_layout.title = "上官婉儿"
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    coll_layout.title = "上官婉儿"
                } else {
                    //中间状态

                }
            }

        })

        for(i in 0..40){
            data.add("假数据$i")
        }

        recyclerView.adapter = StringAdapter(this,data)

    }

}