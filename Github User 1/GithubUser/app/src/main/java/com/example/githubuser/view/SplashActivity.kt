package com.example.githubuser.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.githubuser.R

class SplashActivity : AppCompatActivity() {

    companion object{
        const val delayMillis = 3000L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(mainLooper)
        handler.postDelayed( {
            kotlin.run {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        },delayMillis)
    }

}