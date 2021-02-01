package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.RegistrationContainerActivity
import com.application.suliraapplication.prefs.PreferenceManager
import kotlinx.android.synthetic.main.activity_face_recognization.*

class FaceRecognizationFragment : Fragment() {
    private val preferenceManager: PreferenceManager = PreferenceManager()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_face_recognization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btActivate.setOnClickListener {
            preferenceManager.isFingerPrintEnable(true)
            (activity as RegistrationContainerActivity).replaceFragmentWithBackStack(
                GenderSelectionFragment()
            )
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as RegistrationContainerActivity).handleSkipOrNextBtnVisibility(true, "Skip")
    }
}