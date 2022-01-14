package com.example.mydemo.coordinator_Layout

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.Toast
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import com.example.mydemo.util.MeasureUtil
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_coordinatorlayout.*
import com.example.mydemo.views.TopLayoutManager



/**
 *Date: 2020/4/8
 *author: hxc
 */
class CoordinatorLayoutActivity : BaseActivity() {

    private val data: ArrayList<CoordBean> = ArrayList()
    //展开状态
    private var expanded_state = true

    override fun getLayoutId(): Int {
        return R.layout.activity_coordinatorlayout
    }

    override fun initView() {

    }

    override fun addListener() {

        toolbar.setNavigationOnClickListener {

            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show()

        }

        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    //展开状态
                    var spanString = SpannableString("上官婉儿")
                    spanString.setSpan(AbsoluteSizeSpan(12, true), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    coll_layout.title = "上官婉儿"
                    expanded_state = true
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    coll_layout.title = "上官婉儿"
                    expanded_state = false
                } else {
                    //中间状态

                }
            }

        })



        ivShare.setOnClickListener {
            //            Log.e("scrollTo","scrollTo")
            appbar.setExpanded(false)
//            recyclerView.smoothScrollBy(0, 200)
//            coord.scrollBy(0,200)

//            if (count < collegesRV?.layoutManager!!.itemCount) {

//            }

            smoothScroll(25)
        }


        for (i in 0..60) {
            var cb = CoordBean()
            cb.name = "假数据$i"
            var list = arrayListOf("子集${i + 1}", "子集${i + 2}", "子集${i + 3}")
            cb.info = list

            data.add(cb)
        }

        val manager = TopLayoutManager(this)
        recyclerView.layoutManager = manager

        recyclerView.adapter = CoordAdapter(this, data)

    }


    //        val offset = MeasureUtil.getMeasuredViewH(business_title)
    private val location = IntArray(2)

    private fun smoothScroll(position: Int) {
        var offset2 = MeasureUtil.dip2px(this, 50 * 1f) // 收缩 toolbar的高度
        if (expanded_state) {
            offset2 = MeasureUtil.dip2px(this, 100 * 1f)// 展开top高度
        }

////        val item = recyclerView.layoutManager!!.findViewByPosition(position)//collegeLayoutItems[count]
//        val item = recyclerView.Recycler().getViewForPosition(position)
//        Log.i("smoothScroll", "${item == null}")
//        Log.i("smoothScroll", "${recyclerView.layoutManager!!.childCount}")
//        if(item != null){
//        Log.i("smoothScroll", "${item.recyclerView_info == null}")
//        val item2 = item.recyclerView_info.layoutManager!!.findViewByPosition(0)
//        Log.i("smoothScroll", "${item2 == null}")
//            item?.getLocationOnScreen(location)
////        colSv.smoothScrollBy(0, location[1] - offset + offset2)
//        Log.i("smoothScroll", "${location[1]} | ${offset2}")
//        recyclerView.smoothScrollBy(0, location[1] - offset2)

            recyclerView.smoothScrollToPosition(position)
//    }

    }
}