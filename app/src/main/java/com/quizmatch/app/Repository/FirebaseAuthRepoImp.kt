package com.quizmatch.app.Repository

import com.quizmatch.app.data.remote.APICallMethods
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/*
*/class FirebaseAuthRepoImp @Inject constructor(private val apiCallMethod:APICallMethods)
    :FirebaseAuthRepo {
    override fun getList(): List<String> {

        return listOf("2","4","7")
    }
}