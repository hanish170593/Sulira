package com.application.suliraapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.application.suliraapplication.R
import com.application.suliraapplication.application.SuliraApplication
import com.application.suliraapplication.network.CustomResponse
import com.application.suliraapplication.network.UserDataManager
import com.application.suliraapplication.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class ChatMainFragmentViewModel : ViewModel() {
    var tag = ChatMainFragmentViewModel::class.java.simpleName!!

    private val userDataManager: UserDataManager = UserDataManager()

    val successful: MediatorLiveData<Boolean> = MediatorLiveData()
    lateinit var message: String
    lateinit var userProfile: CustomResponse

    @SuppressLint("CheckResult")
    fun getUserProfile() {
        userDataManager.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CustomResponse>() {
                override fun onNext(response: CustomResponse) {
                    userProfile = response
                    successful.value = true
                }

                override fun onError(throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            message = SuliraApplication.getContext()
                                .getString(R.string.error_please_check_internet)
                        }

                        is TimeoutException -> {
                            message = SuliraApplication.getContext()
                                .getString(R.string.error_request_timed_out)
                        }

                        is HttpException -> {
                            message = CommonUtils.getErrorResponse(throwable).message
                        }

                        else -> {
                            message = SuliraApplication.getContext()
                                .getString(R.string.error_something_went_wrong)
                            Log.e(tag, throwable.localizedMessage)
                        }

                    }
                    successful.value = false
                }

                override fun onComplete() {
                }
            })
    }

}