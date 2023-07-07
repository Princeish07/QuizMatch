package com.quizmatch.app.ui.question

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.core.View
import com.quizmatch.app.R
import com.quizmatch.app.Repository.FirebaseAuth.FirebaseAuthRepo
import com.quizmatch.app.Repository.FirebaseFirestoreDatabase.FirebaseDatabaseRepository
import com.quizmatch.app.Repository.question.QuestionRepo
import com.quizmatch.app.data.local.pref.PrefManager
import com.quizmatch.app.data.model.api.QuestionListResponse
import com.quizmatch.app.data.remote.APICallMethods
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionListViewModel @Inject constructor(private val apiCallMethods: APICallMethods,
                                                private val prefManager: PrefManager, private val
                                                firebaseFirestoreDatabase:
                                                FirebaseDatabaseRepository, private val
                                                questionRepo: QuestionRepo):
    ViewModel()  {
    var scoreUpdate = MutableLiveData<Boolean>()
    val questionListResponse = MutableLiveData<QuestionListResponse>()
    val promptMessage = MutableLiveData<Any>()
    var questionIndex:Int = 0
    lateinit var questionResult : QuestionListResponse.Result
        fun getQuestionList(){

            questionRepo.getQuestList(apiCallMethods = apiCallMethods, firebaseDatabaseRepo = firebaseFirestoreDatabase,
                onSuccess = {
                            questionListResponse.value = it

            }, onError = {
                promptMessage.value = R.string.question_list_failed
            })
        }
    fun updateScore(score:String){
        firebaseFirestoreDatabase.updateScore(score, onSuccess = {

        }, onError = {

        })
    }

    fun checkAnswer(answer:String) {
        questionIndex++

        if(answer == questionResult.correct_answer){
//           // updateScore(prefManager.score.toString())


        }
        else{
           // prefManager.score -= 2
           // updateScore(prefManager.score.toString())
        }
        if(questionIndex==4){
            getQuestionList()
        }else {
            scoreUpdate.value = true
        }
    }

}