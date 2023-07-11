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
import com.quizmatch.app.data.model.firebase.Score
import com.quizmatch.app.data.model.firebase.ScoreDetail
import com.quizmatch.app.data.model.firebase.User
import com.quizmatch.app.data.remote.APICallMethods
import com.quizmatch.app.utils.FirebaseKeys.COMPLETED_DB_KEY
import com.quizmatch.app.utils.FirebaseKeys.INPROGRESS_DB_KEY
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionListViewModel @Inject constructor(
    private val apiCallMethods: APICallMethods,
    private val prefManager: PrefManager, private val
    firebaseFirestoreDatabase:
    FirebaseDatabaseRepository, private val
    questionRepo: QuestionRepo
) : ViewModel() {

    //Variable
    var scoreUpdate = MutableLiveData<Boolean>()
    val questionListResponse = MutableLiveData<QuestionListResponse>()
    val promptMessage = MutableLiveData<Any>()
    val finalScore = MutableLiveData<ScoreDetail>()
    val loader = MutableLiveData<Boolean>()
    var questionIndex: Int = 0
    var scoreDetail = MutableLiveData<Score>()
    var opponentUser = MutableLiveData<User>()
    var matchId = MutableLiveData<String>()
    lateinit var questionResult: QuestionListResponse.Result


    fun getScoreDetail() {

        firebaseFirestoreDatabase.getScoreDetail(matchId = matchId.value!!, scoreId =
        prefManager.userProfile?.user_id!!,
            onSuccess = {
                scoreDetail.value = it.first()
                if(questionIndex > 3) {

                    if (prefManager.score > scoreDetail.value?.score!!) {
                        //You won

                        finalScore.value = ScoreDetail(
                            prefManager.score.toString(), opponentScore =
                            scoreDetail.value?.score!!.toString()
                        )
                        prefManager.score = 0

                    } else if (prefManager.score < scoreDetail.value?.score!!) {
//                //Opponent Won

                        finalScore.value = ScoreDetail(
                            prefManager.score.toString(), opponentScore =
                            scoreDetail.value?.score!!.toString()
                        )
                        prefManager.score = 0
//
                    } else {
                        //questionIndex = 0
                        scoreUpdate.value = true
                    }
                }else {
                    scoreUpdate.value = true

                }
                loader.value =false




        }, onError = {
                loader.value =false

        })
    }
    fun getQuestionList() {
        loader.value = true

        questionRepo.getQuestList(apiCallMethods = apiCallMethods,
            firebaseDatabaseRepo = firebaseFirestoreDatabase,
            onSuccess = {
                questionListResponse.value = it
                loader.value = false

            },
            onError = {
                loader.value = false
                promptMessage.value = R.string.question_list_failed
            })
    }

    fun updateScore(score: Int) {
        firebaseFirestoreDatabase.updateScore(matchId = matchId.value!!, score = score, userId =
        prefManager.userProfile?.user_id!!, onSuccess = {

        }, onError = {
            promptMessage.value = R.string.login_failed

        })
    }

    fun getUser(){
//        firebaseFirestoreDatabase.getUser(prefManager.userProfile?.opponent_id, onSuccess = {
//            opponentUser.value = it
//        }, onError = {
//
//        })
    }

    fun checkAnswer(answer: String) {
        questionIndex++
        if (answer == questionResult.correct_answer) {
            prefManager.score = prefManager.score + 5
            updateScore(prefManager.score)

        } else {
            prefManager.score = prefManager.score - 2
            updateScore(prefManager.score)
        }
        getScoreDetail()

            //getScoreDetail()
       // }
//        else {
//
//
//        }

//        if (questionIndex == 4) {
//          //  if(prefManager.userProfile?.match_status == INPROGRESS_DB_KEY){
//            if(prefManager.score > opponentUser.value?.score!!) {
//                //You won
//                promptMessage.value = "You won"
//            }else if (prefManager.score < opponentUser.value?.score!!){
//                //Opponent Won
//                promptMessage.value = "${opponentUser.value?.getFullName()} won"
//
//            }else{
//                getQuestionList()
//
//            }
//        } else {
//            scoreUpdate.value = true
//        }
    }

}