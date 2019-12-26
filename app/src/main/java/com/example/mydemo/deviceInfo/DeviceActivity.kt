package com.example.mydemo.deviceInfo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.example.mydemo.BaseActivity
import kotlinx.android.synthetic.main.activity_device.*
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.mydemo.R
import com.example.mydemo.util.Utils
import okhttp3.*
import java.io.IOException
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


/**
 *Date: 2019/12/23
 *author: hxc
 */
class DeviceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        tv_info.text = getInfo()

        getLocation()

        Toast.makeText(this,getIPAddress(),Toast.LENGTH_SHORT).show()

    }

    // 设备信息
    private fun getInfo(): String {


//BOARD 主板
        var phoneInfo = "BOARD: " + android.os.Build.BOARD
        phoneInfo += "\n BOOTLOADER: " + android.os.Build.BOOTLOADER
//BRAND 运营商
        phoneInfo += "\n BRAND: " + android.os.Build.BRAND;
        phoneInfo += "\n CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += "\n CPU_ABI2: " + android.os.Build.CPU_ABI2;
//DEVICE 驱动
        phoneInfo += "\n DEVICE: " + android.os.Build.DEVICE;
//DISPLAY 显示
        phoneInfo += "\n DISPLAY: " + android.os.Build.DISPLAY;
//指纹
        phoneInfo += "\n FINGERPRINT: " + android.os.Build.FINGERPRINT;
//HARDWARE 硬件
        phoneInfo += "\n HARDWARE: " + android.os.Build.HARDWARE;
        phoneInfo += "\n HOST: " + android.os.Build.HOST;
        phoneInfo += "\n ID: " + android.os.Build.ID;
//MANUFACTURER 生产厂家
        phoneInfo += "\n MANUFACTURER: " + android.os.Build.MANUFACTURER;
//MODEL 机型
        phoneInfo += "\n MODEL: " + android.os.Build.MODEL;
        phoneInfo += "\n PRODUCT: " + android.os.Build.PRODUCT;
        phoneInfo += "\n RADIO: " + android.os.Build.RADIO;
        phoneInfo += "\n RADITAGSO: " + android.os.Build.TAGS;
        phoneInfo += "\n TIME: " + android.os.Build.TIME;
        phoneInfo += "\n TYPE: " + android.os.Build.TYPE;
        phoneInfo += "\n USER: " + android.os.Build.USER;
//VERSION.RELEASE 固件版本
        phoneInfo += "\n VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += "\n VERSION.CODENAME: " + android.os.Build.VERSION.CODENAME;
//VERSION.INCREMENTAL 基带版本
        phoneInfo += "\n VERSION.INCREMENTAL: " + android.os.Build.VERSION.INCREMENTAL;
//VERSION.SDK SDK版本
        phoneInfo += "\n VERSION.SDK: " + android.os.Build.VERSION.SDK;
        phoneInfo += "\n VERSION.SDK_INT: " + android.os.Build.VERSION.SDK_INT;

        return phoneInfo
    }


    private fun afterM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    //定位信息
    private fun getLocation() {
        // Permission check
        if (afterM()) {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
                return
            }
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
                return
            }
        }

        var locationProvider = ""
        //获取地理位置管理器
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        //获取所有可用的位置提供器
        val providers: List<String> = locationManager.getProviders(true)
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return
        }
        //获取Location
        val location = locationManager.getLastKnownLocation(locationProvider)
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location)
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1f, locationListener);
    }


    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private fun showLocation(location: Location?) {
//        String locationStr = "纬度：" + location.getLatitude() + "\n"
//        + "经度：" + location.getLongitude();
        if (location != null) {
//            updateVersion(location.latitude.toString(), location.longitude.toString())
            getAddress(location.latitude,location.longitude)
        }
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    var locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            showLocation(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }


    };

    private fun getAddress(wd: Double, jd: Double){
        val geocoder = Geocoder(this)
//        boolean falg = geocoder.isPresent();
        val stringBuilder = StringBuilder()
        try {

            //根据经纬度获取地理位置信息
            val addresses = geocoder.getFromLocation(wd, jd, 1)
//            val addresses = geocoder.getFromLocation(30.26667, 120.20000, 1)

            //根据地址获取地理位置信息
//            List<Address> addresses = geocoder.getFromLocationName(adressStr, 1);

            if (addresses.isNotEmpty()) {
                var address = addresses[0]
//                Log.i("111","addresses $addresses")
//                Log.i("111","address $address")
                //中国_申通信息广场_上海市_null_CN_上海市_null_null_徐汇区_31.203507_121.429707
                stringBuilder.append(address.countryName).append("_")//国家
//                stringBuilder.append(address.featureName).append("_")//周边地址
                stringBuilder.append(address.locality).append("_")//市
//                stringBuilder.append(address.postalCode).append("_")
//                stringBuilder.append(address.countryCode).append("_")//国家编码
                stringBuilder.append(address.adminArea).append("_")//省份
//                stringBuilder.append(address.subAdminArea).append("_")
//                stringBuilder.append(address.thoroughfare).append("_")//道路
                stringBuilder.append(address.subLocality).append("_")//香洲区
//                stringBuilder.append(address.latitude).append("_")//经度
//                stringBuilder.append(address.longitude)//维度

                Log.i("111","address $stringBuilder")
            }
        } catch (e:IOException) {
            Toast.makeText(this, "报错", Toast.LENGTH_LONG).show();
            e.printStackTrace()
        }
    }

    //wd 纬度
//jd 经度
//    private fun updateVersion(wd: String, jd: String) {
////        http://api.map.baidu.com/geocoder?output=json&location=23.131427,113.379763&ak=esNPFDwwsXWtsQfw4NMNmur1
//        var path = "http://api.map.baidu.com/geocoder?output=json&location=$wd,$jd&ak=esNPFDwwsXWtsQfw4NMNmur1"
//        val okHttpClient = OkHttpClient()
//        val request = Request.Builder()
//            .url(path)
//            .get()//默认就是GET请求，可以不写
//            .build()
//        val call = okHttpClient.newCall(request)
//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("111", e.toString())
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                Log.d("111", "onResponse: " + response.body().string())
//            }
//        })
//    }


    //ip
    private fun getIPAddress() :String{

        val info = (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
       if (info != null && info.isConnected) {
           if (info.type == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
               try {
                   val en = NetworkInterface.getNetworkInterfaces() as Enumeration<NetworkInterface>
                   var intf = en.nextElement()
                   val enumIpAddr = intf.inetAddresses as Enumeration<InetAddress>
                   val inetAddress = enumIpAddr.nextElement()
                   if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                       return inetAddress.getHostAddress()
                   }
               } catch (e: SocketException) {
                   e.printStackTrace()
               }

           } else if (info.type == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
               val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
               var wifiInfo = wifiManager.connectionInfo;
               var ipAddress = intIP2StringIP(wifiInfo.ipAddress)//得到IPV4地址
               return ipAddress
           } else {
               //当前无网络连接,请在设置中打开网络
           }
       }
        return ""
        }


    /**
         * 将得到的int类型的IP转换为String类型
         *
         * @param ip
         * @return
         */
    private fun  intIP2StringIP(ip :Int) :String {
        return "${ip.and(0xFF)}.${ip.shr(8).and(0xFF)}.${ip.shr(16).and(0xFF)}.${ip.shr(24).and(0xFF)}"
//        return (ip and 0xFF)+"."+((ip >> 8) and 0xFF)+"."+((ip >> 16) and 0xFF)+"."+(ip >> 24 and 0xFF)

    }
}