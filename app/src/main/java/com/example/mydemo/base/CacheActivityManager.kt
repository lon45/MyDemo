package com.example.mydemo.base

import android.app.Activity
import java.util.*

/**
 * @author hxc
 * activity管理
 */
object CacheActivityManager {
    var activityList: MutableList<Activity?> = LinkedList()

    /** * 添加到Activity容器中  */
    fun addActivity(activity: Activity?) {
        if (!activityList.contains(activity)) {
            activityList.add(activity)
        }
    }

    /** * 便利所有Activigty并finish  */
    fun finishAllActivity() {
        while (activityList.size > 0) {
            var activity = activityList[0]
            activityList.remove(activity)
            activity!!.finish()
            System.gc()
        }
    }

    /** * 结束指定的Activity  */
    fun finishSingleActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            if (activityList.contains(activity)) {
                activityList.remove(activity)
            }
            activity.finish()
            activity = null
        }
    }

    /** * 结束指定类名的Activity 在遍历一个列表的时候不能执行删除操作，所有我们先记住要删除的对象，遍历之后才去删除。  */
    fun finishSingleActivityByClass(cls: Class<*>) {
        var tempActivity: Activity? = null
        for (activity in activityList) {
            if (activity!!.javaClass == cls) {
                tempActivity = activity
            }
        }
        finishSingleActivity(tempActivity)
    }

    fun getActivity(cls: Class<*>): Activity? {
        var tempActivity: Activity? = null
        for (activity in activityList) {
            if (activity!!.javaClass == cls) {
                tempActivity = activity
            }
        }
        return tempActivity
    }
    //获取栈顶activity
    fun getTopActivity(): Activity?{
        var tempActivity: Activity? = null
        if(activityList.size > 0){
            tempActivity = activityList[activityList.size - 1]
        }

        return tempActivity
    }
}