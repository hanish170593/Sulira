package com.application.suliraapplication.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.UpdateHealthSleepViewModel
import kotlinx.android.synthetic.main.activity_sleep_time.*
import java.text.SimpleDateFormat
import java.util.*

class SleepTimeFragment : BaseFragment(), TimePicker.OnTimeChangedListener, View.OnClickListener {

    private lateinit var sleepTime: String
    private lateinit var wakeUpTime: String

    private val updateHealthSleepViewModel by lazy {
        ViewModelProvider(this).get(UpdateHealthSleepViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_sleep_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simpleTimePicker.setOnTimeChangedListener(this)
        simpleTimePicker.setIs24HourView(false)
        btSelectedTime.setOnClickListener(this)

        updateHealthSleepViewModel.successful.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                dismissDialog()
                if (it) {
                    setError(updateHealthSleepViewModel.customResponse.msg)
                    (activity as HomeTabActivity).onBackPressed()
                } else {
                    setError(updateHealthSleepViewModel.message)
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onTimeChanged(timePicker: TimePicker?, hourOfDay: Int, minute: Int) {

        Log.d("tttttttttttttttt", "$hourOfDay h $minute m")

        /*val datetime = Calendar.getInstance()
        datetime[Calendar.HOUR_OF_DAY] = hourOfDay
        datetime[Calendar.MINUTE] = minute

        if (datetime[Calendar.AM_PM] === Calendar.AM) am_pm =
            "AM" else if (datetime[Calendar.AM_PM] === Calendar.PM
        ) am_pm = "PM"*/

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getSelectedTimeData(): String {
        var hour = simpleTimePicker.hour
        var minutes = simpleTimePicker.minute
        if (minutes > 9) else minutes = "0$minutes".toInt()

        return if (hour > 12) {
            var hours = hour - 12
            if (hours > 9) else hours = "0$hours".toInt()
            "$hours:$minutes PM"
        } else if (hour == 12) {
            "$hour:$minutes PM"
        } else {
            if (hour > 9) else hour = "0$hour".toInt()
            "$hour:$minutes AM"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNumberOfHours(): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a")

        val date1 = simpleDateFormat.parse(sleepTime)
        var date2 = simpleDateFormat.parse(wakeUpTime)
        if (sleepTime.contains("PM") && wakeUpTime.contains("AM")) {
            val cal: Calendar = Calendar.getInstance()
            cal.time = date2!!
            cal.add(Calendar.DAY_OF_MONTH, 1)
            date2 = cal.time
        }
        val difference: Long = date2!!.time - date1!!.time
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min =
            (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
        hours = if (hours < 0) -hours else hours
        return "$hours h $min m";
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(clickView: View?) {
        when (clickView) {
            btSelectedTime -> {
                when (btSelectedTime.text) {
                    getString(R.string.conitnue) -> {
                        letsStart.text = getString(R.string.wake_up_time)
                        btSelectedTime.text = getString(R.string.update)
                        ivScreenIcon.setImageDrawable(requireActivity().getDrawable(R.drawable.walk_up_morning_icon))
                        sleepTime = getSelectedTimeData()
                    }

                    getString(R.string.update) -> {
                        wakeUpTime = getSelectedTimeData()
                        val numberOfHours = getNumberOfHours()
                        Log.d("final_values", "$sleepTime--$wakeUpTime--$numberOfHours")
                        showDialog()
                        updateHealthSleepViewModel.updateSleepHealthLog(
                            PreferenceManager().userId.toString(),
                            "sleep",
                            numberOfHours,
                            sleepTime,
                            wakeUpTime
                        )
                    }
                }
            }
        }
    }
}