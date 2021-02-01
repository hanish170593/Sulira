package com.application.suliraapplication.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.UpdateHealthConditionViewModel
import kotlinx.android.synthetic.main.fragment_update_sugar.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateSugarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateSugarFragment() : BaseFragment(), View.OnClickListener {

    private val updateHealthConditionViewModel by lazy {
        ViewModelProvider(this).get(UpdateHealthConditionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate-----B", "called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("onCreateView-----B", "called")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_sugar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("onActivityCreated-----B", "called")
        ivBackSugarScreen.setOnClickListener(this)
        btUpdateSugar.setOnClickListener(this)

        updateHealthConditionViewModel.successful.observe(viewLifecycleOwner, Observer {
            dismissDialog()
            if (it) {
                setError(updateHealthConditionViewModel.customResponse.msg)
                (activity as HomeTabActivity).onBackPressed()
            } else {
                setError(updateHealthConditionViewModel.message)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart-----B", "called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause-----B", "called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("onStop-----B", "called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("onDestroyView-----B", "called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy-----B", "called")
    }

    override fun onClick(clickView: View?) {
        when (clickView) {
            ivBackSugarScreen -> (activity as HomeTabActivity).onBackPressed()
            btUpdateSugar -> {
                if (TextUtils.isEmpty(etSugar.text.toString())) {
                    etSugar.error = "Please enter your sugar level"
                    etSugar.isFocusable = true
                    return
                }
                showDialog()
                updateHealthConditionViewModel.updateHealthCondition(
                    PreferenceManager().userId.toString(),
                    "sugar",
                    etSugar.text.toString()
                )
            }
        }

        /* ivBackSugarScreen.let {
             return@let ""
         }

         ivBackSugarScreen.also {
             return@also ""
         }

         ivBackSugarScreen.run {
           return@run ""
         }

         ivBackSugarScreen.apply {
             return@apply ""
         }
 */
        /* with(ivBackSugarScreen){
          return@with ""
         }*/
    }
}