package com.jevely.androidcatontest

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.Choreographer
import java.lang.StringBuilder

object BlockDetectUtil {

    private val TIME_BLOCK = 600
    private val TEST_COUNT = 6
    var handler: Handler? = null

    fun start() {
        val logThread = HandlerThread("LJW")
        logThread.start()
        handler = Handler(logThread.looper)
        handler?.postDelayed(logRunnable, (TIME_BLOCK / TEST_COUNT).toLong())

        Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                handler?.removeCallbacks(logRunnable)
                handler?.postDelayed(logRunnable, (TIME_BLOCK / TEST_COUNT).toLong())
                Choreographer.getInstance().postFrameCallback(this)
            }
        })
    }

    private val logRunnable = object : Runnable {
        var count = TEST_COUNT
        val list = mutableListOf<String>()
        override fun run() {
            val sb = StringBuilder()
            Looper.getMainLooper().thread.stackTrace.forEach {
                sb.append(it.toString() + "\n")
            }
            list.add(sb.toString())
            count--
            if (count == 0) {
                count = TEST_COUNT
                dealList(list)
                list.forEach {
                    Log.d("LJW", it)
                }
                list.clear()
            } else {
                handler?.postDelayed(this, (TIME_BLOCK / TEST_COUNT).toLong())
            }
        }
    }

    private fun dealList(list: MutableList<String>) {
        val resultList = mutableListOf<String>()
        var lastLog = ""
        list.forEach {
            if (TextUtils.equals(it, lastLog) && !resultList.contains(it)) {
                resultList.add(it)
            }
            lastLog = it
        }
        list.clear()
        list.addAll(resultList)
    }

}