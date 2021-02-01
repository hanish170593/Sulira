package com.application.suliraapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.ViewPagerAdapter
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.PremiumPlanModel
import com.application.suliraapplication.viewmodels.PremiumPlanViewModel
import kotlinx.android.synthetic.main.fragment_get_premium_membership.*


class GetPremiumMembershipFragment : BaseFragment(), View.OnClickListener {

    private lateinit var adapterViewPager: ViewPagerAdapter
    private lateinit var premiumPlanModel: PremiumPlanModel
    private var planId = ""

    private val premiumPlanViewModel by lazy {
        ViewModelProvider(this).get(PremiumPlanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_premium_membership, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterViewPager = ViewPagerAdapter(requireContext())
        vpPremium.adapter = adapterViewPager
        dotsIndicator.setViewPager(vpPremium)
        vpPremium.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)

        ivGoPremium.setOnClickListener(this)
        ivBackPremium.setOnClickListener(this)
        flYearlyPlan.setOnClickListener(this)
        flMonthlyPlan.setOnClickListener(this)

        premiumPlanViewModel.successful.observe(viewLifecycleOwner, Observer {
            dismissDialog()

            if (it) {
                premiumPlanModel = premiumPlanViewModel.premiumPlanModel
                if (premiumPlanModel.Plans.isNotEmpty()) {
                    setData()
                }
            } else {
                setError(premiumPlanViewModel.message)
            }
        })

        showDialog()
        premiumPlanViewModel.getPlan()
    }

    private fun setData() {
        tvPlanTitle.text = premiumPlanModel.title
        tvPlanText.text = premiumPlanModel.subTitle

        val saving = premiumPlanModel.Plans[1].saveing
        val monthly = premiumPlanModel.Plans[1].monthly
        val yearly = premiumPlanModel.Plans[1].planPrice
        tvSavingPercentage.text = "Save $saving %"
        tvYearPerMonthPrice.text = "$$monthly"
        tvFullYearPrice.text = "$$monthly/month,billed yearly $$yearly"

        val monthlyCharge = premiumPlanModel.Plans[0].monthly
        tvMonthPrice.text = "$$monthlyCharge"
        tvBilledMonthly.text = "Billed monthly $$monthlyCharge"
        planId = premiumPlanModel.Plans[1].id
    }

    override fun onClick(view: View?) {

        when (view) {

            ivGoPremium -> (activity as HomeTabActivity).replaceFragmentWithBackStack(
                PaymentFragment.newInstance(planId)
            )

            ivBackPremium -> (activity as HomeTabActivity).onBackPressed()

            flYearlyPlan -> {
                planId = premiumPlanModel.Plans[1].id
                ivYearlySelected.visibility = View.VISIBLE
                ivMonthlySelected.visibility = View.GONE
            }

            flMonthlyPlan -> {
                planId = premiumPlanModel.Plans[1].id
                ivYearlySelected.visibility = View.GONE
                ivMonthlySelected.visibility = View.VISIBLE
            }
        }

    }
}