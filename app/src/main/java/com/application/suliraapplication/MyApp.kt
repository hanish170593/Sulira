
package com.application.suliraapplication

import android.app.Application
import com.stripe.android.PaymentConfiguration

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51I8JZ4JCtGV8rT0DM37c22Oshvs4zledIHk2flmX928Nt1lPLtwZYgt7ESuPeRoSlSQ2DeLLQK05vrgDjuQa1EI100IvDx6XA0"
        )
    }
}