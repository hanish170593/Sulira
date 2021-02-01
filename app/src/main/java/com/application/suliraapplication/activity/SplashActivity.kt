package com.application.suliraapplication.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.application.suliraapplication.R
import com.application.suliraapplication.prefs.PreferenceManager
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val android_id:String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        Log.d("android_id",android_id)
        if(!TextUtils.isEmpty(PreferenceManager().userResponse)){
            startActivity(Intent(this,HomeTabActivity::class.java))
        }

        btGetStarted.setOnClickListener {
            startActivity(Intent(this,RegistrationContainerActivity::class.java))
        }

        tvSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause------A","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop------A","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy------A","onDestroy")
    }

}