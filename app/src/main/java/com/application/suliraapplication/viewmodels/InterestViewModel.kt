package com.application.suliraapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.suliraapplication.R
import com.application.suliraapplication.application.SuliraApplication
import com.application.suliraapplication.models.InterestModel
import com.application.suliraapplication.network.UserDataManager
import com.application.suliraapplication.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class InterestViewModel : ViewModel() {
    var tag = InterestViewModel::class.java.simpleName!!
    private var userDataManager: UserDataManager = UserDataManager()
    val successful: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var message: String
    lateinit var interestModel: InterestModel

    @SuppressLint("CheckResult")
    fun getInterestListData() {
        userDataManager.getInterestList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<InterestModel>() {
                override fun onComplete() {

                }

                override fun onNext(responseT: InterestModel) {
                    interestModel = responseT
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

            })
    }
}