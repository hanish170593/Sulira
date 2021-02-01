package com.application.suliraapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.application.suliraapplication.R
import github.hellocsl.cursorwheel.CursorWheelLayout

class SimpleTextAdapter(val context: Context) : CursorWheelLayout.CycleWheelAdapter() {
    override fun getView(parent: View?, position: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.adapter_spinner_layout, null);
    }

    override fun getItem(position: Int): Any {

        return true
    }

    override fun getCount(): Int = 4

}