package com.application.suliraapplication.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.adapter.AdapterFeedBack
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.pojo.FeedBackModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.AddFeedBackViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lukedeighton.wheelview.WheelView
import com.lukedeighton.wheelview.adapter.WheelAdapter
import kotlinx.android.synthetic.main.fragment_user_feedback.*
import java.util.*


class UserFeedbackFragment : BaseFragment(), WheelView.OnWheelItemSelectListener,
    View.OnClickListener {

    private lateinit var listFeedBackList: ArrayList<FeedBackModel>
    private lateinit var adapterFeedBack: AdapterFeedBack
    private var timerForPresence: Timer? = null
    private lateinit var listSuggestion: HashSet<String>

    private val addFeedBackViewModel by lazy {
        ViewModelProvider(this).get(AddFeedBackViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listSuggestion = hashSetOf()

        ivSelectedFeedBack.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_very_good
            )
        )

        tvFeedbackLabel.text = "I am satisfied"

        listFeedBackList = arrayListOf()
        listFeedBackList.add(
            FeedBackModel(
                "I am satisfied",
                R.drawable.ic_very_good
            )
        )

        listFeedBackList.add(
            FeedBackModel(
                "Good",
                R.drawable.ic_happy_smile
            )
        )

        listFeedBackList.add(
            FeedBackModel(
                "Very Good",
                R.drawable.ic_smile_excited
            )
        )

        listFeedBackList.add(
            FeedBackModel(
                "Not Good",
                R.drawable.ic__sad
            )
        )

        listFeedBackList.add(
            FeedBackModel(
                "Bad",
                R.drawable.ic__121
            )
        )

        // adapterFeedBack = AdapterFeedBack(requireContext(), listFeedBackList)
        wvUserFeedBack.adapter = object : WheelAdapter {
            override fun getDrawable(position: Int): Drawable {
                return ContextCompat.getDrawable(
                    requireContext(),
                    listFeedBackList[position].icon
                )!!
            }

            override fun getCount(): Int {
                return listFeedBackList.size
            }
        }

        wvUserFeedBack.setOnWheelItemSelectedListener(this)


        addFeedBackViewModel.successful.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            dismissDialog()

            if (it) {
                setError(addFeedBackViewModel.customResponse.msg)
                (activity as HomeTabActivity).replaceFragmentWithBackStack(HomeScreenFragment())
            } else {
                setError(addFeedBackViewModel.message)
            }
        })

    }

    override fun onWheelItemSelected(parent: WheelView?, itemDrawable: Drawable?, position: Int) {

        if (timerForPresence != null) {
            timerForPresence!!.cancel()
        }

        ivSelectedFeedBack.apply {
            if (itemDrawable != null) {
                setImageResource(listFeedBackList[position].icon)
                View.VISIBLE
            }

        }

        tvFeedbackLabel.text = listFeedBackList[position].title

        try {

            if (mBottomSheetDialog.isShowing) {
                return
            }

            timerForPresence = Timer()
            timerForPresence!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    if (activity != null) activity!!.runOnUiThread {
                        timerForPresence!!.cancel()
                        bottomSheetSuggestionLayout(listFeedBackList[position].icon)
                    }
                }
            }, 2000, 100000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private val mBottomSheetDialog by lazy {
        BottomSheetDialog(requireContext(), R.style.SheetDialog)
    }

    private fun bottomSheetSuggestionLayout(icon: Int) {
        val sheetView: View =
            layoutInflater.inflate(R.layout.bottomsheet_suggestion_layout, null)
        mBottomSheetDialog.setContentView(sheetView)
        val ivSelectedIcon: AppCompatImageView? =
            mBottomSheetDialog.findViewById(R.id.ivSelectedIcon)
        val btSlowLoading: AppCompatButton? = mBottomSheetDialog.findViewById(R.id.btSlowLoading)
        val btCustomerService: AppCompatButton? =
            mBottomSheetDialog.findViewById(R.id.btCustomerService)
        val btNetworkIssue: AppCompatButton? = mBottomSheetDialog.findViewById(R.id.btNetworkIssue)
        val btNotResponsive: AppCompatButton? =
            mBottomSheetDialog.findViewById(R.id.btNotResponsive)
        val btNotFunctional: AppCompatButton? =
            mBottomSheetDialog.findViewById(R.id.btNotFunctional)
        val btSecurityIssue: AppCompatButton? =
            mBottomSheetDialog.findViewById(R.id.btSecurityIssue)
        val btAppCrash: AppCompatButton? = mBottomSheetDialog.findViewById(R.id.btAppCrash)
        val btNavigation: AppCompatButton? = mBottomSheetDialog.findViewById(R.id.btNavigation)
        val btSubmitFeedBack: AppCompatImageView? =
            mBottomSheetDialog.findViewById(R.id.btSubmitFeedBack)

        ivSelectedIcon!!.setImageDrawable(ContextCompat.getDrawable(requireContext(), icon))
        vBlurEffect.visibility = View.VISIBLE
        mBottomSheetDialog.show()
        mBottomSheetDialog.setOnDismissListener {
            vBlurEffect.visibility = View.GONE
        }

        btSlowLoading!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btSlowLoading.getTag(btSlowLoading.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.rect_fill_back,
                if (!TextUtils.isEmpty(
                        btSlowLoading.getTag(btSlowLoading.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btCustomerService!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btCustomerService.getTag(btCustomerService.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.ab_negative_back,
                if (!TextUtils.isEmpty(
                        btCustomerService.getTag(btCustomerService.id).toString()
                    )
                ) R.color.black else R.color.white
            )

        }

        btNetworkIssue!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btNetworkIssue.getTag(btNetworkIssue.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.o_negative_back,
                if (!TextUtils.isEmpty(
                        btNetworkIssue.getTag(btNetworkIssue.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btNotResponsive!!.setOnClickListener {
            clickableEvent(it)
            val toString = btNotResponsive.getTag(btNotResponsive.id).toString()
            setBack(
                it,
                if (!TextUtils.isEmpty(toString)) R.drawable.rect_white_fill_back else R.drawable.o_positive_back,
                if (!TextUtils.isEmpty(toString)) R.color.black else R.color.white
            )
        }

        btNotFunctional!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btNotFunctional.getTag(btNotFunctional.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.ab_positive_back,
                if (!TextUtils.isEmpty(
                        btNotFunctional.getTag(btNotFunctional.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btSecurityIssue!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btSecurityIssue.getTag(btSecurityIssue.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.a_negative_back,
                if (!TextUtils.isEmpty(
                        btSecurityIssue.getTag(btSecurityIssue.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btAppCrash!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btAppCrash.getTag(btAppCrash.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.b_positive_back,
                if (!TextUtils.isEmpty(
                        btAppCrash.getTag(btAppCrash.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btNavigation!!.setOnClickListener {
            clickableEvent(it)
            setBack(
                it,
                if (!TextUtils.isEmpty(
                        btNavigation.getTag(btNavigation.id).toString()
                    )
                ) R.drawable.rect_white_fill_back else R.drawable.rect_fill_back,
                if (!TextUtils.isEmpty(
                        btNavigation.getTag(btNavigation.id).toString()
                    )
                ) R.color.black else R.color.white
            )
        }

        btSubmitFeedBack!!.setOnClickListener {

            val iterator = listSuggestion.iterator()
            val strSuggestionList = StringBuffer()

            while (iterator.hasNext()) {
                if (strSuggestionList.isEmpty()) {
                    strSuggestionList.append(iterator.next())
                } else {
                    strSuggestionList.append("," + iterator.next())
                }
            }

            showDialog()
            addFeedBackViewModel.addFeedBack(
                PreferenceManager().userId.toString(),
                tvFeedbackLabel.text.toString(),
                strSuggestionList.toString()
            )
            Log.e("hhhhhhhhhhhhhhhhhh", strSuggestionList.toString())
            listSuggestion.clear()
        }
    }

    private fun setBack(v: View?, id: Int, colorId: Int) {
        val appCompatButton = v as AppCompatButton
        appCompatButton.background = ContextCompat.getDrawable(requireContext(), id)
        appCompatButton.setTextColor(ContextCompat.getColor(requireContext(), colorId))
    }

    private fun clickableEvent(v: View?) {
        val element = v as AppCompatButton
        val contains = listSuggestion.contains(element.text)
        if (contains) {
            element.setTag(element.id, "")
            listSuggestion.remove(element.text.toString())
        } else {
            element.setTag(element.id, element.text)
            listSuggestion.add(element.text.toString())
        }

    }

    override fun onClick(v: View?) {

    }

}
