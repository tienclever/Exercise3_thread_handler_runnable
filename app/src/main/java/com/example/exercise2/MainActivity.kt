package com.example.exercise2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var sohienthi: Int = 0
    var y_test: Float = 0f

    //khởi tạo runnable
    lateinit var runnable: Runnable
    lateinit var runnabletwo: Runnable
    lateinit var runnablethree: Runnable

    //tạo một thread con
    private val handlerThread: HandlerThread = HandlerThread("threadWorker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handlerThread.start()
        var handler = Handler(this.handlerThread.looper)

        viewScreen.setOnTouchListener { v, event ->
            handler.removeCallbacks(runnable)
            when (event.actionMasked) {
                MotionEvent.ACTION_MOVE -> {
                    var y = event.y
                    when {
                        y_test > y -> {
                            sohienthi += 1
                            if (sohienthi % 100 == 0){
                                ChangeColor()
                            }
                        }
                        y_test < y -> {
                            sohienthi -= 1
                            if (sohienthi % 100 == 0){
                                ChangeColor()
                            }
                        }
                    }
                    txtHienThiSo.text = sohienthi.toString()
                }
                MotionEvent.ACTION_UP -> {
                    handler.postDelayed(runnable, 2000)
                    v.performClick()
                }
                MotionEvent.ACTION_DOWN -> {
                    y_test = event.y
                }
            }
            true
        }

        btnCong.setOnClickListener {
            handler.removeCallbacks(runnable)
            sohienthi++
            txtHienThiSo.text = sohienthi.toString()
            handler.postDelayed(runnable, 2000)
        }

        btnTru.setOnClickListener {
            handler.removeCallbacks(runnable)
            sohienthi--
            txtHienThiSo.text = sohienthi.toString()
            handler.postDelayed(runnable, 2000)
        }

        btnCong.setOnLongClickListener {
            handler.post(runnabletwo)
        }

        btnTru.setOnLongClickListener {
            handler.post(runnablethree)
        }

        runnable = Runnable {
            when {
                sohienthi > 0 -> {
                    sohienthi--
                    this.runOnUiThread {
                        txtHienThiSo.text = sohienthi.toString()
                        if (sohienthi % 100 == 0){
                            ChangeColor()
                        }
                    }
                    handler.postDelayed(runnable, 50)
                }

                sohienthi < 0 -> {
                    sohienthi++
                    this.runOnUiThread {
                        txtHienThiSo.text = sohienthi.toString()
                        if (sohienthi % 100 == 0){
                            ChangeColor()
                        }
                    }
                    handler.postDelayed(runnable, 50)
                }

                else -> {
                    handler.removeCallbacks(runnable)
                }
            }
        }

        runnabletwo = Runnable {
            handler.removeCallbacks(runnable)
            if (btnCong.isPressed) {
                sohienthi++
                this.runOnUiThread {
                    txtHienThiSo.text = sohienthi.toString()
                    if (sohienthi % 100 == 0){
                        ChangeColor()
                    }
                }
                handler.postDelayed(runnabletwo, 50)
            } else {
                handler.removeCallbacks(runnabletwo)
                handler.postDelayed(runnable, 2000)
            }
        }

        runnablethree = Runnable {
            handler.removeCallbacks(runnable)
            if (btnTru.isPressed) {
                sohienthi--
                this.runOnUiThread {
                    txtHienThiSo.text = sohienthi.toString()
                    if (sohienthi % 100 == 0){
                        ChangeColor()
                    }
                }
                handler.postDelayed(runnablethree, 50)
            } else {
                handler.removeCallbacks(runnablethree)
                handler.postDelayed(runnable, 2000)
            }
        }

    }

    private fun ChangeColor() {
        val nrd = Random()
        val color: Int =
            Color.argb(255, nrd.nextInt(256), nrd.nextInt(256), nrd.nextInt(256))
        txtHienThiSo.setTextColor(color)
    }

}