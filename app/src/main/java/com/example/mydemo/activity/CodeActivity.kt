package com.example.mydemo.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.adapter.DragSortAdapter
import com.example.mydemo.base.BaseActivity
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.activity_drag_sort.*
import java.util.*


/**
 *Date: 2020/12/4
 *author: hxc
 */
class DragSortActivity :BaseActivity() {

    private val datas = arrayListOf("111111111","222222","33333333","4444444444","555555555555","666666666")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_sort)

        val mItemMoveListener: OnItemMoveListener = object : OnItemMoveListener {
            override fun onItemMove(srcHolder: RecyclerView.ViewHolder, targetHolder: RecyclerView.ViewHolder): Boolean {
                // 此方法在Item拖拽交换位置时被调用。
                // 第一个参数是要交换为之的Item，第二个是目标位置的Item。

                // 交换数据，并更新adapter。
                val fromPosition: Int = srcHolder.adapterPosition
                val toPosition: Int = targetHolder.adapterPosition
                Collections.swap(datas, fromPosition, toPosition)
                srv_drag.adapter!!.notifyItemMoved(fromPosition, toPosition)

                // 返回true，表示数据交换成功，ItemView可以交换位置。
                return true
            }

            override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder) {
                // 此方法在Item在侧滑删除时被调用。

                // 从数据源移除该Item对应的数据，并刷新Adapter。
                val position: Int = srcHolder.adapterPosition
                datas.removeAt(position)
                srv_drag.adapter!!.notifyItemRemoved(position)
            }
        }
        srv_drag.isLongPressDragEnabled = true
        srv_drag.setOnItemMoveListener(mItemMoveListener) // 监听拖拽，更新UI。
        srv_drag.adapter = DragSortAdapter(this,datas) {
            try {
                //设置行为为 发送短信
                val intent = Intent(Intent.ACTION_VIEW)
//            设置发送至 10086
                intent.type = "vnd.android-dir/mms-sms"
//            设置短信的默认发送内容
                intent.putExtra("sms_body", "公众号：霸道的程序猿")
                startActivity(intent)
            } catch (e:Exception){

            }

        }


    }


//    gridview
//    fun onItemMove(srcHolder: ViewHolder, targetHolder: ViewHolder): Boolean {
//        val fromPosition: Int = srcHolder.getAdapterPosition()
//        val toPosition: Int = targetHolder.getAdapterPosition()
//        if (fromPosition < toPosition) {
//            for (i in fromPosition until toPosition) {
//                Collections.swap(mDataList, i, i + 1)
//            }
//        } else {
//            for (i in fromPosition downTo toPosition + 1) {
//                Collections.swap(mDataList, i, i - 1)
//            }
//        }
//        mMenuAdapter.notifyItemMoved(fromPosition, toPosition)
//        return true
//    }
}