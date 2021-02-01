package com.application.suliraapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import kotlinx.android.synthetic.main.activity_get_strated.*

class GetStartedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_strated)
        btFinalGetStart.setOnClickListener {
          startActivity(Intent(this, HomeTabActivity::class.java))
        }
    }
}