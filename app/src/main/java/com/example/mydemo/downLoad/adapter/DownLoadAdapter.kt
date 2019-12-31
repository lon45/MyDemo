package com.example.mydemo.downLoad.adapter

import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemo.R
import com.example.mydemo.downLoad.DownLoaderManger
import com.example.mydemo.downLoad.bean.DownLoadInfo
import com.example.mydemo.util.Utils
import kotlinx.android.synthetic.main.item_down_load.view.*

/**
 *Date: 2019/11/28
 *author: hxc
 */
class DownLoadAdapter(var mContext: Context, var list: ArrayList<DownLoadInfo>, private var onItemClick : (item:DownLoadInfo) -> Unit) :
    RecyclerView.Adapter<DownLoadAdapter.ViewHolder>() {
    val inflater = LayoutInflater.from(mContext)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = inflater.inflate(R.layout.item_down_load, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = list[position]
        if(item.lesson_chapter == 0){
            holder.itemView.tv_chapter.text = "预."
        } else {
            holder.itemView.tv_chapter.text = "${item.lesson_chapter}."
        }

        holder.itemView.tv_title.text = item.lesson_name
        holder.itemView.tv_time_length.text = "${item.lesson_duration / 60}分钟"
        holder.itemView.tv_size.text = Formatter.formatFileSize(mContext, item.lesson_size)
        holder.itemView.cp_progress.visibility = View.INVISIBLE
        when (item.downLoad_state) {
            DownLoaderManger.STATE_START// 开始下载
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_pause)
                holder.itemView.cp_progress.setValue(0)
                holder.itemView.cp_progress.visibility = View.VISIBLE
                if (item.lesson_size > 0) {
                    holder.itemView.cp_progress.setValue((item.finished *100 / item.lesson_size).toInt())
                }
            }
            DownLoaderManger.STATE_WAITING// 准备下载
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_pause)
                holder.itemView.cp_progress.setValue(0)
                holder.itemView.cp_progress.visibility = View.VISIBLE
                if (item.lesson_size > 0) {
                    holder.itemView.cp_progress.setValue((item.finished *100 / item.lesson_size).toInt())
                }
            }
            DownLoaderManger.STATE_DOWNLOADING// 下载中
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_pause)
                holder.itemView.cp_progress.setValue(0)
                holder.itemView.cp_progress.visibility = View.VISIBLE
                if (item.lesson_size > 0) {
                    holder.itemView.cp_progress.setValue((item.finished *100 / item.lesson_size).toInt())
                }
            }
            DownLoaderManger.STATE_PAUSED// 暂停
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_2)
                holder.itemView.cp_progress.setValue(0)
                holder.itemView.cp_progress.visibility = View.VISIBLE
                if (item.lesson_size > 0) {
                    holder.itemView.cp_progress.setValue((item.finished *100 / item.lesson_size).toInt())
                }
            }
            DownLoaderManger.STATE_DOWNLOADED// 下载完毕
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_complet)
                holder.itemView.iv_download_state.visibility = View.GONE
                holder.itemView.cp_progress.setValue(100)
                holder.itemView.cp_progress.visibility = View.INVISIBLE
                holder.itemView.iv_download_complet.visibility = View.VISIBLE
            }
            DownLoaderManger.STATE_ERROR,DownLoaderManger.STATE_NONE,DownLoaderManger.STATE_DELETE// 下载失败
            -> {
                holder.itemView.iv_download_state.setImageResource(R.mipmap.icon_download_1)
                holder.itemView.cp_progress.visibility = View.INVISIBLE
            }

            }

        holder.itemView.rl_progress.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            onItemClick(item)

        }
        holder.itemView.setOnClickListener {
            if (!Utils.isFastDoubleClick()) {
                return@setOnClickListener
            }
            onItemClick(item)

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private val Tag = DownLoadAdapter::class.java.simpleName

    }

}