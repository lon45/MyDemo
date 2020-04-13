package com.example.mydemo.coordinator_Layout

import com.google.android.material.appbar.AppBarLayout

/**
 *Date: 2020/4/8
 *author: hxc
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        if (p1 == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(p0!!, State.EXPANDED)
            }
            mCurrentState = State.EXPANDED
        } else if (Math.abs(p1) >= p0!!.totalScrollRange) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(p0!!, State.COLLAPSED)
            }
            mCurrentState = State.COLLAPSED
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(p0!!, State.IDLE)
            }
            mCurrentState = State.IDLE
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)


}
