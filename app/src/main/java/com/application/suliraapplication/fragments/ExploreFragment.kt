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
import com.application.suliraapplication.adapter.AdapterExploreCollection
import com.application.suliraapplication.adapter.AdapterExploreGetInspired
import com.application.suliraapplication.adapter.AdapterExploreRecommendation
import com.application.suliraapplication.adapter.AdapterExploreTopics
import com.application.suliraapplication.base.BaseFragment
import com.application.suliraapplication.models.ExploreModel
import com.application.suliraapplication.pojo.ModelInterest
import com.application.suliraapplication.prefs.PreferenceManager
import com.application.suliraapplication.viewmodels.ExploreViewModel
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : BaseFragment() {

    private lateinit var adapterExplorePlaces: AdapterExploreRecommendation
    private lateinit var adapterExploreTopics: AdapterExploreTopics
    private lateinit var adapterGetInspired: AdapterExploreGetInspired
    private lateinit var adapterCollection: AdapterExploreCollection
    private lateinit var listModelInterest: ArrayList<ModelInterest>

    private lateinit var exploreModel:ExploreModel

    private val exploreViewModel by lazy{
        ViewModelProvider(this).get(ExploreViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exploreViewModel.successful.observe(requireActivity(), Observer {
            dismissDialog()
            if (it){
                exploreModel=exploreViewModel.exploreModel
                setListAdapters()
            }else{
                setError(exploreViewModel.message)
            }
        })

        ivExploreBack.setOnClickListener {
            (activity as HomeTabActivity).onBackPressed()
        }

        showDialog()
        exploreViewModel.getExplore(PreferenceManager().userId.toString())
        //(activity as HomeTabActivity).heartBeatDialog()
    }

    private fun setListAdapters(){
        adapterExplorePlaces = AdapterExploreRecommendation(requireContext(),exploreModel)
        adapterExploreTopics = AdapterExploreTopics(requireContext(), exploreModel)
        adapterGetInspired = AdapterExploreGetInspired(requireContext(),exploreModel)
        adapterCollection = AdapterExploreCollection(requireContext(),exploreModel)

        rvRecomendation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterExploreRecommendation(requireContext(), exploreModel)
            adapter = adapterExplorePlaces
        }

        rvTopics.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterExploreTopics(requireContext(), exploreModel)
            adapter = adapterExploreTopics
        }

        rvGetInspired.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AdapterExploreGetInspired(requireContext(), exploreModel)
            adapter = adapterGetInspired
        }

        rvCollection.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = AdapterExploreCollection(requireContext(), exploreModel)
            adapter = adapterCollection
        }
    }

}