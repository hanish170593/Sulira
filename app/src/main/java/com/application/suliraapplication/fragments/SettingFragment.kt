package com.application.suliraapplication.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.activity.SignInActivity
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.LogoutViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : BaseFragment(), View.OnClickListener {

    private val logoutViewModel by lazy {
        ViewModelProvider(this).get(LogoutViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llPaymentOption.setOnClickListener(this)
        llMyProfile.setOnClickListener(this)
        llChangePassword.setOnClickListener(this)
        llPrivacyPolicy.setOnClickListener(this)
        llTermAndCondition.setOnClickListener(this)
        llFeedback.setOnClickListener(this)
        llLogout.setOnClickListener(this)

        logoutViewModel.successful.observe(viewLifecycleOwner, Observer { successful ->
            dismissDialog()
            if (successful) {
                PreferenceManager().saveuserData("")
                PreferenceManager().userId("")
                PreferenceManager().stripeToken("")
                activity!!.startActivity(
                    Intent(
                        activity,
                        SignInActivity::class.java
                    )
                )
                (activity as HomeTabActivity).finish()
            } else {
                setError(logoutViewModel.message)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                try {
                    //(activity as HomeTabActivity).employerCodeDialog()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        }, 3000)
    }

    override fun onClick(clickView: View?) {

        when (clickView) {

            llPaymentOption -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                PaymentOptionFragment()
            )

            llMyProfile -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                MyProfileFragment()
            )

            llChangePassword -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                ResetPasswordFragment()
            )

            llPrivacyPolicy -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                PrivacyAndPolicyFragment()
            )

            llTermAndCondition -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                TermAndConditionFragment()
            )

            llFeedback -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                UserFeedbackFragment()
            )

            llLogout -> {

                AlertDialog.Builder(activity)
                    .setTitle(resources.getString(R.string.logout))
                    .setMessage(resources.getString(R.string.r_u_sure)).setPositiveButton(
                        resources.getString(R.string.yes)
                    ) { dialog: DialogInterface, _: Int ->
                        PreferenceManager().saveuserData("")
                        dialog.dismiss()
                        showDialog()
                        logoutViewModel.logout(PreferenceManager().userId.toString())
                    }.setNegativeButton(
                        resources.getString(R.string.no)
                    ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }.create().show()

            }
        }
    }

}