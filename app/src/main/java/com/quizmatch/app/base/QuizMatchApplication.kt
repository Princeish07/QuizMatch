package com.quizmatch.app.base

import android.app.Application
import com.quizmatch.app.Repository.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseAuthRepoImp
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.data.remote.APIHandler
import com.quizmatch.app.utils.InternetUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuizMatchApplication:Application() {


    override fun onCreate() {
        super.onCreate()
        //init internet utils
        InternetUtil.init(this)
    }
}