package com.roy.githubuser.ui


import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.roy.githubuser.R
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val DELAY_TIME = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(mainLooper).postDelayed({
            startActivity<MainActivity>()
            finish()
        }, DELAY_TIME.toLong())
    }
}