package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.GetUserScheduleModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.GetUserScheduleViewModel
import com.application.suliraapplication.viewmodels.UpdateUserScheduleViewModel
import kotlinx.android.synthetic.main.activity_weight_reminder.*

class WeightReminderFragment : BaseFragment(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var getUserScheduleModel: GetUserScheduleModel
    private lateinit var listSet: HashSet<String>

    private val updateUserScheduleViewModel by lazy {
        ViewModelProvider(this).get(UpdateUserScheduleViewModel::class.java)
    }

    private val getUserScheduleViewModel by lazy {
        ViewModelProvider(this).get(GetUserScheduleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_weight_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listSet = hashSetOf()

        getUserScheduleViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                getUserScheduleModel = getUserScheduleViewModel.getUserScheduleModel
                setData()
            } else {
                setError(getUserScheduleViewModel.message)
            }
        })

        updateUserScheduleViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                setError(updateUserScheduleViewModel.customResponse.msg)
                (activity as HomeTabActivity).onBackPressed()
            } else {
                setError(updateUserScheduleViewModel.message)
            }
        })

        btSetReminder.setOnClickListener(this)
        cbMonday.setOnCheckedChangeListener(this)
        cbTuesday.setOnCheckedChangeListener(this)
        cbWednesday.setOnCheckedChangeListener(this)
        cbThursday.setOnCheckedChangeListener(this)
        cbFriday.setOnCheckedChangeListener(this)
        cbSaturday.setOnCheckedChangeListener(this)
        cbSunday.setOnCheckedChangeListener(this)
    }

    private fun setData() {
        cbMonday.isChecked = getUserScheduleModel.scheduleData.monday == "1"
        cbTuesday.isChecked = getUserScheduleModel.scheduleData.tuesday == "1"
        cbWednesday.isChecked = getUserScheduleModel.scheduleData.wednesday == "1"
        cbThursday.isChecked = getUserScheduleModel.scheduleData.thursday == "1"
        cbFriday.isChecked = getUserScheduleModel.scheduleData.friday == "1"
        cbSaturday.isChecked = getUserScheduleModel.scheduleData.saturday == "1"
        cbSunday.isChecked = getUserScheduleModel.scheduleData.sunday == "1"
    }

    override fun onResume() {
        super.onResume()
        showDialog()
        getUserScheduleViewModel.getUserSchedule(PreferenceManager().userId.toString(), "weight")
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            btSetReminder -> {

                val strSelectedDays = StringBuffer()

                val iterator = listSet.iterator()
                while (iterator.hasNext()) {
                    if (strSelectedDays.isEmpty()) {
                        strSelectedDays.append(iterator.next())
                    } else {
                        strSelectedDays.append("," + iterator.next())
                    }
                }

                Log.d("strSelectedDays", strSelectedDays.toString())

                if (TextUtils.isEmpty(strSelectedDays.toString())) {
                    setError("Please select at least one day")
                    return
                }

                showDialog()
                updateUserScheduleViewModel.updateUserSchedule(
                    PreferenceManager().userId.toString(),
                    "weight",
                    strSelectedDays.toString()
                )

            }
        }

    }

    override fun onCheckedChanged(clickButton: CompoundButton?, isChecked: Boolean) {

        when (clickButton) {

            cbMonday -> {
                handleData(isChecked, "monday")
            }

            cbTuesday -> {
                handleData(isChecked, "tuesday")
            }

            cbWednesday -> {
                handleData(isChecked, "wednesday")
            }

            cbThursday -> {
                handleData(isChecked, "thursday")
            }

            cbFriday -> {
                handleData(isChecked, "friday")
            }

            cbSaturday -> {
                handleData(isChecked, "saturday")
            }

            cbSunday -> {
                handleData(isChecked, "sunday")
            }

        }

        val size = listSet.size
        tvSelectedDays.text="SELECTED DAYS ($size)"
    }

    private fun handleData(isChecked: Boolean, strKey: String) {
        if (isChecked) {
            listSet.add(strKey)
        } else {
            listSet.remove(strKey)
        }
    }

}