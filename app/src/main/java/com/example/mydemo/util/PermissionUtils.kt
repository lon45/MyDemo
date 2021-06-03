package com.app.chuanghehui.commom.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

/**
 *Date: 2019/12/3
 *author: hxc
 * 权限
 */
object PermissionUtils {

    private fun afterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
    //对权限统一提醒
    fun requestPermission(activity: Activity) {
        if (afterM()) {
//            val hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA), 86)
//                return false
//            }
        }
//        return true
    }

    fun requestWriteStorage(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 86)
                return false
            }
        }
        return true
    }
    fun requestReadStorage(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 86)
                return false
            }
        }
        return true
    }

    fun requestReadWirteStorage(activity: Activity): Boolean {
        if (afterM()) {
            val hasReadPermission = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasWritePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (hasReadPermission != PackageManager.PERMISSION_GRANTED || hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 86)
                return false
            }
        }
        return true
    }


    /**
     *     获取deviceid 需要的权限
     */

    fun requestPhoneState(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), 86)
                return false
            }
        }
        return true
    }



    /**
     *     获取相机 需要的权限
     */

    fun requestCameraState(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.CAMERA), 86)
                return false
            }
        }
        return true
    }

    //ACCESS_FINE_LOCATION
    fun requestFineLocation(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 86)
                return false
            }
        }
        return true
    }
    //ACCESS_COARSE_LOCATION
    fun requestCoarseLocation(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 86)
                return false
            }
        }
        return true
    }
    fun requestLocationIsGranted(activity: Activity):Boolean{
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            val hasPermission1 = activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            if (hasPermission1 != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    fun requestRecordAudio(activity: Activity): Boolean {
        if (afterM()) {
            val hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS), 86)
                return false
            }

//            val hasPermission1 = activity.checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS)
//            if (hasPermission1 != PackageManager.PERMISSION_GRANTED) {
//                activity.requestPermissions(arrayOf(Manifest.permission.MODIFY_AUDIO_SETTINGS), 86)
//                return false
//            }
        }
        return true
    }

}