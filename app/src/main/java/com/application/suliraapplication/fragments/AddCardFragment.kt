package com.application.suliraapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.application.suliraapplication.R
import com.application.suliraapplication.activity.HomeTabActivity
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.prefs.PreferenceManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment(), View.OnClickListener {
    private val USER_AGENT =
        "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/72.0.3626.121 Mobile Safari/535.19"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_add_card, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpModel: SignUpModel =
            Gson().fromJson(PreferenceManager().userResponse.toString(), SignUpModel::class.java)

        ivAddCardBack.setOnClickListener(this)
        progressBar.isShowArrow = true
        progressBar.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        progressBar.setCircleBackgroundEnabled(true)
        webViewPwa.settings.loadsImagesAutomatically = true
        webViewPwa.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webViewPwa.settings.userAgentString = USER_AGENT
        webViewPwa.settings.javaScriptEnabled = true

        webViewPwa.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == "https://hourlylancer.com/devlop/sulira/") {
                    (activity as HomeTabActivity).onBackPressed()
                }
            }
        }

        webViewPwa.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {

                if (activity ==null){
                    return
                }

                if (progressBar.visibility == View.GONE) {
                    progressBar.visibility = View.VISIBLE
                }

                if (progress == 100) {
                    progressBar.visibility = View.GONE
                }

            }
        }

        val id = signUpModel.userInfo.stripeToken
        webViewPwa.loadUrl("http://hourlylancer.com/devlop/sulira/saveCardValidation/$id")
    }

    override fun onClick(p0: View?) {
        (activity as HomeTabActivity).onBackPressed()
    }
}