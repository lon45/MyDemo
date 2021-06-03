package com.nader.collectmiclib

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.Message
import android.util.Log
import com.nader.collectmiclib.bean.SoundBean
import com.nader.collectmiclib.utils.FFT
import com.nader.collectmiclib.utils.VoiceUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs


/**
 *Date: 2021/5/13
 *author: hxc
 * 录音分析
 */
class SoundAnalysis {

    /**
     * 可能存在的采样频率
     */
    private val SAMPLE_RATES_LIST = intArrayOf(8000)//intArrayOf(11025, 8000, 22050, 44100, 16000)

    /**
     * 采样频率
     */
    private var sampleRate = 44100

    /**
     * 采样点数
     */
    private var sampleCount = 1024

    /**
     * 音乐跳变临界点
     */
    private val FREQUENCY_CRITICAL = 3500

    var mHandler: Handler? = null

    private var mExecutorService: ExecutorService? = null

    private var audioRecord: AudioRecord? = null

    constructor(mHandler: Handler) {
        this.mHandler = mHandler
        //录音JNI函数不具有线程安全性，因此用单线程
//        mExecutorService = Executors.newSingleThreadExecutor()
        mExecutorService = Executors.newFixedThreadPool(2)

        init()

    }

    fun init() {
        for (i in SAMPLE_RATES_LIST.indices) {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATES_LIST[i],
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioRecord.getMinBufferSize(
                    SAMPLE_RATES_LIST[i],
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
            )
            if (audioRecord!!.state == AudioRecord.STATE_INITIALIZED) {
                sampleRate = SAMPLE_RATES_LIST[i]
                sampleCount = 1024 //SAMPLE_COUNT[i];
                break
            }
        }
    }

    /**
     * 开始录音
     */
    fun startRecord() {
        if (audioRecord != null && audioRecord!!.state == AudioRecord.STATE_INITIALIZED) {
            mExecutorService!!.submit {
                Log.i("Record","startRecord")
                audioRecord!!.startRecording()
                analysisRecord()
            }
        }
    }

    /**
     * 停止录音
     */
    fun stopRecord() {
        if (audioRecord != null && audioRecord!!.state == AudioRecord.STATE_INITIALIZED) {
            mExecutorService!!.submit {
                audioRecord!!.stop()
//                audioRecord!!.release()
            }
        }
    }
    /**
     * 销毁
     */
    fun destroy(){
        if (audioRecord != null && audioRecord!!.state == AudioRecord.STATE_INITIALIZED) {
            mExecutorService!!.submit {
                audioRecord!!.release()
            }
        }
    }

    private fun analysisRecord() {
        val bufferRead = ByteArray(sampleCount)
        var lenght: Int
        var tempFrequency = 0.0

        while (audioRecord!!.read(bufferRead, 0, sampleCount).also { lenght = it } > 0) {
            //非录音状态，跳出循环
//            Log.i("Record",audioRecord!!.state.toString()+"||"+audioRecord!!.recordingState)
            if(audioRecord!!.state == AudioRecord.STATE_UNINITIALIZED || audioRecord!!.recordingState == AudioRecord.RECORDSTATE_STOPPED){
//                Log.i("Record22",audioRecord!!.state.toString())
                break
            }
//            Log.i("aaa","${VoiceUtil.calculateVolume(bufferRead,16)}")
            val currentFrequency = FFT.getFrequency(bufferRead, sampleRate, sampleCount)
            val currentVolume = VoiceUtil.getVolume(bufferRead, lenght)
            // && abs(currentFrequency - tempFrequency) > FREQUENCY_CRITICAL
            if (currentFrequency > 0) {//&& currentFrequency != tempFrequency
//                Log.i("calculateVolume","${currentFrequency}")
//                Log.i("Color_s2","${abs(currentFrequency - tempFrequency).toInt()} | ${currentFrequency.toInt()} | ${tempFrequency.toInt()}")
//                tempFrequency = currentFrequency
                if(mHandler != null){
                    var soundBean = SoundBean()
                    soundBean.mFrequency = currentFrequency
                    soundBean.mVolume = currentVolume
                    val message = Message.obtain()
                    message.obj = soundBean
                    message.what = CollectMic.SOUND_MESSAGE
                    mHandler!!.sendMessage(message)
                }

//                Log.i(
//                    "Record----------",
//                    "currentFrequency$currentFrequency---$currentVolume"
//                )
            }

        }

    }
}