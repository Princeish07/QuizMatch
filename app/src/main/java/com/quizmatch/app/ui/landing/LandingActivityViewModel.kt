package com.quizmatch.app.ui.landing

import androidx.lifecycle.ViewModel
import com.quizmatch.app.Repository.FirebaseAuthRepo
import com.quizmatch.app.data.local.pref.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LandingActivityViewModel @Inject constructor(private val firebaseAuthRepo: FirebaseAuthRepo,
                                                   private val prefManager: PrefManager): ViewModel() {

    fun getList(){
        for(i in 0 until firebaseAuthRepo.getList().size){
            print(firebaseAuthRepo.getList()[i])
        }
    }

}