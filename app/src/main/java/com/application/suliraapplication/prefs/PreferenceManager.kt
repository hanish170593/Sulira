package com.application.suliraapplication.prefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.application.suliraapplication.application.SuliraApplication

/**
 * This class contains SharedPreferences utilities, such as methods to save and clear application sensitive data.
 */
class PreferenceManager {

    companion object {
        const val APPLICATION_PREFERENCE = "app-preferences"
        const val AUTH_TOKEN = "auth-token"
        const val USER_RESPONSE = "USER_RESPONSE"
        const val FINGER_PRINT_ENABLE = "FINGER_PRINT_ENABLE"
        const val USER_ID = "USER_ID"
        const val STRIPE_TOKEN = "STRIPE_TOKEN"
    }

    private val context: Context = SuliraApplication.getContext()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        APPLICATION_PREFERENCE, Context.MODE_PRIVATE
    )

    /**
     * Saves the authorization token to SharedPreferences file.
     * @param authToken String which is the authorization token
     */
    @SuppressLint("ApplySharedPref")
    //Cannot use .apply(), it will take time to save the token. We need token ASAP
    fun putAuthToken(authToken: String) {
        sharedPreferences.edit().putString(AUTH_TOKEN, "Bearer $authToken").commit()
    }

    @SuppressLint("ApplySharedPref")
    fun saveuserData(userResponse: String) {
        sharedPreferences.edit().putString(USER_RESPONSE, userResponse).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun userId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun stripeToken(stripeToken: String) {
        sharedPreferences.edit().putString(STRIPE_TOKEN, stripeToken).commit()
    }

    @SuppressLint("ApplySharedPref")
    fun isFingerPrintEnable(isEnable: Boolean) {
        sharedPreferences.edit().putBoolean(FINGER_PRINT_ENABLE, isEnable).commit()
    }

    val fingerPrintEnable: Boolean? get() = sharedPreferences.getBoolean(FINGER_PRINT_ENABLE, false)

    val userResponse: String? get() = sharedPreferences.getString(USER_RESPONSE, "")

    val authToken: String?
        get() = sharedPreferences.getString(AUTH_TOKEN, "")

    val stripePaymentToken: String?
        get() = sharedPreferences.getString(STRIPE_TOKEN, "")

    val userId: String?
        get() = sharedPreferences.getString(USER_ID, "")

    /**
     * Clears all the data that has been saved in the preferences file.
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
