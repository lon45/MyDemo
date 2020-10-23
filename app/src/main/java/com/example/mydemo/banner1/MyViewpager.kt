package com.example.mydemo.banner1

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mydemo.R
import kotlinx.android.synthetic.main.item_home_banner.view.*

/**
 *Date: 2019/12/31
 *author: hxc
 */
class MyViewpager(private val mContext: Context, private val datas:List<String>, private val onItemClick:(item:String) ->Unit) :PagerAdapter(){
    private var layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return if (datas.size == 1) 1 else Integer.MAX_VALUE
    }
    var imgWith = 0
    var imgHeight = 0
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.item_home_banner, null)
        if (datas.isNotEmpty()) {

            Glide.with(mContext).asBitmap().load(datas[position % datas.size]).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.Img.post(Runnable {

                        var width = view.Img.width // 获取宽度
                        var height = view.Img.height // 获取高度
                        if(imgWith == 0){
                            imgWith = width
                        }
                        if(imgHeight == 0){
                            imgHeight = height
                        }

                        if(width == 0){
                            width = imgWith
                        }
                        if(height == 0){
                            height = imgHeight
                        }
                        var scale = width.toFloat() / resource.width.toFloat()
                        if ((scale * resource.height.toFloat()).toInt() < height) {
                            scale = height.toFloat() / resource.height.toFloat()
                        }

                        if(width == 0 || height == 0){
                            return@Runnable
                        }
                        val tempBm = Bitmap.createScaledBitmap(resource, if((resource.width.toFloat() * scale).toInt()<width) width else (resource.width.toFloat() * scale).toInt(), if((resource.height.toFloat() * scale).toInt()<height) height else (resource.height.toFloat() * scale).toInt(), false)
                        //裁剪
                        var x = (tempBm.width - width) / 2
                        var y = (tempBm.height - height) / 2

                        var thumbBmp = Bitmap.createBitmap(tempBm, x, y, width, height, null, false)

                        view.Img.setImageBitmap(thumbBmp)
                    })
                }
            })

//

            view.setOnClickListener { onItemClick(datas[position % datas.size]) }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}