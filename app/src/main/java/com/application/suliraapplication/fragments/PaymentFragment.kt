package com.application.suliraapplication.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.AppCompatButton
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
import com.application.suliraapplication.viewmodels.GetUserInfoViewModel
import com.application.suliraapplication.viewmodels.ProceedToPayViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_saved_card.*

private const val ARG_PARAM1 = "param1"

class PaymentFragment : BaseFragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private var planId: String = ""
    private lateinit var adapterFinalPayment: AdapterFinalPayment
    private lateinit var cardListModel: CardListModel

    private val getSavedCardListViewModel by lazy {
        ViewModelProvider(this).get(GetSavedCardListViewModel::class.java)
    }

    private val proceedToPayViewModel by lazy {
        ViewModelProvider(this).get(ProceedToPayViewModel::class.java)
    }

    private val getUserInfoViewModel by lazy {
        ViewModelProvider(this).get(GetUserInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            planId = it.getString(ARG_PARAM1).toString()
        }

        ivAddNewCardPayment.setOnClickListener {
            (activity as HomeTabActivity).replaceFragmentWithBackStack(AddCardFragment())
        }

        ivPaymentBack.setOnClickListener(this)
        btnPay.setOnClickListener(this)

        getSavedCardListViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                cardListModel = getSavedCardListViewModel.cardListModel

                btnPay.visibility =
                    if (cardListModel.stripeCrads.isNotEmpty()) View.VISIBLE else View.GONE

                setData()
            } else {
                setError(getSavedCardListViewModel.message)
            }

        })

        proceedToPayViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it) {
                successDialog(requireContext())
            } else {
                errorDialog(requireContext())
            }
        })

        getUserInfoViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()

            if (it) {
                PreferenceManager().saveuserData(Gson().toJson(getUserInfoViewModel.signUpModel))
                (activity as HomeTabActivity).replaceFragmentWithBackStack(HomeScreenFragment())
            } else {
                setError(getUserInfoViewModel.message)
            }
        })

        showDialog()
        getSavedCardListViewModel.getSavedCardsList(PreferenceManager().stripePaymentToken.toString())
    }

    private fun successDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout_payment_success)
        dialog.setCancelable(false)
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window!!.setLayout(6 * width / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        val btPaymentSuccess: AppCompatButton = dialog.findViewById(R.id.btPaymentSuccess)
        btPaymentSuccess.setOnClickListener {
            showDialog()
            getUserInfoViewModel.getUserInfo(PreferenceManager().userId.toString())
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun errorDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout_payment_error)
        dialog.setCancelable(false)
        val metrics: DisplayMetrics = context.resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window!!.setLayout(6 * width / 7, WindowManager.LayoutParams.WRAP_CONTENT)
        val btPaymentFailedOk: AppCompatButton = dialog.findViewById(R.id.btPaymentFailedOk)
        btPaymentFailedOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setData() {

        adapterFinalPayment = AdapterFinalPayment(cardListModel.stripeCrads, requireContext())

        rvPaymentCardList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = AdapterFinalPayment(cardListModel.stripeCrads, requireContext())
            adapter = adapterFinalPayment
        }
    }

    override fun onClick(clickView: View?) {
        when (clickView) {

            ivPaymentBack -> (activity as HomeTabActivity).onBackPressed()


            btnPay -> {
                chargeUser()
            }

        }
    }

    private fun chargeUser() {
        val selectedCard = adapterFinalPayment.getSelectedCard()
        var selectedCardId = ""
        for (item in selectedCard) {
            if (item.isSelected) {
                selectedCardId = item.id
            }
        }

        val userId = PreferenceManager().userId.toString()
        val stripeToken = PreferenceManager().stripePaymentToken.toString()

        showDialog()
        proceedToPayViewModel.proceedToPay(userId, stripeToken, selectedCardId, planId)

    }

}