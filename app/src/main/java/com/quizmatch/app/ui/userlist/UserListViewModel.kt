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
    var questionIndex: Int = 0
    var opponentUser = MutableLiveData<User>()
    var matchId = MutableLiveData<String>()
    lateinit var questionResult: QuestionListResponse.Result
    val userListResponse = MutableLiveData<MutableList<User>>()


    fun getUserList(){
    firebaseFirestoreDatabase.getPlayerList(userId = prefManager.userProfile?.user_id!!, onSuccess = {
        userListResponse.value = it.toMutableList()
    }, onError = {
        promptMessage.value = R.string.invalid_user_list

    })

    }
    fun createMatch(itemDetail: User) {
        val p = prefManager.userProfile?.matchId?.intersect(itemDetail.matchId.toSet())
        opponentUser.value = itemDetail
        if(p?.isEmpty()!!){
            firebaseFirestoreDatabase.createMatch(userID = prefManager.userProfile?.user_id!!, opponentId = itemDetail.user_id, onSuccess = {
                prefManager.userProfile?.matchId?.toMutableList()?.add(it)
                matchId.value = it
            }, onError = {
                promptMessage.value = R.string.unable_create_match

            })
        }else{
            matchId.value = p.first()

            promptMessage.value = R.string.room_Exits
        }


    }

}