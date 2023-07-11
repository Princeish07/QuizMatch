package com.quizmatch.app.ui.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.quizmatch.app.R
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.data.model.api.QuestionListResponse
import com.quizmatch.app.data.model.firebase.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val prefManager: PrefManager, private val
    firebaseFirestoreDatabase: FirebaseDatabaseRepository
) : ViewModel() {

    //Variable

    var scoreUpdate = MutableLiveData<Boolean>()
    val questionListResponse = MutableLiveData<QuestionListResponse>()
    val promptMessage = MutableLiveData<Any>()
    val loader = MutableLiveData<Boolean>()
    var opponentUser = MutableLiveData<User>()
    var matchId = MutableLiveData<String>()
    lateinit var questionResult: QuestionListResponse.Result
    val userListResponse = MutableLiveData<MutableList<User>>()


    fun getUserList(){
        loader.value = true
    firebaseFirestoreDatabase.getPlayerList(userId = prefManager.userProfile?.user_id!!, onSuccess = {
        userListResponse.value = it.toMutableList()
        loader.value = false

    }, onError = {
        loader.value = false

        promptMessage.value = R.string.invalid_user_list

    })

    }
    fun createMatch(itemDetail: User) {
        val p = prefManager.userProfile?.matchId?.intersect(itemDetail.matchId.toSet())
        prefManager.score = 0
        opponentUser.value = itemDetail
        if(p?.isEmpty()!!){
            firebaseFirestoreDatabase.createMatch(userID = prefManager.userProfile?.user_id!!, opponentId = itemDetail.user_id, onSuccess = {
                prefManager.userProfile?.matchId?.toMutableList()?.add(it)
                matchId.value = it
                loader.value = false

            }, onError = {
                promptMessage.value = R.string.unable_create_match
                loader.value = false


            })
        }else{
            matchId.value = p.first()
            loader.value = false
        }


    }

}