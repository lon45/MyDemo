package com.example.mydemo.leftscroll

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydemo.base.BaseActivity
import com.example.mydemo.R
import com.yanzhenjie.recyclerview.*
import kotlinx.android.synthetic.main.activity_left_scorll.*


/**
 *Date: 2020/8/21
 *author: hxc
 */
class LeftScrollActivity : BaseActivity() {

    private val datas = arrayListOf("111111111","222222","33333333","4444444444","555555555555","666666666")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_scorll)

//        rv_time.isItemViewSwipeEnabled = true// 开启滑动删除。默认关闭。
        rv_time.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 设置监听器。
            rv_time.setSwipeMenuCreator { leftMenu, rightMenu, position ->
                // 删除
                val deleteItem = SwipeMenuItem(this@LeftScrollActivity)
                deleteItem.setText("删除")
                    .setBackgroundColor(Color.RED)
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(15) // 文字大小。
                    .setWidth(140).height = ViewGroup.LayoutParams.MATCH_PARENT
                rightMenu.addMenuItem(deleteItem)
            }

        // 菜单点击监听。
        rv_time.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            Toast.makeText(this@LeftScrollActivity,"删除成功",Toast.LENGTH_LONG).show()
            datas.removeAt(adapterPosition)
            rv_time.adapter!!.notifyDataSetChanged()
        }

        // 必须 最后执行
        rv_time.adapter = LeftScrollAdapter(this,datas) {

            Toast.makeText(this@LeftScrollActivity,"点击事件",Toast.LENGTH_LONG).show()
        }

    }

}