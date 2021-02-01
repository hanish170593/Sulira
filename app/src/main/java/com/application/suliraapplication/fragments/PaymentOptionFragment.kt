package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterFinalPayment
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.CardListModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.GetSavedCardListViewModel
import kotlinx.android.synthetic.main.fragment_payment_option.*
import kotlinx.android.synthetic.main.fragment_saved_card.*

class PaymentOptionFragment : BaseFragment() {
    private lateinit var adapterFinalPayment: AdapterFinalPayment
    private lateinit var cardListModel: CardListModel

    private val getSavedCardListViewModel by lazy {
        ViewModelProvider(this).get(GetSavedCardListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPaymentOptionBack.setOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        ivAddNewCard.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(AddCardFragment())
        }

        getSavedCardListViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                cardListModel = getSavedCardListViewModel.cardListModel
                setData()
            } else {
                setError(getSavedCardListViewModel.message)
            }

        })

        showDialog()
        getSavedCardListViewModel.getSavedCardsList(PreferenceManager().stripePaymentToken.toString())
    }

    private fun setData() {

        adapterFinalPayment = AdapterFinalPayment(cardListModel.stripeCrads, requireContext())

        rvPaymentSavedCard.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterFinalPayment(cardListModel.stripeCrads, requireContext())
            adapter = adapterFinalPayment
        }


    }
}