package com.quizmatch.app.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.core.View
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.local.pref.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(private val prefManager: PrefManager):ViewModel() {

    fun authenticationHandle():Boolean{
        return prefManager.userProfile?.user_id != "" && prefManager.userProfile?.user_id != null
    }
}