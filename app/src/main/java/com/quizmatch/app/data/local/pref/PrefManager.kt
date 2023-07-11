package com.quizmatch.app.data.local.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.SCORE
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.SHAREPRE_NAME
import com.quizmatch.app.data.local.pref.SharedPreferencesKeys.Companion.USER_PROFILE
import com.quizmatch.app.data.model.firebase.User
import javax.inject.Inject


class PrefManager
@Inject constructor() : SharedPreferencesKeys {
    var pref: SharedPreferences? = null



    /**
     * User profile
     *
     * Get or set user profile details
     */
    var userProfile: User?
        get() = (if (pref!!.getString(USER_PROFILE, "") != "") {
            Gson().fromJson(pref!!.getString(USER_PROFILE, ""), User::class.java)
        } else {
            null
        })
        set(userProfile) {
            pref!!.edit().putString(USER_PROFILE, Gson().toJson(userProfile)).apply()
        }

    /**
     * Score
     *
     * Get or set user score to hit REST apis
     */
    var score: Int
        get() = pref!!.getInt(SCORE, 0)!!
        set(score) {
            pref!!.edit().putInt(SCORE, score).apply()
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
            .apply()
    }



}