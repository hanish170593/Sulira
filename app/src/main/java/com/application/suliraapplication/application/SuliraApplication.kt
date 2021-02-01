package com.application.suliraapplication.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.stripe.android.PaymentConfiguration

/**
 * The entry point, a class that represents Mentorship application.
 */

class SuliraApplication : Application() {

    companion object {

        lateinit var instance: SuliraApplication

        /**
         * @return the instance of the Application
         */
        fun getApplication(): SuliraApplication {
            return instance
        }

        /**
         * @return the context of the Application
         */
        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51I8KI3L3TVzhu95a51vCz80v0LOJETmebLb5B7zkwG0hn1Tp3EK1SlO02M29y24961a7EivvQnXsMg5g6We7f5LF00ym30k7Et"
        )
    }
}
