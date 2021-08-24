package com.jevely.androidcatontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BlockDetectUtil.start()

        findViewById<TextView>(R.id.tv).setOnClickListener {
            try {
                Thread.sleep(700)
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}