package com.example.mydemo.leftexit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mydemo.R
import com.example.mydemo.util.Utils


/**
 *Date: 2022/4/27
 *author: hxc
 */
class LeftExitActivity: AppCompatActivity() {
    private var myLayout:MyLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_exit)
        myLayout = MyLayout(this)
        myLayout!!.bind(this)

        myLayout!!.setListener(object :MyLayout.ScrollListener{
            override fun canTopScroll(): Boolean {
                return true
            }

            override fun canLeftScroll(): Boolean {
                return true
            }

            override fun canBottomScroll(): Boolean {
                return true
            }

            override fun topScrollFinish() {
                Utils.log("11111111","topScrollFinish")
            }

            override fun leftScrollFinish() {
                Utils.log("11111111","leftScrollFinish")
            }

            override fun bottomScrollFinish() {
                Utils.log("11111111","bottomScrollFinish")
            }

        })
    }

}