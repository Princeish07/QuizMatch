package com.quizmatch.app.ui.landing

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quizmatch.app.R
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LandingActivityViewModel @Inject constructor(private val firebaseAuthRepo: FirebaseAuthRepo,
                                                   private val prefManager: PrefManager, private val
                                                   firebaseFirestoreDatabase: FirebaseDatabaseRepository
):
    ViewModel() {
    //Variable
     val promptMessage = MutableLiveData<Any>()
    val loader = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    //region Validation
    private fun isDataValid(
        email: String,
        password: String
    ): Boolean {
        when {

            TextUtils.isEmpty(email.trim()) -> {
                promptMessage.value = R.string.empty_email

            }
            !email.trim().isValidEmail() -> {
                promptMessage.value = R.string.invalid_email

                }
            TextUtils.isEmpty(password.trim()) -> {
                promptMessage.value = R.string.empty_password

            }
            password.trim().length < 6 -> {
                promptMessage.value = R.string.invalid_password

            }
        }
        return email.trim().isValidEmail() && !TextUtils.isEmpty(password.trim()) && password.trim().length!! >= 6
    }
    //endregion
    fun login(email:String, password:String) {
        loader.value = true
        if (isDataValid(email, password)) {
            firebaseAuthRepo.logIn(email = email,
                password = password,
                firebaseFirestoreDatabase,
                onSuccess = {
                    promptMessage.value = R.string.login_success
                 //   loader.value = false
                    loginSuccess.value = false
                    prefManager.userProfile = it

                },
                onError = {
                    promptMessage.value = R.string.login_failed
                    loader.value = false

                })
        }
    }

}