package com.application.suliraapplication.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.suliraapplication.R
import com.application.suliraapplication.application.SuliraApplication
import com.application.suliraapplication.models.SignUpModel
import com.application.suliraapplication.network.UserDataManager
import com.application.suliraapplication.utils.CommonUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class EditUserViewModel:ViewModel() {

    var tag = EditUserViewModel::class.java.simpleName!!
    private var userDataManager: UserDataManager = UserDataManager()
    val successful: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var message: String
    lateinit var signUpModel: SignUpModel

    @SuppressLint("CheckResult")
    fun editUser(userId: RequestBody, fullName: RequestBody, email: RequestBody, phone: RequestBody, age: RequestBody, profilePic: MultipartBody.Part) {
        userDataManager.editUser(userId,fullName,email,phone,age,profilePic)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<SignUpModel>() {
                override fun onComplete() {

                }

                override fun onNext(response: SignUpModel) {
                    signUpModel = response
                    message=response.msg
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