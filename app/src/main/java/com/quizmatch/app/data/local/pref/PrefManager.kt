package com.quizmatch.app.data.local.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.ACCESS_TOKEN
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.SHAREPRE_NAME
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.USER_PROFILE
import javax.inject.Inject


class PrefManager
@Inject constructor() : SharedPreferencesKeys {
    var pref: SharedPreferences? = null

    /**
     * Access token
     *
     * Get or set user access token to hit REST apis
     */
    var accessToken: String
        get() = pref!!.getString(ACCESS_TOKEN, "")!!
        set(accessToken) {
            pref!!.edit().putString(ACCESS_TOKEN, accessToken).apply()
        }




    /**
     * init shared preference
     * @param context application context
     */

    fun initPref(context: Context): PrefManager {
        pref = context.getSharedPreferences(SHAREPRE_NAME, MODE_PRIVATE)
        return this
    }

    /**
     * Clear pref data
     *
     * Clear shared preferences data on user log out
     *
     */
    fun clearPrefData() {
        pref!!.edit()
            .remove(USER_PROFILE)
            .remove(ACCESS_TOKEN)
            .apply()
    }



}