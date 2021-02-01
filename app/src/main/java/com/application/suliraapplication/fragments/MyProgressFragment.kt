package com.application.suliraapplication.fragments

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.activity.heartbeat.HeartCheckActivity
import com.application.suliraapplication.adapter.AdapterWeightList
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.HealthProgressDataModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.utils.MyMarkerView
import com.application.suliraapplication.viewmodels.HealthProgressViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_my_progress.*

private const val ARG_PARAM1 = "TYPE"
private const val ARG_PARAM2 = "param2"

@Suppress("DEPRECATION")
class MyProgressFragment : BaseFragment(), AdapterView.OnItemSelectedListener,
    View.OnClickListener {

    private lateinit var adapterWeightList: AdapterWeightList
    var progress = arrayOf("week", "month", "year")
    private lateinit var healthProgressDataModel: HealthProgressDataModel
    private var tfRegular: Typeface? = null
    private var tfLight: Typeface? = null
    private var dataType: String? = null
    private var strLastUpdateAt: String? = null
    private lateinit var strDataTime: String
    private var strTime = "week"

    private val healthProgressViewModel by lazy {
        ViewModelProvider(this).get(HealthProgressViewModel::class.java)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DummyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyProgressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        callApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            dataType = it.getString(ARG_PARAM1)
            strLastUpdateAt = it.getString(ARG_PARAM2)
        }

        tvProgressType.text = dataType
        btUpdate.setOnClickListener(this)
        tfRegular = Typeface.createFromAsset(requireActivity().assets, "OpenSans-Regular.ttf")
        tfLight = Typeface.createFromAsset(requireActivity().assets, "OpenSans-Light.ttf")
        chart.description.isEnabled = false
        chart.isClickable = false
        chart.setPinchZoom(false)
        chart.setDrawBarShadow(false)

        chart.setDrawGridBackground(false)
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart // For bounds control
        chart.marker = mv // Set the marker to the chart

        val legend = chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(true)
        legend.typeface = tfLight
        legend.yOffset = 0f
        legend.xOffset = 10f
        legend.xEntrySpace = 0f
        legend.yEntrySpace = 0f
        legend.textSize = 8f

        val xAxis = chart.xAxis
        xAxis.typeface = tfLight
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)

        val leftAxis = chart.axisLeft
        leftAxis.typeface = tfLight
        leftAxis.valueFormatter = LargeValueFormatter()
        leftAxis.setDrawGridLines(false)
        leftAxis.spaceTop = 35f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        chart.axisRight.isEnabled = false
        chart.xAxis.spaceMax = 1f
        ivBackMyProgress.setOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        val adapterArray: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.spinner_item, progress)
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, progress)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnProgressReport.apply {
            adapter = adapterArray
            setSelection(0, false)
            onItemSelectedListener = this@MyProgressFragment
            prompt = "Please Select"
            gravity = Gravity.CENTER
        }
        adapterArray.setNotifyOnChange(true)

        healthProgressViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                healthProgressDataModel = healthProgressViewModel.healthProgressDataModel
                setHeaderData()
                setData()
            } else {
                setError(healthProgressViewModel.message)
            }
        })

    }

    private fun callApi() {
        showDialog()
        when (dataType) {
            "Weight" -> healthProgressViewModel.getHealthLogData(
                PreferenceManager().userId.toString(),
                strTime,
                "weight"
            )

            "Heart Rate" -> healthProgressViewModel.getHealthLogData(
                PreferenceManager().userId.toString(),
                strTime,
                "heartRate"
            )

            "Sleep" -> healthProgressViewModel.getHealthLogData(
                PreferenceManager().userId.toString(),
                strTime,
                "sleep"
            )

            "Sugar" -> healthProgressViewModel.getHealthLogData(
                PreferenceManager().userId.toString(),
                strTime,
                "sugar"
            )
        }
    }

    private fun setHeaderData() {

        when (dataType) {

            "Weight" -> {
                tvCurrentWeight.text = healthProgressDataModel.lastUpdatedValue
                tvProgressDataType.text = "LBS"
                btUpdate.text = "Update Weight"
            }

            "Heart Rate" -> {
                tvCurrentWeight.text = healthProgressDataModel.lastUpdatedValue
                tvProgressDataType.text = "BPM"
                btUpdate.text = "Update Heart Rate"
            }

            "Sleep" -> {
                tvCurrentWeight.text = healthProgressDataModel.lastUpdatedValue
                tvProgressDataType.visibility = View.GONE
                btUpdate.text = "Update Sleep"
            }

            "Sugar" -> {
                tvCurrentWeight.text = healthProgressDataModel.lastUpdatedValue
                tvProgressDataType.text = "mg/dL"
                btUpdate.text = "Update Sugar"
            }
        }

        tvLastUpdatedAt.text = healthProgressDataModel.lastUpdatedDate
    }

    private fun setData() {

        if (healthProgressDataModel.healthLogArray.isNotEmpty()) {
            adapterWeightList =
                AdapterWeightList(healthProgressDataModel.healthLogArray, requireContext())

            rvWeightList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter =
                    AdapterWeightList(healthProgressDataModel.healthLogArray, requireContext())
                adapter = adapterWeightList
            }
        }

        tvBmi.text = healthProgressDataModel.bmiVal
        tvBmiText.text = healthProgressDataModel.bmiText

        val values = ArrayList<BarEntry>()
        for (item in healthProgressDataModel.healthLogArray.indices) {

            values.add(
                BarEntry(
                    healthProgressDataModel.healthLogArray[item].grafTime.split(" ")[0].split("-")[2].toFloat(),
                    healthProgressDataModel.healthLogArray[item].healthValue.toFloat()
                )
            )
        }

        val set1: BarDataSet
        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "")
            set1.setDrawIcons(false)
            set1.color = requireActivity().resources.getColor(R.color.app_color)
            val dataSets = java.util.ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(tfLight)
            data.barWidth = 0.5f
            chart.data = data
        }

        chart.invalidate()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        strTime = p0!!.selectedItem.toString()
        callApi()
    }

    override fun onClick(clickView: View?) {
        when (clickView) {
            btUpdate -> {
                callClickListener()
            }
        }
    }

    private fun callClickListener() {
        when (dataType) {

            "Weight" -> {
                (activity as HomeTabActivity).replaceFragmentWithBackStack(UpdateWeightFragment())
            }

            "Heart Rate" -> {
                requireActivity().startActivity(
                    Intent(
                        activity,
                        HeartCheckActivity::class.java
                    ).putExtra(
                        "lastUpdate",
                        strLastUpdateAt
                    )
                )
            }

            "Sleep" -> {
                (activity as HomeTabActivity).replaceFragmentWithBackStack(SleepTimeFragment())
            }

            "Sugar" -> {
                (activity as HomeTabActivity).replaceFragmentWithBackStack(UpdateSugarFragment())
            }
        }
    }

}
